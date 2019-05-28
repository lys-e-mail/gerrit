package com.google.gerrit.extensions.validators;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/** XXX */
@AutoValue
public abstract class CommentValidationResult {
  public enum Status {
    VALID,
    INVALID
  }

  public static CommentValidationResult create(Status status, ImmutableList<String> messages) {
    return new AutoValue_CommentValidationResult(status, messages);
  }

  public abstract Status getStatus();
  public abstract ImmutableList<String> getMessages();
}
