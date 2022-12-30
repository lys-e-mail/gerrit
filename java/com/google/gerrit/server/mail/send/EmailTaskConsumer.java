package com.google.gerrit.server.mail.send;

import com.google.gerrit.server.mail.send.EmailTaskQueue.EmailEntity;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;

/** Handles email events. This should run on a background thread. */
@Singleton
public class EmailTaskConsumer {
  private EmailTaskQueue emailTaskQueue;

  @Inject
  public EmailTaskConsumer(EmailTaskQueue emailTaskQueue) {
    this.emailTaskQueue = emailTaskQueue;
  }

  public void handleQueue() {
    List<EmailEntity> emailEntities = emailTaskQueue.get();
    if (emailEntities.size() == 1) {
      System.out.println("Yes");
    }
    // do your handling: spawn a thread to do the processing in the background
  }
}
