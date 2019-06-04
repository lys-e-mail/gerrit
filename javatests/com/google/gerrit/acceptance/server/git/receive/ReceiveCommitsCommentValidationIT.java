package com.google.gerrit.acceptance.server.git.receive;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.PushOneCommit;
import com.google.gerrit.acceptance.testsuite.request.RequestScopeOperations;
import com.google.gerrit.extensions.annotations.Exports;
import com.google.gerrit.extensions.api.changes.DraftInput;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.gerrit.extensions.validators.CommentValidationFailure;
import com.google.gerrit.extensions.validators.CommentValidationListener;
import com.google.gerrit.extensions.validators.CommentValidationListener.CommentForValidation;
import com.google.gerrit.extensions.validators.CommentValidationListener.CommentType;
import com.google.gerrit.server.plugincontext.PluginSetContext;
import com.google.inject.Inject;
import com.google.inject.Module;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** Tests comment validation when publishing drafts via the {@code --publish-comments} option. */
public class ReceiveCommitsCommentValidationIT extends AbstractDaemonTest {
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
  public void validateCommentsInReceiveCommits_commentOK() throws Exception {
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    String commentText = "this comment is ok";
    DraftInput comment = newDraft(commentText);
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).hasSize(1);
    assertThat(getValidationCalls())
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, commentText)));
  }

  @Test
  public void validateCommentsInReceiveCommits_commentRejected() throws Exception {
    PushOneCommit.Result result = createChange();
    String changeId = result.getChangeId();
    String revId = result.getCommit().getName();
    String commentText = "this comment will be rejected";
    DraftInput comment = newDraft(commentText);
    addDraft(changeId, revId, comment);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    amendChange(changeId, "refs/for/master%publish-comments", admin, testRepo);
    assertThat(getPublishedComments(result.getChangeId())).isEmpty();
    assertThat(getValidationCalls())
        .isEqualTo(
            ImmutableList.of(
                CommentForValidation.create(CommentType.REVIEW_COMMENT, commentText)));
  }

  private List<CommentForValidation> getValidationCalls() {
    return ((TestCommentValidationListener) commentValidationListeners.iterator().next().get())
        .arguments;
  }
}
