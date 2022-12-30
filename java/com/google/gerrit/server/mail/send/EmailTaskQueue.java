package com.google.gerrit.server.mail.send;

import com.google.gerrit.proto.Entities.EmailTask;
import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmailTaskQueue {
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
