package com.google.gerrit.server.mail.send;

import com.google.gerrit.proto.Entities.EmailTask;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EmailTaskDispatcher {
  private final EmailTaskQueue emailTaskQueue;

  @Inject
  public EmailTaskDispatcher(EmailTaskQueue emailTaskQueue) {
    this.emailTaskQueue = emailTaskQueue;
  }

  public void dispatch(EmailTask emailTask) {
    synchronized (emailTaskQueue) {
      emailTaskQueue.enqueue(emailTask);
      emailTaskQueue.notify();
    }
  }
}
