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

import com.google.gerrit.entities.Account;
import com.google.gerrit.entities.Project;
import com.google.gerrit.entities.Workspace;
import com.google.gerrit.extensions.api.projects.WorkspaceApi;
import com.google.gerrit.extensions.api.projects.Workspaces;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.project.ProjectCache;
import com.google.gerrit.server.project.WorkspaceResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
class WorkspacesImpl implements Workspaces {
  private final WorkspaceApiImpl.Factory api;
  private final ProjectCache projectCache;
  private final Provider<CurrentUser> user;

  @Inject
  WorkspacesImpl(
      WorkspaceApiImpl.Factory api, ProjectCache projectCache, Provider<CurrentUser> user) {
    this.api = api;
    this.projectCache = projectCache;
    this.user = user;
  }

  @Override
  public WorkspaceApi id(String project, int accountId, String wsName) throws RestApiException {
    try {
      Workspace.Id id = Workspace.id(Account.id(accountId), Project.nameKey(project), wsName);
      WorkspaceResource rsrc =
          new WorkspaceResource(
              id, projectCache.get(id.project()).get(), user.get().asIdentifiedUser());
      return api.create(rsrc);
    } catch (Exception e) {
      throw asRestApiException("Cannot retrieve project", e);
    }
  }
}
