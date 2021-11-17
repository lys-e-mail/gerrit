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
<<<<<<< HEAD   (5a82cb Set version to 3.5.0-SNAPSHOT)
=======
import static com.google.gerrit.server.schema.AclUtil.grant;
import static com.google.gerrit.testing.GerritJUnit.assertThrows;
import static com.google.gerrit.truth.ConfigSubject.assertThat;
import static com.google.gerrit.truth.MapSubject.assertThatMap;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
>>>>>>> BRANCH (76110d Merge branch 'stable-3.3' into stable-3.4)

import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.RestResponse;
import com.google.gerrit.acceptance.testsuite.project.ProjectOperations;
import com.google.gerrit.entities.Permission;
import com.google.gerrit.extensions.api.access.ProjectAccessInfo;
import com.google.gerrit.extensions.api.projects.ProjectInput;
import com.google.gerrit.extensions.restapi.IdString;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
<<<<<<< HEAD   (5a82cb Set version to 3.5.0-SNAPSHOT)
=======
import java.util.HashMap;
import java.util.List;
>>>>>>> BRANCH (76110d Merge branch 'stable-3.3' into stable-3.4)
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
  public void listAccessForNonVisibleProject() throws Exception {
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
<<<<<<< HEAD   (5a82cb Set version to 3.5.0-SNAPSHOT)
  public void listAccess_projectNameAreTrimmed() throws Exception {
    RestResponse r =
        adminRestSession.get("/access/?project=" + IdString.fromDecoded(" " + project.get() + " "));
    r.assertOK();
    Map<String, ProjectAccessInfo> infoByProject =
        newGson()
            .fromJson(r.getReader(), new TypeToken<Map<String, ProjectAccessInfo>>() {}.getType());
    assertThat(infoByProject.keySet()).containsExactly(project.get());
=======
  public void removePermissionRulesAndCleanupEmptyEntries() throws Exception {
    // Add initial permission set
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    pApi().access(accessInput);

    // Remove specific permission rules
    AccessSectionInfo accessSectionToRemove = newAccessSectionInfo();
    PermissionInfo codeReview = newPermissionInfo();
    codeReview.label = LabelId.CODE_REVIEW;
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.DENY, false);
    codeReview.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    pri = new PermissionRuleInfo(PermissionRuleInfo.Action.DENY, false);
    codeReview.rules.put(SystemGroupBackend.PROJECT_OWNERS.get(), pri);
    accessSectionToRemove.permissions.put(Permission.LABEL + LabelId.CODE_REVIEW, codeReview);
    ProjectAccessInput removal = newProjectAccessInput();
    removal.remove.put(REFS_HEADS, accessSectionToRemove);
    pApi().access(removal);

    // Remove locally
    accessInput.add.get(REFS_HEADS).permissions.remove(Permission.LABEL + LabelId.CODE_REVIEW);

    // Check
    assertThat(pApi().access().local).isEqualTo(accessInput.add);
  }

  @Test
  public void getPermissionsWithDisallowedUser() throws Exception {
    // Add initial permission set
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createAccessSectionInfoDenyAll();

    // Disallow READ
    accessInput.add.put(REFS_META_VERSION, accessSectionInfo);
    accessInput.add.put(REFS_DRAFTS, accessSectionInfo);
    accessInput.add.put(REFS_STARRED_CHANGES, accessSectionInfo);
    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    pApi().access(accessInput);

    requestScopeOperations.setApiUser(user.id());
    assertThrows(ResourceNotFoundException.class, () -> pApi().access());
  }

  @Test
  public void setPermissionsWithDisallowedUser() throws Exception {
    // Add initial permission set
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createAccessSectionInfoDenyAll();

    // Disallow READ
    accessInput.add.put(REFS_META_VERSION, accessSectionInfo);
    accessInput.add.put(REFS_DRAFTS, accessSectionInfo);
    accessInput.add.put(REFS_STARRED_CHANGES, accessSectionInfo);
    accessInput.add.put(REFS_HEADS, accessSectionInfo);
    pApi().access(accessInput);

    // Create a change to apply
    ProjectAccessInput accessInfoToApply = newProjectAccessInput();
    AccessSectionInfo accessSectionInfoToApply = createDefaultAccessSectionInfo();
    accessInfoToApply.add.put(REFS_HEADS, accessSectionInfoToApply);

    requestScopeOperations.setApiUser(user.id());
    assertThrows(ResourceNotFoundException.class, () -> pApi().access());
  }

  @Test
  public void permissionsGroupMap() throws Exception {
    // Add initial permission set
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSection = newAccessSectionInfo();

    PermissionInfo push = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    push.rules.put(SystemGroupBackend.PROJECT_OWNERS.get(), pri);
    accessSection.permissions.put(Permission.PUSH, push);

    PermissionInfo read = newPermissionInfo();
    pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    read.rules.put(SystemGroupBackend.ANONYMOUS_USERS.get(), pri);
    accessSection.permissions.put(Permission.READ, read);

    accessInput.add.put(REFS_ALL, accessSection);
    ProjectAccessInfo result = pApi().access(accessInput);
    assertThatMap(result.groups)
        .keys()
        .containsExactly(
            SystemGroupBackend.PROJECT_OWNERS.get(), SystemGroupBackend.ANONYMOUS_USERS.get());

    // Check the name, which is what the UI cares about; exhaustive
    // coverage of GroupInfo should be in groups REST API tests.
    assertThat(result.groups.get(SystemGroupBackend.PROJECT_OWNERS.get()).name)
        .isEqualTo("Project Owners");
    // Strip the ID, since it is in the key.
    assertThat(result.groups.get(SystemGroupBackend.PROJECT_OWNERS.get()).id).isNull();

    // Get call returns groups too.
    ProjectAccessInfo loggedInResult = pApi().access();
    assertThatMap(loggedInResult.groups)
        .keys()
        .containsExactly(
            SystemGroupBackend.PROJECT_OWNERS.get(), SystemGroupBackend.ANONYMOUS_USERS.get());

    GroupInfo owners = loggedInResult.groups.get(SystemGroupBackend.PROJECT_OWNERS.get());
    assertThat(owners.name).isEqualTo("Project Owners");
    assertThat(owners.id).isNull();
    assertThat(owners.members).isNull();
    assertThat(owners.includes).isNull();

    // PROJECT_OWNERS is invisible to anonymous user, but GetAccess disregards visibility.
    requestScopeOperations.setApiUserAnonymous();
    ProjectAccessInfo anonResult = pApi().access();
    assertThatMap(anonResult.groups)
        .keys()
        .containsExactly(
            SystemGroupBackend.PROJECT_OWNERS.get(), SystemGroupBackend.ANONYMOUS_USERS.get());
  }

  @Test
  public void updateParentAsUser() throws Exception {
    // Create child
    String newParentProjectName = projectOperations.newProject().create().get();

    // Set new parent
    ProjectAccessInput accessInput = newProjectAccessInput();
    accessInput.parent = newParentProjectName;

    requestScopeOperations.setApiUser(user.id());
    AuthException thrown = assertThrows(AuthException.class, () -> pApi().access(accessInput));
    assertThat(thrown).hasMessageThat().contains("administrate server not permitted");
  }

  @Test
  public void updateParentAsAdministrator() throws Exception {
    // Create parent
    String newParentProjectName = projectOperations.newProject().create().get();

    // Set new parent
    ProjectAccessInput accessInput = newProjectAccessInput();
    accessInput.parent = newParentProjectName;

    pApi().access(accessInput);

    assertThat(pApi().access().inheritsFrom.name).isEqualTo(newParentProjectName);
  }

  @Test
  public void addGlobalCapabilityAsUser() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultGlobalCapabilitiesAccessSectionInfo();

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    requestScopeOperations.setApiUser(user.id());
    assertThrows(
        AuthException.class, () -> gApi.projects().name(allProjects.get()).access(accessInput));
  }

  @Test
  public void addGlobalCapabilityAsAdmin() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultGlobalCapabilitiesAccessSectionInfo();

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    ProjectAccessInfo updatedAccessSectionInfo =
        gApi.projects().name(allProjects.get()).access(accessInput);
    assertThatMap(updatedAccessSectionInfo.local.get(AccessSection.GLOBAL_CAPABILITIES).permissions)
        .keys()
        .containsAtLeastElementsIn(accessSectionInfo.permissions.keySet());
  }

  @Test
  public void addPluginGlobalCapability() throws Exception {
    try (Registration registration =
        extensionRegistry
            .newRegistration()
            .add(
                new CapabilityDefinition() {
                  @Override
                  public String getDescription() {
                    return "A Plugin Global Capability";
                  }
                },
                "fooCapability")) {
      ProjectAccessInput accessInput = newProjectAccessInput();
      AccessSectionInfo accessSectionInfo = newAccessSectionInfo();

      PermissionInfo foo = newPermissionInfo();
      PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
      foo.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
      accessSectionInfo.permissions.put(ExtensionRegistry.PLUGIN_NAME + "-fooCapability", foo);

      accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

      ProjectAccessInfo updatedAccessSectionInfo =
          gApi.projects().name(allProjects.get()).access(accessInput);
      assertThatMap(
              updatedAccessSectionInfo.local.get(AccessSection.GLOBAL_CAPABILITIES).permissions)
          .keys()
          .containsAtLeastElementsIn(accessSectionInfo.permissions.keySet());
    }
  }

  @Test
  public void addPermissionAsGlobalCapability() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = newAccessSectionInfo();

    PermissionInfo push = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    push.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSectionInfo.permissions.put(Permission.PUSH, push);

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);
    BadRequestException ex =
        assertThrows(
            BadRequestException.class,
            () -> gApi.projects().name(allProjects.get()).access(accessInput));
    assertThat(ex).hasMessageThat().isEqualTo("Unknown global capability: " + Permission.PUSH);
  }

  @Test
  public void addInvalidGlobalCapability() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultGlobalCapabilitiesAccessSectionInfo();

    PermissionInfo permissionInfo = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    permissionInfo.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSectionInfo.permissions.put("Invalid Global Capability", permissionInfo);

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);
    BadRequestException ex =
        assertThrows(
            BadRequestException.class,
            () -> gApi.projects().name(allProjects.get()).access(accessInput));
    assertThat(ex)
        .hasMessageThat()
        .isEqualTo("Unknown global capability: Invalid Global Capability");
  }

  @Test
  public void addGlobalCapabilityForNonRootProject() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultGlobalCapabilitiesAccessSectionInfo();

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    assertThrows(BadRequestException.class, () -> pApi().access(accessInput));
  }

  @Test
  public void addNonGlobalCapabilityToGlobalCapabilities() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = newAccessSectionInfo();

    PermissionInfo permissionInfo = newPermissionInfo();
    permissionInfo.rules.put(adminGroupUuid().get(), null);
    accessSectionInfo.permissions.put(Permission.PUSH, permissionInfo);

    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);
    assertThrows(
        BadRequestException.class,
        () -> gApi.projects().name(allProjects.get()).access(accessInput));
  }

  @Test
  public void removeGlobalCapabilityAsUser() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultGlobalCapabilitiesAccessSectionInfo();

    accessInput.remove.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    requestScopeOperations.setApiUser(user.id());
    assertThrows(
        AuthException.class, () -> gApi.projects().name(allProjects.get()).access(accessInput));
  }

  @Test
  public void removeGlobalCapabilityAsAdmin() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = newAccessSectionInfo();

    PermissionInfo permissionInfo = newPermissionInfo();
    permissionInfo.rules.put(adminGroupUuid().get(), null);
    accessSectionInfo.permissions.put(GlobalCapability.ACCESS_DATABASE, permissionInfo);

    // Add and validate first as removing existing privileges such as
    // administrateServer would break upcoming tests
    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    ProjectAccessInfo updatedProjectAccessInfo =
        gApi.projects().name(allProjects.get()).access(accessInput);
    assertThatMap(updatedProjectAccessInfo.local.get(AccessSection.GLOBAL_CAPABILITIES).permissions)
        .keys()
        .containsAtLeastElementsIn(accessSectionInfo.permissions.keySet());

    // Remove
    accessInput.add.clear();
    accessInput.remove.put(AccessSection.GLOBAL_CAPABILITIES, accessSectionInfo);

    updatedProjectAccessInfo = gApi.projects().name(allProjects.get()).access(accessInput);
    assertThatMap(updatedProjectAccessInfo.local.get(AccessSection.GLOBAL_CAPABILITIES).permissions)
        .keys()
        .containsNoneIn(accessSectionInfo.permissions.keySet());
  }

  @Test
  public void unknownPermissionRemainsUnchanged() throws Exception {
    String access = "access";
    String unknownPermission = "unknownPermission";
    String registeredUsers = "group Registered Users";
    String refsFor = "refs/for/*";
    // Clone repository to forcefully add permission
    TestRepository<InMemoryRepository> allProjectsRepo = cloneProject(allProjects, admin);

    // Fetch permission ref
    GitUtil.fetch(allProjectsRepo, "refs/meta/config:cfg");
    allProjectsRepo.reset("cfg");

    // Load current permissions
    String config =
        gApi.projects()
            .name(allProjects.get())
            .branch(RefNames.REFS_CONFIG)
            .file(ProjectConfig.PROJECT_CONFIG)
            .asString();

    // Append and push unknown permission
    Config cfg = new Config();
    cfg.fromText(config);
    cfg.setString(access, refsFor, unknownPermission, registeredUsers);
    config = cfg.toText();
    PushOneCommit push =
        pushFactory.create(
            admin.newIdent(), allProjectsRepo, "Subject", ProjectConfig.PROJECT_CONFIG, config);
    push.to(RefNames.REFS_CONFIG).assertOkStatus();

    // Verify that unknownPermission is present
    config =
        gApi.projects()
            .name(allProjects.get())
            .branch(RefNames.REFS_CONFIG)
            .file(ProjectConfig.PROJECT_CONFIG)
            .asString();
    cfg.fromText(config);
    assertThat(cfg).stringValue(access, refsFor, unknownPermission).isEqualTo(registeredUsers);

    // Make permission change through API
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();
    accessInput.add.put(refsFor, accessSectionInfo);
    gApi.projects().name(allProjects.get()).access(accessInput);
    accessInput.add.clear();
    accessInput.remove.put(refsFor, accessSectionInfo);
    gApi.projects().name(allProjects.get()).access(accessInput);

    // Verify that unknownPermission is still present
    config =
        gApi.projects()
            .name(allProjects.get())
            .branch(RefNames.REFS_CONFIG)
            .file(ProjectConfig.PROJECT_CONFIG)
            .asString();
    cfg.fromText(config);
    assertThat(cfg).stringValue(access, refsFor, unknownPermission).isEqualTo(registeredUsers);
  }

  @Test
  public void allUsersCanOnlyInheritFromAllProjects() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    accessInput.parent = project.get();
    BadRequestException thrown =
        assertThrows(
            BadRequestException.class,
            () -> gApi.projects().name(allUsers.get()).access(accessInput));
    assertThat(thrown)
        .hasMessageThat()
        .contains(allUsers.get() + " must inherit from " + allProjects.get());
  }

  @Test
  public void syncCreateGroupPermission_addAndRemoveCreateGroupCapability() throws Exception {
    // Grant CREATE_GROUP to Registered Users
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSection = newAccessSectionInfo();
    PermissionInfo createGroup = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    createGroup.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSection.permissions.put(GlobalCapability.CREATE_GROUP, createGroup);
    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSection);
    gApi.projects().name(allProjects.get()).access(accessInput);

    // Assert that the permission was synced from All-Projects (global) to All-Users (ref)
    Map<String, AccessSectionInfo> local = gApi.projects().name("All-Users").access().local;
    assertThatMap(local).keys().contains(RefNames.REFS_GROUPS + "*");
    Map<String, PermissionInfo> permissions = local.get(RefNames.REFS_GROUPS + "*").permissions;
    // READ is the default permission and should be preserved by the syncer
    assertThatMap(permissions).keys().containsExactly(Permission.READ, Permission.CREATE);
    Map<String, PermissionRuleInfo> rules = permissions.get(Permission.CREATE).rules;
    assertThatMap(rules).values().containsExactly(pri);

    // Revoke the permission
    accessInput.add.clear();
    accessInput.remove.put(AccessSection.GLOBAL_CAPABILITIES, accessSection);
    gApi.projects().name(allProjects.get()).access(accessInput);

    // Assert that the permission was synced from All-Projects (global) to All-Users (ref)
    Map<String, AccessSectionInfo> local2 = gApi.projects().name("All-Users").access().local;
    assertThatMap(local2).keys().contains(RefNames.REFS_GROUPS + "*");
    Map<String, PermissionInfo> permissions2 = local2.get(RefNames.REFS_GROUPS + "*").permissions;
    // READ is the default permission and should be preserved by the syncer
    assertThatMap(permissions2).keys().containsExactly(Permission.READ);
  }

  @Test
  public void syncCreateGroupPermission_addCreateGroupCapabilityToMultipleGroups()
      throws Exception {
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);

    // Grant CREATE_GROUP to Registered Users
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSection = newAccessSectionInfo();
    PermissionInfo createGroup = newPermissionInfo();
    createGroup.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSection.permissions.put(GlobalCapability.CREATE_GROUP, createGroup);
    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSection);
    gApi.projects().name(allProjects.get()).access(accessInput);

    // Grant CREATE_GROUP to Administrators
    accessInput = newProjectAccessInput();
    accessSection = newAccessSectionInfo();
    createGroup = newPermissionInfo();
    createGroup.rules.put(adminGroupUuid().get(), pri);
    accessSection.permissions.put(GlobalCapability.CREATE_GROUP, createGroup);
    accessInput.add.put(AccessSection.GLOBAL_CAPABILITIES, accessSection);
    gApi.projects().name(allProjects.get()).access(accessInput);

    // Assert that the permissions were synced from All-Projects (global) to All-Users (ref)
    Map<String, AccessSectionInfo> local = gApi.projects().name("All-Users").access().local;
    assertThatMap(local).keys().contains(RefNames.REFS_GROUPS + "*");
    Map<String, PermissionInfo> permissions = local.get(RefNames.REFS_GROUPS + "*").permissions;
    // READ is the default permission and should be preserved by the syncer
    assertThatMap(permissions).keys().containsExactly(Permission.READ, Permission.CREATE);
    Map<String, PermissionRuleInfo> rules = permissions.get(Permission.CREATE).rules;
    assertThatMap(rules)
        .keys()
        .containsExactly(SystemGroupBackend.REGISTERED_USERS.get(), adminGroupUuid().get());
    assertThat(rules.get(SystemGroupBackend.REGISTERED_USERS.get())).isEqualTo(pri);
    assertThat(rules.get(adminGroupUuid().get())).isEqualTo(pri);
  }

  @Test
  public void addAccessSectionForInvalidRef() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    // 'refs/heads/stable_*' is invalid, correct would be '^refs/heads/stable_.*'
    String invalidRef = Constants.R_HEADS + "stable_*";
    accessInput.add.put(invalidRef, accessSectionInfo);

    BadRequestException thrown =
        assertThrows(BadRequestException.class, () -> pApi().access(accessInput));
    assertThat(thrown).hasMessageThat().contains("Invalid Name: " + invalidRef);
  }

  @Test
  public void createAccessChangeWithAccessSectionForInvalidRef() throws Exception {
    ProjectAccessInput accessInput = newProjectAccessInput();
    AccessSectionInfo accessSectionInfo = createDefaultAccessSectionInfo();

    // 'refs/heads/stable_*' is invalid, correct would be '^refs/heads/stable_.*'
    String invalidRef = Constants.R_HEADS + "stable_*";
    accessInput.add.put(invalidRef, accessSectionInfo);

    BadRequestException thrown =
        assertThrows(BadRequestException.class, () -> pApi().accessChange(accessInput));
    assertThat(thrown).hasMessageThat().contains("Invalid Name: " + invalidRef);
  }

  private ProjectApi pApi() throws Exception {
    return gApi.projects().name(newProjectName.get());
  }

  @Test
  public void grantAllowAndDenyForSameGroup() throws Exception {
    GroupReference registeredUsers = systemGroupBackend.getGroup(REGISTERED_USERS);
    String access = "access";
    List<String> allowThenDeny =
        asList(registeredUsers.toConfigValue(), "deny " + registeredUsers.toConfigValue());
    // Clone repository to forcefully add permission
    TestRepository<InMemoryRepository> allProjectsRepo = cloneProject(allProjects, admin);

    // Fetch permission ref
    GitUtil.fetch(allProjectsRepo, "refs/meta/config:cfg");
    allProjectsRepo.reset("cfg");

    // Load current permissions
    String config =
        gApi.projects()
            .name(allProjects.get())
            .branch(RefNames.REFS_CONFIG)
            .file(ProjectConfig.PROJECT_CONFIG)
            .asString();

    // Append and push allowThenDeny permissions
    Config cfg = new Config();
    cfg.fromText(config);
    cfg.setStringList(access, AccessSection.HEADS, Permission.READ, allowThenDeny);
    config = cfg.toText();
    PushOneCommit push =
        pushFactory.create(
            admin.newIdent(), allProjectsRepo, "Subject", ProjectConfig.PROJECT_CONFIG, config);
    push.to(RefNames.REFS_CONFIG).assertOkStatus();

    ProjectAccessInfo pai = gApi.projects().name(allProjects.get()).access();
    Map<String, AccessSectionInfo> local = pai.local;
    AccessSectionInfo heads = local.get(AccessSection.HEADS);
    Map<String, PermissionInfo> permissions = heads.permissions;
    PermissionInfo read = permissions.get(Permission.READ);
    Map<String, PermissionRuleInfo> rules = read.rules;
    assertEquals(
        rules.get(registeredUsers.getUUID().get()),
        new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false));
  }

  @Test
  public void grantDenyAndAllowForSameGroup() throws Exception {
    GroupReference registeredUsers = systemGroupBackend.getGroup(REGISTERED_USERS);
    String access = "access";
    List<String> denyThenAllow =
        asList("deny " + registeredUsers.toConfigValue(), registeredUsers.toConfigValue());
    // Clone repository to forcefully add permission
    TestRepository<InMemoryRepository> allProjectsRepo = cloneProject(allProjects, admin);

    // Fetch permission ref
    GitUtil.fetch(allProjectsRepo, "refs/meta/config:cfg");
    allProjectsRepo.reset("cfg");

    // Load current permissions
    String config =
        gApi.projects()
            .name(allProjects.get())
            .branch(RefNames.REFS_CONFIG)
            .file(ProjectConfig.PROJECT_CONFIG)
            .asString();

    // Append and push denyThenAllow permissions
    Config cfg = new Config();
    cfg.fromText(config);
    cfg.setStringList(access, AccessSection.HEADS, Permission.READ, denyThenAllow);
    config = cfg.toText();
    PushOneCommit push =
        pushFactory.create(
            admin.newIdent(), allProjectsRepo, "Subject", ProjectConfig.PROJECT_CONFIG, config);
    push.to(RefNames.REFS_CONFIG).assertOkStatus();

    ProjectAccessInfo pai = gApi.projects().name(allProjects.get()).access();
    Map<String, AccessSectionInfo> local = pai.local;
    AccessSectionInfo heads = local.get(AccessSection.HEADS);
    Map<String, PermissionInfo> permissions = heads.permissions;
    PermissionInfo read = permissions.get(Permission.READ);
    Map<String, PermissionRuleInfo> rules = read.rules;
    assertEquals(
        rules.get(registeredUsers.getUUID().get()),
        new PermissionRuleInfo(PermissionRuleInfo.Action.DENY, false));
  }

  private ProjectAccessInput newProjectAccessInput() {
    ProjectAccessInput p = new ProjectAccessInput();
    p.add = new HashMap<>();
    p.remove = new HashMap<>();
    return p;
  }

  private PermissionInfo newPermissionInfo() {
    PermissionInfo p = new PermissionInfo(null, null);
    p.rules = new HashMap<>();
    return p;
  }

  private AccessSectionInfo newAccessSectionInfo() {
    AccessSectionInfo a = new AccessSectionInfo();
    a.permissions = new HashMap<>();
    return a;
  }

  private AccessSectionInfo createDefaultAccessSectionInfo() {
    AccessSectionInfo accessSection = newAccessSectionInfo();

    PermissionInfo push = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    push.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSection.permissions.put(Permission.PUSH, push);

    PermissionInfo codeReview = newPermissionInfo();
    codeReview.label = LabelId.CODE_REVIEW;
    pri = new PermissionRuleInfo(PermissionRuleInfo.Action.DENY, false);
    codeReview.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);

    pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    pri.max = 1;
    pri.min = -1;
    codeReview.rules.put(SystemGroupBackend.PROJECT_OWNERS.get(), pri);
    accessSection.permissions.put(Permission.LABEL + LabelId.CODE_REVIEW, codeReview);

    return accessSection;
  }

  private AccessSectionInfo createDefaultGlobalCapabilitiesAccessSectionInfo() {
    AccessSectionInfo accessSection = newAccessSectionInfo();

    PermissionInfo email = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.ALLOW, false);
    email.rules.put(SystemGroupBackend.REGISTERED_USERS.get(), pri);
    accessSection.permissions.put(GlobalCapability.EMAIL_REVIEWERS, email);

    return accessSection;
  }

  private AccessSectionInfo createAccessSectionInfoDenyAll() {
    AccessSectionInfo accessSection = newAccessSectionInfo();

    PermissionInfo read = newPermissionInfo();
    PermissionRuleInfo pri = new PermissionRuleInfo(PermissionRuleInfo.Action.DENY, false);
    read.rules.put(SystemGroupBackend.ANONYMOUS_USERS.get(), pri);
    accessSection.permissions.put(Permission.READ, read);

    return accessSection;
>>>>>>> BRANCH (76110d Merge branch 'stable-3.3' into stable-3.4)
  }
}
