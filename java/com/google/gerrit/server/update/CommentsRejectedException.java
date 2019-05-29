package com.google.gerrit.server.update;

import com.google.common.collect.ImmutableList;
import com.google.gerrit.extensions.validators.CommentValidationFailure;
import java.util.Collection;

/** Thrown when comment validation rejected a comment, preventing it from being published. */
public class CommentsRejectedException extends Exception {
  // XXX Serialization ID?
  // XXX Set message via super(...)?

  private final ImmutableList<CommentValidationFailure> commentValidationFailures;

  public CommentsRejectedException(Collection<CommentValidationFailure> commentValidationFailures) {
    this.commentValidationFailures = ImmutableList.copyOf(commentValidationFailures);
  }

  /** XXX Unused? */
  public ImmutableList<CommentValidationFailure> getCommentValidationFailures() {
    return commentValidationFailures;
  }
}
