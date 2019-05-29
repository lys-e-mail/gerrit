package com.google.gerrit.extensions.validators;

import com.google.auto.value.AutoValue;
import com.google.gerrit.extensions.validators.CommentValidationListener.CommentForValidation;

/** XXX */
@AutoValue
public abstract class CommentValidationFailure {
  static CommentValidationFailure create(CommentForValidation commentForValidation, String message) {
    return new AutoValue_CommentValidationFailure(commentForValidation, message);
  }

  public abstract CommentForValidation getComment();
  public abstract String getMessage();
}
