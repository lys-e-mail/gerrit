// Copyright (C) 2013 The Android Open Source Project
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

package com.google.gerrit.server.api.projects;

import static com.google.gerrit.server.api.ApiUtil.asRestApiException;
import static java.util.stream.Collectors.toList;

import com.google.gerrit.extensions.api.projects.BranchInfo;
import com.google.gerrit.extensions.api.projects.CommitApi;
import com.google.gerrit.extensions.api.projects.ProjectApi;
import com.google.gerrit.extensions.api.projects.WorkspaceApi;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gerrit.server.git.GitRepositoryManager;
import com.google.gerrit.server.project.WorkspaceResource;
import com.google.gerrit.server.restapi.project.CommitsCollection;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import java.util.List;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class WorkspaceApiImpl implements WorkspaceApi {
  interface Factory {
    WorkspaceApiImpl create(WorkspaceResource workspace);
  }

  private final GitRepositoryManager repositoryManager;
  private final CommitApiImpl.Factory commitApi;
  private final CommitsCollection commitsCollection;
  private final WorkspaceResource workspace;

  @AssistedInject
  WorkspaceApiImpl(
      GitRepositoryManager repositoryManager,
      CommitApiImpl.Factory commitApi,
      CommitsCollection commitsCollection,
      @Assisted WorkspaceResource workspace) {
    this.repositoryManager = repositoryManager;
    this.commitApi = commitApi;
    this.commitsCollection = commitsCollection;
    this.workspace = workspace;
  }

  @Override
  public ProjectApi.ListRefsRequest<BranchInfo> branches() {
    return new ProjectApi.ListRefsRequest<>() {
      @Override
      public List<BranchInfo> get() throws RestApiException {
        try (Repository repo = repositoryManager.openWorkspace(workspace.getWorkspace())) {
          return repo.getRefDatabase().getRefs().stream()
              .map(WorkspaceApiImpl::toBranchInfo)
              .collect(toList());
        } catch (Exception e) {
          throw asRestApiException("Cannot list branches", e);
        }
      }
    };
  }

  @Override
  public CommitApi commit(String commit) throws RestApiException {
    try {
      return commitApi.create(commitsCollection.parse(workspace, IdString.fromDecoded(commit)));
    } catch (Exception e) {
      throw asRestApiException("Cannot parse commit", e);
    }
  }

  private static BranchInfo toBranchInfo(Ref r) {
    BranchInfo i = new BranchInfo();
    i.ref = r.getName();
    i.revision = r.getObjectId().toString();
    return i;
  }
}
