package com.google.gerrit.server.mail.send;

import com.google.common.flogger.FluentLogger;
import com.google.gerrit.exceptions.EmailException;
import com.google.gerrit.proto.Entities.EmailTask;
import com.google.gerrit.server.mail.EmailTaskConverter;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/** Handles email events. This should run on a background thread. */
@Singleton
public class EmailTaskConsumer {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final EmailTaskQueue emailTaskQueue;
  private final EmailTaskConverter.Args args;

  @Inject
  public EmailTaskConsumer(EmailTaskQueue emailTaskQueue, EmailTaskConverter.Args args) {
    this.emailTaskQueue = emailTaskQueue;
    this.args = args;
  }

  public void handleQueue() {
    for (EmailTask emailTask : emailTaskQueue.get()) {
      try {
        EmailTaskConverter.getInstance(emailTask.getEventType(), args).convert(emailTask).send();
      } catch (EmailException e) {
        logger.atSevere().withCause(e).log("Failed to send the email");
      }
    }
  }
}
