package com.google.gerrit.server.restapi.change;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.testing.GerritJUnit.assertThrows;
import static java.util.stream.Collectors.toList;

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
import com.google.gerrit.extensions.api.changes.ReviewInput.DraftHandling;
import com.google.gerrit.extensions.client.Comment;
import com.google.gerrit.extensions.client.Comment.Range;
import com.google.gerrit.extensions.client.Side;
import com.google.gerrit.extensions.common.ChangeMessageInfo;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.TopLevelResource;
import com.google.gerrit.extensions.validators.CommentValidationFailure;
import com.google.gerrit.extensions.validators.CommentValidationListener;
import com.google.gerrit.extensions.validators.CommentValidationListener.CommentForValidation;
import com.google.gerrit.extensions.validators.CommentValidationListener.CommentType;
import com.google.gerrit.server.change.ChangeResource;
import com.google.gerrit.server.change.RevisionResource;
import com.google.gerrit.server.plugincontext.PluginSetContext;
import com.google.gerrit.server.update.CommentsRejectedException;
import com.google.gerrit.server.update.UpdateException;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

// XXX Should some tests be split off into another test case?

@NoHttpd
public class PostReviewIT extends AbstractDaemonTest {
  @Inject private Provider<ChangesCollection> changes;
  @Inject private Provider<PostReview> postReview;
  @Inject private RequestScopeOperations requestScopeOperations;
  @Inject private PluginSetContext<CommentValidationListener> commentValidationListeners;

  @Override
  public Module createModule() {
    return new FactoryModule() {
      @Override
      public void configure() {
        bind(CommentValidationListener.class)
            .annotatedWith(Exports.named("TestCommentValidationListener"))
            .to(TestCommentValidationListener.class)
            .asEagerSingleton();
      }
    };
  }

  private static class TestCommentValidationListener implements CommentValidationListener {
    @Override
    public ImmutableList<CommentValidationFailure> validateComments(
        ImmutableList<CommentForValidation> comments) {
      arguments.addAll(comments);
      for (CommentForValidation c : comments) {
        if (c.getText().contains("reject")) {
          return ImmutableList.of(c.failValidation("invalid comment: contains 'reject'"));
        }
      }
      return ImmutableList.of();
    }

    List<CommentForValidation> arguments = new ArrayList<>();
  }

  @Before
  public void setUp() {
    requestScopeOperations.setApiUser(admin.id());
    getValidationCalls().clear();
  }

  @Test
  public void validateCommentsInInput_commentOK() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    ReviewInput input = new ReviewInput();
    String commentText = "this comment is OK";
    CommentInput comment = newComment(file, commentText);
    comment.updated = new Timestamp(0);
    input.comments = ImmutableMap.of(comment.path, Lists.newArrayList(comment));
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    assertThat(getPublishedComments(changeId)).isEmpty();
    postReview.get().apply(batchUpdateFactory, revisionResource, input, comment.updated);
    assertThat(getPublishedComments(changeId)).hasSize(1);
    assertThat(getValidationCalls())  // XXX Also for the other tests.
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, commentText)));
  }

  @Test
  public void validateCommentsInInput_commentRejected() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    String commentText = "this comment will be rejected";
    ReviewInput input = new ReviewInput();
    CommentInput comment = newComment(file, commentText);
    comment.updated = new Timestamp(0);
    input.comments = ImmutableMap.of(comment.path, Lists.newArrayList(comment));
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    assertThat(getPublishedComments(changeId)).isEmpty();
    UpdateException updateException =
        assertThrows(
            UpdateException.class,
            () ->
                postReview
                    .get()
                    .apply(batchUpdateFactory, revisionResource, input, comment.updated));
    assertThat(updateException.getCause()).isInstanceOf(CommentsRejectedException.class);
    assertThat(
        Iterables.getOnlyElement(
            ((CommentsRejectedException) updateException.getCause())
                .getCommentValidationFailures())
            .getComment()
            .getText())
        .isEqualTo(commentText);
    assertThat(getPublishedComments(changeId)).isEmpty();
    assertThat(getValidationCalls())
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, commentText)));
  }

  @Test
  public void validateDrafts_draftOK() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    DraftInput draft = newDraft("this comment is ok");
    addDraft(changeId, revId, draft);
    assertThat(getPublishedComments(changeId)).isEmpty();

    ReviewInput reviewInput = new ReviewInput();
    reviewInput.drafts = DraftHandling.PUBLISH;
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    postReview.get().apply(batchUpdateFactory, revisionResource, reviewInput, new Timestamp(0));
    assertThat(getPublishedComments(changeId)).hasSize(1);
    assertThat(getValidationCalls())
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, draft.message)));
  }

  @Test
  public void validateDrafts_draftRejected() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    DraftInput draft = newDraft("this comment is rejected");
    addDraft(changeId, revId, draft);
    assertThat(getPublishedComments(changeId)).isEmpty();

    ReviewInput reviewInput = new ReviewInput();
    reviewInput.drafts = DraftHandling.PUBLISH;
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    UpdateException updateException =
        assertThrows(
            UpdateException.class,
            () ->
                postReview
                    .get()
                    .apply(batchUpdateFactory, revisionResource, reviewInput, new Timestamp(0)));
    assertThat(updateException.getCause()).isInstanceOf(CommentsRejectedException.class);
    assertThat(
        Iterables.getOnlyElement(
            ((CommentsRejectedException) updateException.getCause())
                .getCommentValidationFailures())
            .getComment()
            .getText())
        .isEqualTo(draft.message);
    assertThat(getPublishedComments(changeId)).isEmpty();
    assertThat(getValidationCalls())
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, draft.message)));
  }

  @Test
  public void validateCommentsInReviewMessage_messageOK() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    String messageText = "this message is OK";
    ReviewInput input = new ReviewInput().message(messageText);
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    int numMessages = gApi.changes().id(changeId).get().messages.size();
    postReview.get().apply(batchUpdateFactory, revisionResource, input, new Timestamp(0));
    assertThat(gApi.changes().id(changeId).get().messages).hasSize(numMessages + 1);
    ChangeMessageInfo message = Iterables.getLast(gApi.changes().id(changeId).get().messages);
    assertThat(message.message).contains(messageText);
  }

  @Test
  public void validateCommentsInReviewMessage_messageRejected() throws Exception {
    String file = "file";
    PushOneCommit push =
        pushFactory.create(admin.newIdent(), testRepo, "first subject", file, "contents");
    PushOneCommit.Result r = push.to("refs/for/master");
    String changeId = r.getChangeId();
    String revId = r.getCommit().getName();

    String messageText = "this message will be rejected";
    ReviewInput input = new ReviewInput().message(messageText);
    ChangeResource changeResource =
        changes.get().parse(TopLevelResource.INSTANCE, IdString.fromDecoded(changeId));
    RevisionResource revisionResource =
        revisions.parse(changeResource, IdString.fromDecoded(revId));
    assertThat(gApi.changes().id(changeId).get().messages).hasSize(1);  // From the initial commit.
    UpdateException updateException =
        assertThrows(
            UpdateException.class,
            () ->
                postReview
                    .get()
                    .apply(batchUpdateFactory, revisionResource, input, new Timestamp(0)));
    assertThat(updateException.getCause()).isInstanceOf(CommentsRejectedException.class);
    assertThat(
            Iterables.getOnlyElement(
                    ((CommentsRejectedException) updateException.getCause())
                        .getCommentValidationFailures())
                .getComment()
                .getText())
        .isEqualTo(messageText);
    assertThat(gApi.changes().id(changeId).get().messages).hasSize(1);  // Unchanged from before.
    ChangeMessageInfo message = Iterables.getLast(gApi.changes().id(changeId).get().messages);
    assertThat(message.message).doesNotContain(messageText);
  }

  // XXX Relocate this test?
  @Test
  public void validateCommentsInReceiveCommits_commentOK() throws Exception {
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    DraftInput comment = newDraft("this comment is ok");
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).hasSize(1);
  }

  // XXX Relocate this test?
  @Test
  public void validateCommentsInReceiveCommits_commentRejected() throws Exception {
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    DraftInput comment = newDraft("this comment will be rejected");
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
  }

  private List<CommentForValidation> getValidationCalls() {
    return ((TestCommentValidationListener) commentValidationListeners.iterator().next().get())
        .arguments;
  }

  private void addDraft(String changeId, String revId, DraftInput in) throws Exception {
    gApi.changes().id(changeId).revision(revId).createDraft(in).get();
  }

  private Collection<CommentInfo> getPublishedComments(String changeId) throws Exception {
    return gApi.changes().id(changeId).comments().values().stream()
        .flatMap(Collection::stream)
        .collect(toList());
  }

  private static CommentInput newComment(String path, String message) {
    return populate(new CommentInput(), path, message);
  }

  private DraftInput newDraft(String message) {
    return populate(new DraftInput(), "file", message);
  }

  private static <C extends Comment> C populate(C c, String path, Range range, String message) {
    int line = range.startLine;
    c.path = path;
    c.side = Side.REVISION;
    c.parent = null;
    c.line = line != 0 ? line : null;
    c.message = message;
    c.unresolved = false;
    if (line != 0) c.range = range;
    return c;
  }

  private static <C extends Comment> C populate(C c, String path, String message) {
    return populate(c, path, createLineRange(), message);
  }

  private static Comment.Range createLineRange() {
    Comment.Range range = new Comment.Range();
    range.startLine = 0;
    range.startCharacter = 1;
    range.endLine = 0;
    range.endCharacter = 5;
    return range;
  }
}
