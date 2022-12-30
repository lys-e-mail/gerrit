package com.google.gerrit.server.mail.send;

import com.google.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EmailTaskQueue {
  private List<EmailEntity> emailQueue = Collections.synchronizedList(new ArrayList<>());

  public void enqueue(EmailEntity entity) {
    emailQueue.add(entity);
  }

  public List<EmailEntity> get() {
    List<EmailEntity> clonedList = emailQueue.stream().collect(Collectors.toList());
    emailQueue = Collections.synchronizedList(new ArrayList<>());
    return clonedList;
  }

  public static class EmailEntity {
    String type;

    public EmailEntity(String type) {
      this.type = type;
    }
  }
}
