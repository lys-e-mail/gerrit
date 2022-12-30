package com.google.gerrit.server.mail;

import com.google.common.flogger.FluentLogger;
import com.google.gerrit.extensions.events.LifecycleListener;
import com.google.gerrit.lifecycle.LifecycleModule;
import com.google.gerrit.server.mail.send.EmailTaskConsumer;
import com.google.gerrit.server.mail.send.EmailTaskQueue;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EmailTaskHandler implements LifecycleListener {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final EmailTaskConsumer emailTaskConsumer;
  private final EmailTaskQueue emailTaskQueue;

  @Inject
  public EmailTaskHandler(EmailTaskConsumer emailTaskConsumer, EmailTaskQueue emailTaskQueue) {
    this.emailTaskConsumer = emailTaskConsumer;
    this.emailTaskQueue = emailTaskQueue;
  }

  public static class EmailTaskHandlerModule extends LifecycleModule {
    @Override
    protected void configure() {
      listener().to(EmailTaskHandler.class);
    }
  }

  @Override
  public void start() {
    // Run the email task consumer on a background thread
    Thread t =
        new Thread(
            () -> {
              try {
                while (true) {
                  synchronized (emailTaskQueue) {
                    emailTaskQueue.wait();
                    // when notified
                    emailTaskConsumer.handleQueue();
                  }
                }
              } catch (InterruptedException e) {
                logger.atSevere().withCause(e).log("This did not work");
              }
            });

    t.start();
  }

  @Override
  public void stop() {}
}
