// Copyright (C) 2017 The Android Open Source Project
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

package com.google.gerrit.server.change;

import com.google.gerrit.extensions.restapi.BadRequestException;
import com.google.gerrit.extensions.restapi.ResourceConflictException;
import com.google.gerrit.reviewdb.client.PatchSet;
import com.google.gerrit.server.git.PureRevertCache;
import com.google.gerrit.server.notedb.ChangeNotes;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.Optional;
import org.eclipse.jgit.errors.InvalidObjectIdException;
import org.eclipse.jgit.lib.ObjectId;

/** Can check if a change is a pure revert (= a revert with no further modifications). */
@Singleton
public class PureRevert {
  private final PureRevertCache pureRevertCache;

  @Inject
  PureRevert(PureRevertCache pureRevertCache) {
    this.pureRevertCache = pureRevertCache;
  }

  public boolean get(ChangeNotes notes, Optional<String> claimedOriginal)
      throws IOException, BadRequestException, ResourceConflictException {
    PatchSet currentPatchSet = notes.getCurrentPatchSet();
    if (currentPatchSet == null) {
      throw new ResourceConflictException("current revision is missing");
    }
    if (!claimedOriginal.isPresent()) {
      return pureRevertCache.isPureRevert(notes);
    }

<<<<<<< HEAD   (6b4abb Update git submodules)
    ObjectId claimedOriginalObjectId;
    try {
      claimedOriginalObjectId = ObjectId.fromString(claimedOriginal.get());
    } catch (InvalidObjectIdException e) {
      throw new BadRequestException("invalid object ID");
=======
    try (Repository repo = repoManager.openRepository(notes.getProjectName());
        ObjectInserter oi = repo.newObjectInserter();
        RevWalk rw = new RevWalk(repo)) {
      RevCommit claimedOriginalCommit;
      try {
        claimedOriginalCommit = rw.parseCommit(ObjectId.fromString(claimedOriginal));
      } catch (InvalidObjectIdException | MissingObjectException e) {
        throw new BadRequestException("invalid object ID", e);
      }
      if (claimedOriginalCommit.getParentCount() == 0) {
        throw new BadRequestException("can't check against initial commit");
      }
      RevCommit claimedRevertCommit =
          rw.parseCommit(ObjectId.fromString(currentPatchSet.getRevision().get()));
      if (claimedRevertCommit.getParentCount() == 0) {
        throw new BadRequestException("claimed revert has no parents");
      }
      // Rebase claimed revert onto claimed original
      ThreeWayMerger merger =
          mergeUtilFactory
              .create(projectCache.checkedGet(notes.getProjectName()))
              .newThreeWayMerger(oi, repo.getConfig());
      merger.setBase(claimedRevertCommit.getParent(0));
      boolean success = merger.merge(claimedRevertCommit, claimedOriginalCommit);
      if (!success || merger.getResultTreeId() == null) {
        // Merge conflict during rebase
        return new PureRevertInfo(false);
      }

      // Any differences between claimed original's parent and the rebase result indicate that the
      // claimedRevert is not a pure revert but made content changes
      try (DiffFormatter df = new DiffFormatter(new ByteArrayOutputStream())) {
        df.setReader(oi.newReader(), repo.getConfig());
        List<DiffEntry> entries =
            df.scan(claimedOriginalCommit.getParent(0), merger.getResultTreeId());
        return new PureRevertInfo(entries.isEmpty());
      }
>>>>>>> BRANCH (79d0c3 ErrorProne: Enable and fix UnusedException check)
    }

    return pureRevertCache.isPureRevert(
        notes.getProjectName(),
        ObjectId.fromString(notes.getCurrentPatchSet().getRevision().get()),
        claimedOriginalObjectId);
  }
}
