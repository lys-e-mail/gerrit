// Copyright (C) 2018 The Android Open Source Project
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

package com.google.gerrit.acceptance.git;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.acceptance.GitUtil.assertPushOk;
import static com.google.gerrit.acceptance.GitUtil.pushHead;

import com.google.gerrit.acceptance.GitUtil;
import com.google.gerrit.acceptance.TestProjectInput;
import com.google.gerrit.acceptance.UseLocalDisk;
import com.google.gerrit.entities.Project;
import com.google.gerrit.entities.Workspace;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.junit.Test;

@UseLocalDisk
public class GitOverHttpServletIT extends AbstractGitOverHttpServlet {

  @Test
  @TestProjectInput(createEmptyCommit = false)
  public void workspace() throws Exception {
    testRepo =
        GitUtil.cloneProject(
            Project.nameKey(project.get() + "~ws1"),
            admin.getHttpUrl(server) + "/" + project.get() + "~ws1");

    RevCommit c = testRepo.commit().message("Initial commit22").insertChangeId().create();
    //    String id = GitUtil.getChangeId(testRepo, c).get();
    testRepo.reset(c);

    String r = "refs/heads/foo-master";
    PushResult pr = pushHead(testRepo, r, false);
    assertPushOk(pr, r);

    // contained in ws
    try (Repository repo = repoManager.openWorkspace(Workspace.id(admin.id(), project, "ws1"))) {
      assertThat(repo.getObjectDatabase().has(c)).isTrue();
    }

    // not contained in repo
    try (Repository repo = repoManager.openRepository(project)) {
      assertThat(repo.getObjectDatabase().has(c)).isFalse();
    }
  }
}
