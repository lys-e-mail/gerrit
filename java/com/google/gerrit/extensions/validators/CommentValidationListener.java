package com.google.gerrit.extensions.validators;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.gerrit.extensions.annotations.ExtensionPoint;

/**
 * XXX
 */
@ExtensionPoint
public interface CommentValidationListener {

  // XXX Clash with MailComment.CommentType?
  enum CommentType {
    REVIEW_COMMENT,
    REVIEW_MESSAGE,
    EMAIL_MESSAGE
  }

  // XXX Location?
  @AutoValue
  abstract class CommentForValidation {
    public static CommentForValidation create(CommentType type,  String text) {
      return new AutoValue_CommentValidationListener_CommentForValidation(type, text);
    }

    public abstract CommentType getType();
    public abstract String getText();
  }

  /** XXX */
  CommentValidationResult validateComments(ImmutableList<CommentForValidation> comments);
}
