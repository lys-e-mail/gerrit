package com.google.gerrit.server.update;

/** Thrown when comment validation rejected a comment, preventing it from being published. */
public class CommentRejectedException extends Exception {
  // XXX Serialization ID?

  public CommentRejectedException(String message) {
    super(message);
  }
}
