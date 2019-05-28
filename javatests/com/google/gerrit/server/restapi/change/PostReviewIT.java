package com.google.gerrit.server.restapi.change;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.acceptance.PushOneCommit.FILE_NAME;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.NoHttpd;
import com.google.gerrit.acceptance.PushOneCommit;
import com.google.gerrit.acceptance.testsuite.request.RequestScopeOperations;
import com.google.gerrit.extensions.annotations.Exports;
import com.google.gerrit.extensions.api.changes.DraftInput;
import com.google.gerrit.extensions.api.changes.ReviewInput;
import com.google.gerrit.extensions.api.changes.ReviewInput.CommentInput;
import com.google.gerrit.extensions.client.Comment;
import com.google.gerrit.extensions.client.Side;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.TopLevelResource;
import com.google.gerrit.extensions.validators.CommentValidationListener;
import com.google.gerrit.extensions.validators.CommentValidationResult;
import com.google.gerrit.extensions.validators.CommentValidationResult.Status;
import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.server.change.ChangeResource;
import com.google.gerrit.server.change.RevisionResource;
import com.google.gerrit.server.notedb.ChangeNoteUtil;
import com.google.gerrit.server.notedb.DeleteCommentRewriter;
import com.google.gerrit.testing.FakeEmailSender;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.NoteMap;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// XXX Also validate the reply msg

@NoHttpd
public class PostReviewIT extends AbstractDaemonTest {
  @Inject private ChangeNoteUtil noteUtil;
  @Inject private FakeEmailSender email;
  @Inject private Provider<ChangesCollection> changes;
  @Inject private Provider<PostReview> postReview;
  @Inject private RequestScopeOperations requestScopeOperations;

  private final Integer[] lines = {0, 1};


  @Override
  public Module createModule() {
    return new FactoryModule() {
      @Override
      public void configure() {
        bind(CommentValidationListener.class)
            .annotatedWith(Exports.named("TestCommentValidationListener"))
            .to(TestCommentValidationListener.class);
      }
    };
  }

  private static class TestCommentValidationListener implements CommentValidationListener {
    @Override
    public CommentValidationResult validateComments(ImmutableList<CommentForValidation> comments) {
      // XXX More fancy?
      for (CommentForValidation c : comments) {
        if (c.getText().contains("reject")) {
          return CommentValidationResult.create(Status.INVALID, ImmutableList.of(
              "invalid comment: " + c.getText()));
        }
      }
      return CommentValidationResult.create(Status.VALID, ImmutableList.of());
    }
  }

  @Before
  public void setUp() {
    requestScopeOperations.setApiUser(admin.id());
  }

  @Test
  // XXX Also test validation of drafts.
  public void validateCommentsViaInput() throws Exception {
    // XXX Can this setup be simplified?
    Timestamp timestamp = new Timestamp(0);
    String file = "file";
    String contents = "contents";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, contents);
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    ReviewInput input = new ReviewInput();
    CommentInput comment = newComment(file, Side.REVISION, 0, "XXX beep XXX", false);
    comment.updated = timestamp;
    input.comments = ImmutableMap.of(comment.path, Lists.newArrayList(comment));
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource = revisions.parse(changeResource, IdString.fromDecoded(revId));

    // XXX Use an exception?
    postReview.get().apply(batchUpdateFactory, revisionResource, input, timestamp);

    Map<String, List<CommentInfo>> result = getPublishedComments(changeId, revId);
    assertThat(result).isNotEmpty();
    CommentInfo actual = Iterables.getOnlyElement(result.get(comment.path));
    CommentInput ci = infoToInput(file).apply(actual);
    ci.updated = comment.updated;
    assertThat(comment).isEqualTo(ci);
    assertThat(actual.updated).isEqualTo(gApi.changes().id(r.getChangeId()).info().created);
  }

  @Test
  public void validateCommentsInReceiveCommits_commentOK() throws Exception {
    // XXX Extract common code to setup?
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    String path = "file";
    DraftInput comment = newDraft(path, Side.REVISION, 0, "this comment is ok");
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).hasSize(1);
  }

  @Test
  public void validateCommentsInReceiveCommits_commentRejected() throws Exception {
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    String path = "file";
    DraftInput comment = newDraft(path, Side.REVISION, 0, "this comment will be rejected");
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
  }

  // XXX Delete/extract helpers, or extract/pull up?

  private List<CommentInfo> getRevisionComments(String changeId, String revId) throws Exception {
    return getPublishedComments(changeId, revId).values().stream()
        .flatMap(List::stream)
        .collect(toList());
  }

  private CommentInput addComment(String changeId, String message) throws Exception {
    ReviewInput input = new ReviewInput();
    CommentInput comment = newComment(FILE_NAME, Side.REVISION, 0, message, false);
    input.comments = ImmutableMap.of(comment.path, Lists.newArrayList(comment));
    gApi.changes().id(changeId).current().review(input);
    return comment;
  }

  private void addComments(String changeId, String revision, CommentInput... commentInputs)
      throws Exception {
    ReviewInput input = new ReviewInput();
    input.comments = Arrays.stream(commentInputs).collect(groupingBy(c -> c.path));
    gApi.changes().id(changeId).revision(revision).review(input);
  }

  /**
   * All the commits, which contain the target comment before, should still contain the comment with
   * the updated message. All the other metas of the commits should be exactly the same.
   */
  private void assertMetaBranchCommitsAfterRewriting(
      List<RevCommit> beforeDelete,
      Change.Id changeId,
      String targetCommentUuid,
      String expectedMessage)
      throws Exception {
    List<RevCommit> afterDelete = getChangeMetaCommitsInReverseOrder(changeId);
    assertThat(afterDelete).hasSize(beforeDelete.size());

    try (Repository repo = repoManager.openRepository(project);
        ObjectReader reader = repo.newObjectReader()) {
      for (int i = 0; i < beforeDelete.size(); i++) {
        RevCommit commitBefore = beforeDelete.get(i);
        RevCommit commitAfter = afterDelete.get(i);

        Map<String, com.google.gerrit.reviewdb.client.Comment> commentMapBefore =
            DeleteCommentRewriter.getPublishedComments(
                noteUtil, changeId, reader, NoteMap.read(reader, commitBefore));
        Map<String, com.google.gerrit.reviewdb.client.Comment> commentMapAfter =
            DeleteCommentRewriter.getPublishedComments(
                noteUtil, changeId, reader, NoteMap.read(reader, commitAfter));

        if (commentMapBefore.containsKey(targetCommentUuid)) {
          assertThat(commentMapAfter).containsKey(targetCommentUuid);
          com.google.gerrit.reviewdb.client.Comment comment =
              commentMapAfter.get(targetCommentUuid);
          assertThat(comment.message).isEqualTo(expectedMessage);
          comment.message = commentMapBefore.get(targetCommentUuid).message;
          commentMapAfter.put(targetCommentUuid, comment);
          assertThat(commentMapAfter).isEqualTo(commentMapBefore);
        } else {
          assertThat(commentMapAfter).doesNotContainKey(targetCommentUuid);
        }

        // Other metas should be exactly the same.
        assertThat(commitAfter.getFullMessage()).isEqualTo(commitBefore.getFullMessage());
        assertThat(commitAfter.getCommitterIdent()).isEqualTo(commitBefore.getCommitterIdent());
        assertThat(commitAfter.getAuthorIdent()).isEqualTo(commitBefore.getAuthorIdent());
        assertThat(commitAfter.getEncoding()).isEqualTo(commitBefore.getEncoding());
        assertThat(commitAfter.getEncodingName()).isEqualTo(commitBefore.getEncodingName());
      }
    }
  }

  private static String extractComments(String msg) {
    // Extract lines between start "....." and end "-- ".
    Pattern p = Pattern.compile(".*[.]{5}\n+(.*)\\n+-- \n.*", Pattern.DOTALL);
    Matcher m = p.matcher(msg);
    return m.matches() ? m.group(1) : msg;
  }

  private ReviewInput newInput(CommentInput c) {
    ReviewInput in = new ReviewInput();
    in.comments = new HashMap<>();
    in.comments.put(c.path, Lists.newArrayList(c));
    return in;
  }

  private void addComment(PushOneCommit.Result r, String message) throws Exception {
    addComment(r, message, false, false, null);
  }

  private void addComment(
      PushOneCommit.Result r,
      String message,
      boolean omitDuplicateComments,
      Boolean unresolved,
      String inReplyTo)
      throws Exception {
    CommentInput c = new CommentInput();
    c.line = 1;
    c.message = message;
    c.path = FILE_NAME;
    c.unresolved = unresolved;
    c.inReplyTo = inReplyTo;
    ReviewInput in = newInput(c);
    in.omitDuplicateComments = omitDuplicateComments;
    gApi.changes().id(r.getChangeId()).revision(r.getCommit().name()).review(in);
  }

  private CommentInfo addDraft(String changeId, String revId, DraftInput in) throws Exception {
    return gApi.changes().id(changeId).revision(revId).createDraft(in).get();
  }

  private void updateDraft(String changeId, String revId, DraftInput in, String uuid)
      throws Exception {
    gApi.changes().id(changeId).revision(revId).draft(uuid).update(in);
  }

  private void deleteDraft(String changeId, String revId, String uuid) throws Exception {
    gApi.changes().id(changeId).revision(revId).draft(uuid).delete();
  }

  private CommentInfo getPublishedComment(String changeId, String revId, String uuid)
      throws Exception {
    return gApi.changes().id(changeId).revision(revId).comment(uuid).get();
  }

  private Map<String, List<CommentInfo>> getPublishedComments(String changeId, String revId)
      throws Exception {
    return gApi.changes().id(changeId).revision(revId).comments();
  }

  private Collection<CommentInfo> getPublishedComments(String changeId) throws Exception {
    return gApi.changes().id(changeId).comments().values().stream()
        .flatMap(Collection::stream)
        .collect(toList());
  }

  private Map<String, List<CommentInfo>> getDraftComments(String changeId, String revId)
      throws Exception {
    return gApi.changes().id(changeId).revision(revId).drafts();
  }

  private CommentInfo getDraftComment(String changeId, String revId, String uuid) throws Exception {
    return gApi.changes().id(changeId).revision(revId).draft(uuid).get();
  }

  private static CommentInput newComment(String file, String message) {
    return newComment(file, Side.REVISION, 0, message, false);
  }

  private static CommentInput newComment(
      String path, Side side, int line, String message, Boolean unresolved) {
    CommentInput c = new CommentInput();
    return populate(c, path, side, null, line, message, unresolved);
  }

  private static CommentInput newCommentOnParent(
      String path, int parent, int line, String message) {
    CommentInput c = new CommentInput();
    return populate(c, path, Side.PARENT, Integer.valueOf(parent), line, message, false);
  }

  private DraftInput newDraft(String path, Side side, int line, String message) {
    DraftInput d = new DraftInput();
    return populate(d, path, side, null, line, message, false);
  }

  private DraftInput newDraft(String path, Side side, Comment.Range range, String message) {
    DraftInput d = new DraftInput();
    return populate(d, path, side, null, range, message, false);
  }

  private DraftInput newDraftOnParent(String path, int parent, int line, String message) {
    DraftInput d = new DraftInput();
    return populate(d, path, Side.PARENT, Integer.valueOf(parent), line, message, false);
  }

  private static <C extends Comment> C populate(
      C c,
      String path,
      Side side,
      Integer parent,
      Comment.Range range,
      String message,
      Boolean unresolved) {
    int line = range.startLine;
    c.path = path;
    c.side = side;
    c.parent = parent;
    c.line = line != 0 ? line : null;
    c.message = message;
    c.unresolved = unresolved;
    if (line != 0) c.range = range;
    return c;
  }

  private static <C extends Comment> C populate(
      C c, String path, Side side, Integer parent, int line, String message, Boolean unresolved) {
    return populate(c, path, side, parent, createLineRange(line, 1, 5), message, unresolved);
  }

  private static Comment.Range createLineRange(int line, int startChar, int endChar) {
    Comment.Range range = new Comment.Range();
    range.startLine = line;
    range.startCharacter = startChar;
    range.endLine = line;
    range.endCharacter = endChar;
    return range;
  }

  private static Function<CommentInfo, CommentInput> infoToInput(String path) {
    return infoToInput(path, CommentInput::new);
  }

  private static Function<CommentInfo, DraftInput> infoToDraft(String path) {
    return infoToInput(path, DraftInput::new);
  }

  private static <I extends Comment> Function<CommentInfo, I> infoToInput(
      String path, Supplier<I> supplier) {
    return info -> {
      I i = supplier.get();
      i.path = path;
      copy(info, i);
      return i;
    };
  }

  private static void copy(Comment from, Comment to) {
    to.side = from.side == null ? Side.REVISION : from.side;
    to.parent = from.parent;
    to.line = from.line;
    to.message = from.message;
    to.range = from.range;
    to.unresolved = from.unresolved;
    to.inReplyTo = from.inReplyTo;
  }
}
