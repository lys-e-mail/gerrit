package com.google.gerrit.server.mail.send;

import com.google.gerrit.server.mail.send.EmailTaskQueue.EmailEntity;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EmailTaskDispatcher {
  private final EmailTaskQueue emailTaskQueue;

  @Inject
  public EmailTaskDispatcher(EmailTaskQueue emailTaskQueue) {
    this.emailTaskQueue = emailTaskQueue;
  }

  public void dispatch() {
    synchronized (emailTaskQueue) {
      emailTaskQueue.enqueue(new EmailEntity("REVERT"));
      emailTaskQueue.notify();
    }
  }
}
