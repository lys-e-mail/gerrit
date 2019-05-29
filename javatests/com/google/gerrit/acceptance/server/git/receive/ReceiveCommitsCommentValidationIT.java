package com.google.gerrit.acceptance.server.git.receive;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.PushOneCommit;
import com.google.gerrit.acceptance.testsuite.request.RequestScopeOperations;
import com.google.gerrit.extensions.annotations.Exports;
import com.google.gerrit.extensions.api.changes.ReviewInput;
import com.google.gerrit.extensions.api.changes.ReviewInput.CommentInput;
import com.google.gerrit.extensions.client.Side;
import com.google.gerrit.extensions.common.CommentInfo;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.TopLevelResource;
import com.google.gerrit.extensions.validators.CommentValidationListener;
import com.google.gerrit.extensions.validators.CommentValidationFailure;
import com.google.gerrit.extensions.validators.CommentValidationFailure.Status;
import com.google.gerrit.server.change.ChangeResource;
import com.google.gerrit.server.change.RevisionResource;
import com.google.gerrit.server.notedb.ChangeNoteUtil;
import com.google.gerrit.server.restapi.change.ChangesCollection;
import com.google.gerrit.server.restapi.change.PostReview;
import com.google.gerrit.testing.FakeEmailSender;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provider;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

// XXX Discard this test and just have one common test?
public class ReceiveCommitsCommentValidationIT extends AbstractDaemonTest {
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
    public CommentValidationFailure validateComments(ImmutableList<CommentForValidation> comments) {
      return CommentValidationFailure.create(Status.INVALID, ImmutableList.of("not OK"));
    }
  }

  @Before
  public void setUp() {
    requestScopeOperations.setApiUser(user.id());
  }

  @Test
  public void validateCommentsInReceiveCommits() throws Exception {
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
}
