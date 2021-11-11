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

package com.google.gerrit.server.access;

import com.google.gerrit.extensions.api.access.ProjectAccessInfo;
import com.google.gerrit.extensions.restapi.ResourceConflictException;
import com.google.gerrit.extensions.restapi.ResourceNotFoundException;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.extensions.restapi.TopLevelResource;
import com.google.gerrit.reviewdb.client.Project;
import com.google.gerrit.server.project.GetAccess;
import com.google.inject.Inject;

import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ListAccess implements RestReadView<TopLevelResource> {

  @Option(name = "--project", aliases = {"-p"}, metaVar = "PROJECT",
      usage = "projects for which the access rights should be returned")
  private List<String> projects = new ArrayList<>();

  private final GetAccess getAccess;

  @Inject
  public ListAccess(GetAccess getAccess) {
    this.getAccess = getAccess;
  }

  @Override
  public Map<String, ProjectAccessInfo> apply(TopLevelResource resource)
      throws ResourceNotFoundException, ResourceConflictException, IOException {
    Map<String, ProjectAccessInfo> access = new TreeMap<>();
    for (String p : projects) {
      Project.NameKey projectName = new Project.NameKey(p);
      access.put(p, getAccess.apply(projectName));
    }
    return access;
  }

<<<<<<< HEAD   (b8813c Merge branch 'stable-2.12' into stable-2.13)
=======
  private ProjectControl open(Project.NameKey projectName)
      throws ResourceNotFoundException, IOException {
    try {
      return projectControlFactory.validateFor(projectName,
          ProjectControl.OWNER | ProjectControl.VISIBLE, self.get());
    } catch (NoSuchProjectException e) {
      throw new ResourceNotFoundException(projectName.get());
    }
  }

  public class ProjectAccessInfo {
    public String revision;
    public ProjectInfo inheritsFrom;
    public Map<String, AccessSectionInfo> local;
    public Boolean isOwner;
    public Set<String> ownerOf;
    public Boolean canUpload;
    public Boolean canAdd;
    public Boolean configVisible;

    public ProjectAccessInfo(ProjectControl pc, ProjectConfig config) {
      final RefControl metaConfigControl =
          pc.controlForRef(RefNames.REFS_CONFIG);
      local = Maps.newHashMap();
      ownerOf = Sets.newHashSet();
      Map<AccountGroup.UUID, Boolean> visibleGroups = new HashMap<>();

      for (AccessSection section : config.getAccessSections()) {
        String name = section.getName();
        if (AccessSection.GLOBAL_CAPABILITIES.equals(name)) {
          if (pc.isOwner()) {
            local.put(name, new AccessSectionInfo(section));
            ownerOf.add(name);

          } else if (metaConfigControl.isVisible()) {
            local.put(section.getName(), new AccessSectionInfo(section));
          }

        } else if (RefConfigSection.isValid(name)) {
          RefControl rc = pc.controlForRef(name);
          if (rc.isOwner()) {
            local.put(name, new AccessSectionInfo(section));
            ownerOf.add(name);

          } else if (metaConfigControl.isVisible()) {
            local.put(name, new AccessSectionInfo(section));

          } else if (rc.isVisible()) {
            // Filter the section to only add rules describing groups that
            // are visible to the current-user. This includes any group the
            // user is a member of, as well as groups they own or that
            // are visible to all users.

            AccessSection dst = null;
            for (Permission srcPerm : section.getPermissions()) {
              Permission dstPerm = null;

              for (PermissionRule srcRule : srcPerm.getRules()) {
                AccountGroup.UUID group = srcRule.getGroup().getUUID();
                if (group == null) {
                  continue;
                }

                Boolean canSeeGroup = visibleGroups.get(group);
                if (canSeeGroup == null) {
                  try {
                    canSeeGroup = groupControlFactory.controlFor(group).isVisible();
                  } catch (NoSuchGroupException e) {
                    canSeeGroup = Boolean.FALSE;
                  }
                  visibleGroups.put(group, canSeeGroup);
                }

                if (canSeeGroup) {
                  if (dstPerm == null) {
                    if (dst == null) {
                      dst = new AccessSection(name);
                      local.put(name, new AccessSectionInfo(dst));
                    }
                    dstPerm = dst.getPermission(srcPerm.getName(), true);
                  }
                  dstPerm.add(srcRule);
                }
              }
            }
          }
        }
      }

      if (ownerOf.isEmpty() && pc.isOwnerAnyRef()) {
        // Special case: If the section list is empty, this project has no current
        // access control information. Rely on what ProjectControl determines
        // is ownership, which probably means falling back to site administrators.
        ownerOf.add(AccessSection.ALL);
      }


      if (config.getRevision() != null) {
        revision = config.getRevision().name();
      }

      ProjectState parent =
          Iterables.getFirst(pc.getProjectState().parents(), null);
      if (parent != null) {
        inheritsFrom = projectJson.format(parent.getProject());
      }

      if (pc.getProject().getNameKey().equals(allProjectsName)) {
        if (pc.isOwner()) {
          ownerOf.add(AccessSection.GLOBAL_CAPABILITIES);
        }
      }

      isOwner = toBoolean(pc.isOwner());
      canUpload = toBoolean(pc.isOwner()
          || (metaConfigControl.isVisible() && metaConfigControl.canUpload()));
      canAdd = toBoolean(pc.canAddRefs());
      configVisible = pc.isOwner() || metaConfigControl.isVisible();
    }
  }

  public static class AccessSectionInfo {
    public Map<String, PermissionInfo> permissions;

    public AccessSectionInfo(AccessSection section) {
      permissions = Maps.newHashMap();
      for (Permission p : section.getPermissions()) {
        permissions.put(p.getName(), new PermissionInfo(p));
      }
    }
  }

  public static class PermissionInfo {
    public String label;
    public Boolean exclusive;
    public Map<String, PermissionRuleInfo> rules;

    public PermissionInfo(Permission permission) {
      label = permission.getLabel();
      exclusive = toBoolean(permission.getExclusiveGroup());
      rules = Maps.newHashMap();
      for (PermissionRule r : permission.getRules()) {
        rules.putIfAbsent(r.getGroup().getUUID().get(), new PermissionRuleInfo(r)); // First entry for the group wins
      }
    }
  }

  public static class PermissionRuleInfo {
    public PermissionRule.Action action;
    public Boolean force;
    public Integer min;
    public Integer max;


    public PermissionRuleInfo(PermissionRule rule) {
      action = rule.getAction();
      force = toBoolean(rule.getForce());
      if (hasRange(rule)) {
        min = rule.getMin();
        max = rule.getMax();
      }
    }

    private boolean hasRange(PermissionRule rule) {
      return (!(rule.getMin() == null || rule.getMin() == 0))
          || (!(rule.getMax() == null || rule.getMax() == 0));
    }
  }

  private static Boolean toBoolean(boolean value) {
    return value ? true : null;
  }
>>>>>>> BRANCH (44ef71 Merge branch 'stable-2.11' into stable-2.12)
}
