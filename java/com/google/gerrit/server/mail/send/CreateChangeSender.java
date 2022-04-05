// Copyright (C) 2009 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.server.mail.send;

import com.google.gerrit.entities.Change;
import com.google.gerrit.entities.NotifyConfig.NotifyType;
import com.google.gerrit.entities.Project;
import com.google.gerrit.exceptions.EmailException;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

/** Notify interested parties of a brand new change. */
public class CreateChangeSender extends NewChangeSender {
  public interface Factory {
    CreateChangeSender create(Project.NameKey project, Change.Id changeId);
  }

  @Inject
  public CreateChangeSender(
      EmailArguments args, @Assisted Project.NameKey project, @Assisted Change.Id changeId) {
    super(args, newChangeData(args, project, changeId));
  }

  @Override
  protected void init() throws EmailException {
    super.init();

<<<<<<< HEAD   (9aac9a Add property project_prefix)
    includeWatchers(NotifyType.NEW_CHANGES, !change.isWorkInProgress() && !change.isPrivate());
=======
    try {
      // Upgrade watching owners from CC and BCC to TO.
      Watchers matching =
          getWatchers(NotifyType.NEW_CHANGES, !change.isWorkInProgress() && !change.isPrivate());
      // TODO(hiesel): Remove special handling for owners
      StreamSupport.stream(matching.all().accounts.spliterator(), false)
          .filter(this::isOwnerOfProjectOrBranch)
          .forEach(acc -> addWatcher(RecipientType.TO, acc));
      // Add everyone else. Owners added above will not be duplicated.
      add(RecipientType.TO, matching.to);
      add(RecipientType.CC, matching.cc);
      add(RecipientType.BCC, matching.bcc);
    } catch (StorageException err) {
      // Just don't CC everyone. Better to send a partial message to those
      // we already have queued up then to fail deliver entirely to people
      // who have a lower interest in the change.
      logger.atWarning().withCause(err).log("Cannot notify watchers for new change");
    }

>>>>>>> BRANCH (605261 Merge branch 'stable-3.4' into stable-3.5)
    includeWatchers(NotifyType.NEW_PATCHSETS, !change.isWorkInProgress() && !change.isPrivate());
  }
}
