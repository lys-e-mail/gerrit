// Copyright (C) 2021 The Android Open Source Project
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

package com.google.gerrit.acceptance.rest.project;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.acceptance.testsuite.project.TestProjectUpdate.block;
import static com.google.gerrit.server.group.SystemGroupBackend.REGISTERED_USERS;

import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.RestResponse;
import com.google.gerrit.acceptance.testsuite.project.ProjectOperations;
import com.google.gerrit.entities.Permission;
import com.google.gerrit.extensions.api.access.ProjectAccessInfo;
import com.google.gerrit.extensions.api.projects.ProjectInput;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import java.util.Map;
import org.junit.Test;

public class AccessIT extends AbstractDaemonTest {
  @Inject private ProjectOperations projectOperations;

  @Test
  public void listAccessWithoutSpecifyingProject() throws Exception {
    RestResponse r = adminRestSession.get("/access/");
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject).isEmpty();
  }

  @Test
  public void listAccessWithoutSpecifyingAnEmptyProjectName() throws Exception {
    RestResponse r = adminRestSession.get("/access/?p=");
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject).isEmpty();
  }

  @Test
  public void listAccessForNonExistingProject() throws Exception {
    RestResponse r = adminRestSession.get("/access/?project=non-existing");
    r.assertNotFound();
    assertThat(r.getEntityContent()).isEqualTo("non-existing");
  }

  @Test
<<<<<<< HEAD   (7d8239 Merge "Remove support for ElasticSearch indexes" into stable)
  public void listAccessForNonVisibleProject() throws Exception {
=======
  public void grantRevertPermissionDoesntDeleteAdminsPreferences() throws Exception {
    GroupReference registeredUsers = systemGroupBackend.getGroup(REGISTERED_USERS);
    GroupReference otherGroup = systemGroupBackend.getGroup(ANONYMOUS_USERS);

    try (Repository repo = repoManager.openRepository(newProjectName)) {
      MetaDataUpdate md = new MetaDataUpdate(GitReferenceUpdated.DISABLED, newProjectName, repo);
      ProjectConfig projectConfig = projectConfigFactory.read(md);
      projectConfig.upsertAccessSection(
          AccessSection.HEADS,
          heads -> {
            grant(projectConfig, heads, Permission.REVERT, registeredUsers);
            grant(projectConfig, heads, Permission.REVERT, otherGroup);
          });
      md.getCommitBuilder().setAuthor(admin.newIdent());
      md.getCommitBuilder().setCommitter(admin.newIdent());
      md.setMessage("Add revert permission for all registered users\n");

      projectConfig.commit(md);
    }
    projectCache.evict(newProjectName);
    ProjectAccessInfo expected = pApi().access();

    grantRevertPermission.execute(newProjectName);
    projectCache.evictAndReindex(newProjectName);
    ProjectAccessInfo actual = pApi().access();
    // Permissions don't change
    assertThat(expected.local).isEqualTo(actual.local);
  }

  @Test
  public void grantRevertPermissionOnlyWorksOnce() throws Exception {
    grantRevertPermission.execute(newProjectName);
    grantRevertPermission.execute(newProjectName);

    try (Repository repo = repoManager.openRepository(newProjectName)) {
      MetaDataUpdate md = new MetaDataUpdate(GitReferenceUpdated.DISABLED, newProjectName, repo);
      ProjectConfig projectConfig = projectConfigFactory.read(md);
      AccessSection all = projectConfig.getAccessSection(AccessSection.ALL);

      Permission permission = all.getPermission(Permission.REVERT);
      assertThat(permission.getRules()).hasSize(1);
    }
  }

  @Test
  public void getDefaultInheritance() throws Exception {
    String inheritedName = pApi().access().inheritsFrom.name;
    assertThat(inheritedName).isEqualTo(AllProjectsNameProvider.DEFAULT);
  }

  private Registration newFileHistoryWebLink() {
    FileHistoryWebLink weblink =
        new FileHistoryWebLink() {
          @Override
          public WebLinkInfo getFileHistoryWebLink(
              String projectName, String revision, String fileName) {
            return new WebLinkInfo(
                "name", "imageURL", "http://view/" + projectName + "/" + fileName);
          }
        };
    return extensionRegistry.newRegistration().add(weblink);
  }

  @Test
  public void webLink() throws Exception {
    try (Registration registration = newFileHistoryWebLink()) {
      ProjectAccessInfo info = pApi().access();
      assertThat(info.configWebLinks).hasSize(1);
      assertThat(info.configWebLinks.get(0).url)
          .isEqualTo("http://view/" + newProjectName + "/project.config");
    }
  }

  @Test
  public void webLinkNoRefsMetaConfig() throws Exception {
    try (Repository repo = repoManager.openRepository(newProjectName);
        Registration registration = newFileHistoryWebLink()) {
      RefUpdate u = repo.updateRef(RefNames.REFS_CONFIG);
      u.setForceUpdate(true);
      assertThat(u.delete()).isEqualTo(Result.FORCED);

      // This should not crash.
      pApi().access();
    }
  }

  @Test
  public void addAccessSection() throws Exception {
    RevCommit initialHead = projectOperations.project(newProjectName).getHead(RefNames.REFS_CONFIG);

    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    pApi().access(accessInput);

    assertThat(pApi().access().local).isEqualTo(accessInput.add);

    RevCommit updatedHead = projectOperations.project(newProjectName).getHead(RefNames.REFS_CONFIG);
    eventRecorder.assertRefUpdatedEvents(
        newProjectName.get(), RefNames.REFS_CONFIG, null, initialHead, initialHead, updatedHead);
  }

  @Test
  public void addAccessSectionForPluginPermission() throws Exception {
    try (Registration registration =
        extensionRegistry
            .newRegistration()
            .add(
                new PluginProjectPermissionDefinition() {
                  @Override
                  public String getDescription() {
                    return "A Plugin Project Permission";
                  }
                },
                "fooPermission")) {
      ProjectAccessInput accessInput = newProjectAccessInput();
      AccessSectionInfo accessSectionInfo = newAccessSectionInfo();

      PermissionInfo foo = newPermissionInfo();
      PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
      foo.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
      accessSectionInfo.permissions.put(
          "plugin-" + ExtensionRegistry.PLUGIN_NAME + "-fooPermission", foo);

      accessInput.add.put(REFS_HEADS, accessSectionInfo);
      ProjectAccessInfo updatedAccessSectionInfo = pApi().access(accessInput);
      assertThat(updatedAccessSectionInfo.local).isEqualTo(accessInput.add);

      assertThat(pApi().access().local).isEqualTo(accessInput.add);
    }
  }

  @Test
  public void addAccessSectionWithInvalidPermission() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    PermissionInfo push = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    push.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSectionInfo.permissions.put("Invalid Permission", push);

    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    BadRequestException ex =
        assertThrows(BadRequestException.class, () -> pApi().access(accessInput));
    assertThat(ex).hasMessageThat().isEqualTo("Unknown permission: Invalid Permission");
  }

  @Test
  public void addAccessSectionWithInvalidLabelPermission() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    PermissionInfo push = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    push.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSectionInfo.permissions.put("label-Invalid Permission", push);

    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    BadRequestException ex =
        assertThrows(BadRequestException.class, () -> pApi().access(accessInput));
    assertThat(ex).hasMessageThat().isEqualTo("Unknown permission: label-Invalid Permission");
  }

  @Test
  public void createAccessChangeNop() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    assertThrows(BadRequestException.class, () -> pApi().accessChange(accessInput));
  }

  @Test
  public void createAccessChangeEmptyConfig() throws Exception {
    try (Repository repo = repoManager.openRepository(newProjectName)) {
      RefUpdate ru = repo.updateRef(RefNames.REFS_CONFIG);
      ru.setForceUpdate(true);
      assertThat(ru.delete()).isEqualTo(Result.FORCED);

      ProjectAccessInput accessInput = newProjectAccessInput();
      AccessSectionInfo accessSection = newAccessSectionInfo();
      PermissionInfo read = newPermissionInfo();
      PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.BLOCK, false);
      read.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
      accessSection.permissions.put(Permission.READ, read);
      accessInput.add.put(REFS_HEADS, accessSection);

      ChangeInfo out = pApi().accessChange(accessInput);
      assertThat(out.status).isEqualTo(ChangeStatus.NEW);
    }
  }

  @Test
  public void createAccessChange() throws Exception {
>>>>>>> BRANCH (d736a7 Set version to 3.4.3-SNAPSHOT)
    projectOperations
        .project(project)
        .forUpdate()
        .add(block(Permission.READ).ref("refs/*").group(REGISTERED_USERS))
        .update();

    RestResponse r = userRestSession.get("/access/?project=" + project.get());
    r.assertNotFound();
    assertThat(r.getEntityContent()).isEqualTo(project.get());
  }

  @Test
  public void listAccess() throws Exception {
    RestResponse r = adminRestSession.get("/access/?project=" + project.get());
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject.keySet()).containsExactly(project.get());
  }

  @Test
  public void listAccess_withUrlEncodedProjectName() throws Exception {
    String fooBarBazProjectName = name("foo/bar/baz");
    ProjectInput in = new ProjectInput();
    in.name = fooBarBazProjectName;
    gApi.projects().create(in);

    RestResponse r =
        adminRestSession.get("/access/?project=" + IdString.fromDecoded(fooBarBazProjectName));
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject.keySet()).containsExactly(fooBarBazProjectName);
  }

  @Test
  public void listAccess_projectNameAreTrimmed() throws Exception {
    RestResponse r =
        adminRestSession.get("/access/?project=" + IdString.fromDecoded(" " + project.get() + " "));
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject.keySet()).containsExactly(project.get());
  }
}
