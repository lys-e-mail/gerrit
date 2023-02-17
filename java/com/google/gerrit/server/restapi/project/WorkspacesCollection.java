// Copyright (C) 2012 The Android Open Source Project
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

package com.google.gerrit.server.restapi.project;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import com.google.gerrit.common.Nullable;
import com.google.gerrit.entities.Project;
import com.google.gerrit.entities.Workspace;
import com.google.gerrit.extensions.registration.DynamicMap;
import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gerrit.extensions.restapi.ResourceNotFoundException;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gerrit.extensions.restapi.RestCollection;
import com.google.gerrit.extensions.restapi.RestView;
import com.google.gerrit.extensions.restapi.TopLevelResource;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.IdentifiedUser;
import com.google.gerrit.server.permissions.PermissionBackendException;
import com.google.gerrit.server.project.ProjectCache;
import com.google.gerrit.server.project.WorkspaceResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.io.IOException;
import java.util.List;

public class WorkspacesCollection implements RestCollection<TopLevelResource, WorkspaceResource> {
  private final DynamicMap<RestView<WorkspaceResource>> views;
  private final ProjectCache projectCache;
  private final Provider<CurrentUser> user;

  @Inject
  public WorkspacesCollection(
      DynamicMap<RestView<WorkspaceResource>> views,
      ProjectCache projectCache,
      Provider<CurrentUser> user) {
    this.views = views;
    this.projectCache = projectCache;
    this.user = user;
  }

  @Override
  public RestView<TopLevelResource> list() {
    throw new UnsupportedOperationException("not supported");
  }

  @Override
  public WorkspaceResource parse(TopLevelResource parent, IdString id)
      throws RestApiException, IOException, PermissionBackendException {
    WorkspaceResource rsrc = _parse(id.get());
    if (rsrc == null) {
      throw new ResourceNotFoundException(id);
    }
    return rsrc;
  }

  @Nullable
  private WorkspaceResource _parse(String id) throws AuthException {
    List<String> params = ImmutableList.copyOf(Splitter.on("~").split(id));
    IdentifiedUser u = user.get().asIdentifiedUser();
    if (Ints.tryParse(params.get(0)) != u.getAccountId().get()) {
      throw new AuthException(params + " not accessible by user " + u.getAccountId());
    }
    Project.NameKey project = Project.nameKey(params.get(1));

    return new WorkspaceResource(
        Workspace.id(u.getAccountId(), project, params.get(2)), projectCache.get(project).get(), u);
  }

  @Override
  public DynamicMap<RestView<WorkspaceResource>> views() {
    return views;
  }
}
