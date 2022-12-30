package com.google.gerrit.server.mail.send;

import com.google.common.flogger.FluentLogger;
import com.google.gerrit.exceptions.EmailException;
import com.google.gerrit.extensions.events.LifecycleListener;
import com.google.gerrit.lifecycle.LifecycleModule;
import com.google.gerrit.proto.Entities.EmailTask;
import com.google.gerrit.server.mail.EmailTaskConverter;
import com.google.gerrit.server.mail.EmailTaskDispatcher;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An email dispatcher that enqueues the {@link EmailTask} into an in-memory queue - {@link Queue}.
 * The email queue is monitored by a background thread (see {@link Listener}) for handling the email
 * events.
 */
@Singleton
public class InMemoryEmailTaskDispatcher implements EmailTaskDispatcher {
  private final Queue emailTaskQueue;

  @Inject
  public InMemoryEmailTaskDispatcher(Queue emailTaskQueue) {
    this.emailTaskQueue = emailTaskQueue;
  }

  @Override
  public void dispatch(EmailTask emailTask) {
    synchronized (emailTaskQueue) {
      emailTaskQueue.enqueue(emailTask);
      emailTaskQueue.notify();
    }
  }

  /**
   * An In-memory handler that starts a background thread which listens to dispatched email tasks.
   */
  @Singleton
  public static class Listener implements LifecycleListener {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final Consumer emailTaskConsumer;
    private final Queue emailTaskQueue;

    @Inject
    public Listener(Consumer emailTaskConsumer, Queue emailTaskQueue) {
      this.emailTaskConsumer = emailTaskConsumer;
      this.emailTaskQueue = emailTaskQueue;
    }

    public static class EmailTaskDispatcherModule extends LifecycleModule {
      @Override
      protected void configure() {
        bind(EmailTaskDispatcher.class).to(InMemoryEmailTaskDispatcher.class);
        listener().to(Listener.class);
      }
    }

    @Override
    public void start() {
      // Run the email task consumer in a background thread.
      new Thread(
              () -> {
                try {
                  while (true) {
                    synchronized (emailTaskQueue) {
                      emailTaskQueue.wait();
                      emailTaskConsumer.consumeEvents();
                    }
                  }
                } catch (InterruptedException e) {
                  logger.atSevere().withCause(e).log("This did not work");
                }
              })
          .start();
    }

    @Override
    public void stop() {}
  }

  /** A shared data structure for storing email task events. */
  @Singleton
  public static class Queue {
    private List<EmailTask> emailQueue = Collections.synchronizedList(new ArrayList<>());

    public void enqueue(EmailTask emailTask) {
      emailQueue.add(emailTask);
    }

    public List<EmailTask> get() {
      List<EmailTask> clonedList = emailQueue.stream().collect(Collectors.toList());
      emailQueue = Collections.synchronizedList(new ArrayList<>());
      return clonedList;
    }
  }

  /** Handles email task events stored in the {@link Queue}. */
  @Singleton
  public static class Consumer {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final Queue emailTaskQueue;
    private final EmailTaskConverter.Args args;

    @Inject
    public Consumer(Queue emailTaskQueue, EmailTaskConverter.Args args) {
      this.emailTaskQueue = emailTaskQueue;
      this.args = args;
    }

    public void consumeEvents() {
      for (EmailTask emailTask : emailTaskQueue.get()) {
        try {
          EmailTaskConverter.getInstance(emailTask.getEventType(), args).convert(emailTask).send();
        } catch (EmailException e) {
          logger.atSevere().withCause(e).log("Failed to send the email");
        }
      }
    }
  }
}
