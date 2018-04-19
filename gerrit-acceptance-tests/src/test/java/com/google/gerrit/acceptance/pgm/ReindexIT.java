// Copyright (C) 2014 The Android Open Source Project
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

package com.google.gerrit.acceptance.pgm;

import static com.google.common.collect.ImmutableMap.toImmutableMap;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static com.google.gerrit.extensions.client.ListGroupsOption.MEMBERS;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.gerrit.acceptance.NoHttpd;
import com.google.gerrit.acceptance.StandaloneSiteTest;
<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
import com.google.gerrit.acceptance.pgm.IndexUpgradeController.UpgradeAttempt;
=======
import com.google.gerrit.elasticsearch.testing.ElasticTestUtils;
import com.google.gerrit.elasticsearch.testing.ElasticTestUtils.ElasticNodeInfo;
>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)
import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.common.ChangeInput;
import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.reviewdb.client.Project;
<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
import com.google.gerrit.server.index.GerritIndexStatus;
import com.google.gerrit.server.index.change.ChangeIndexCollection;
import com.google.gerrit.server.index.change.ChangeSchemaDefinitions;
import com.google.gerrit.server.query.change.InternalChangeQuery;
import com.google.inject.Provider;
=======
import com.google.gerrit.testutil.ConfigSuite;
>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)
import java.nio.file.Files;
<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
import java.util.Set;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
=======
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import org.eclipse.jgit.lib.Config;
import org.junit.AfterClass;
>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)
import org.junit.Test;

@NoHttpd
public class ReindexIT extends StandaloneSiteTest {
<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
  private static final String CHANGES = ChangeSchemaDefinitions.NAME;

  private Project.NameKey project;
  private String changeId;
=======

  @ConfigSuite.Config
  public static Config elasticsearch() throws InterruptedException, ExecutionException {
    if (elasticNodeInfo == null) {
      elasticNodeInfo = ElasticTestUtils.startElasticsearchNode();
    }
    String indicesPrefix = UUID.randomUUID().toString();
    ElasticTestUtils.createAllIndexes(elasticNodeInfo, indicesPrefix);

    Config cfg = new Config();
    ElasticTestUtils.configure(cfg, elasticNodeInfo.port, indicesPrefix);
    return cfg;
  }

  private static ElasticNodeInfo elasticNodeInfo;
>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)

  @Test
  public void reindexFromScratch() throws Exception {
    setUpChange();

<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
=======
      ChangeInput in = new ChangeInput();
      in.project = project.get();
      in.branch = "master";
      in.subject = "Test change";
      in.newBranch = true;
      changeId = gApi.changes().create(in).info().changeId;
    }

>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)
    MoreFiles.deleteRecursively(sitePaths.index_dir, RecursiveDeleteOption.ALLOW_INSECURE);
    Files.createDirectory(sitePaths.index_dir);
    assertServerStartupFails();

    runGerrit("reindex", "-d", sitePaths.site_path.toString(), "--show-stack-trace");
    assertReady(ChangeSchemaDefinitions.INSTANCE.getLatest().getVersion());

    try (ServerContext ctx = startServer()) {
      GerritApi gApi = ctx.getInjector().getInstance(GerritApi.class);
      // Query change index
      assertThat(gApi.changes().query("message:Test").get().stream().map(c -> c.changeId))
          .containsExactly(changeId);
      // Query account index
      assertThat(gApi.accounts().query("admin").get().stream().map(a -> a._accountId))
          .containsExactly(adminId.get());
      // Query group index
      assertThat(
              gApi.groups()
                  .query("Group")
                  .withOption(MEMBERS)
                  .get()
                  .stream()
                  .flatMap(g -> g.members.stream())
                  .map(a -> a._accountId))
          .containsExactly(adminId.get());
    }
  }

<<<<<<< HEAD   (79ea76 Fix typo in cmd-stream-events.txt)
  @Test
  public void onlineUpgradeChanges() throws Exception {
    int prevVersion = ChangeSchemaDefinitions.INSTANCE.getPrevious().getVersion();
    int currVersion = ChangeSchemaDefinitions.INSTANCE.getLatest().getVersion();

    // Before storing any changes, switch back to the previous version.
    GerritIndexStatus status = new GerritIndexStatus(sitePaths);
    status.setReady(CHANGES, currVersion, false);
    status.setReady(CHANGES, prevVersion, true);
    status.save();
    assertReady(prevVersion);

    setOnlineUpgradeConfig(false);
    setUpChange();
    setOnlineUpgradeConfig(true);

    IndexUpgradeController u = new IndexUpgradeController(1);
    try (ServerContext ctx = startServer(u.module())) {
      assertSearchVersion(ctx, prevVersion);
      assertWriteVersions(ctx, prevVersion, currVersion);

      // Updating and searching old schema version works.
      Provider<InternalChangeQuery> queryProvider =
          ctx.getInjector().getProvider(InternalChangeQuery.class);
      assertThat(queryProvider.get().byKey(new Change.Key(changeId))).hasSize(1);
      assertThat(queryProvider.get().byTopicOpen("topic1")).isEmpty();

      GerritApi gApi = ctx.getInjector().getInstance(GerritApi.class);
      gApi.changes().id(changeId).topic("topic1");
      assertThat(queryProvider.get().byTopicOpen("topic1")).hasSize(1);

      u.runUpgrades();
      assertThat(u.getStartedAttempts())
          .containsExactly(UpgradeAttempt.create(CHANGES, prevVersion, currVersion));
      assertThat(u.getSucceededAttempts())
          .containsExactly(UpgradeAttempt.create(CHANGES, prevVersion, currVersion));
      assertThat(u.getFailedAttempts()).isEmpty();

      assertReady(currVersion);
      assertSearchVersion(ctx, currVersion);
      assertWriteVersions(ctx, currVersion);

      // Updating and searching new schema version works.
      assertThat(queryProvider.get().byTopicOpen("topic1")).hasSize(1);
      assertThat(queryProvider.get().byTopicOpen("topic2")).isEmpty();
      gApi.changes().id(changeId).topic("topic2");
      assertThat(queryProvider.get().byTopicOpen("topic1")).isEmpty();
      assertThat(queryProvider.get().byTopicOpen("topic2")).hasSize(1);
    }
  }

  private void setUpChange() throws Exception {
    project = new Project.NameKey("project");
    try (ServerContext ctx = startServer()) {
      GerritApi gApi = ctx.getInjector().getInstance(GerritApi.class);
      gApi.projects().create(project.get());

      ChangeInput in = new ChangeInput(project.get(), "master", "Test change");
      in.newBranch = true;
      changeId = gApi.changes().create(in).info().changeId;
    }
  }

  private void setOnlineUpgradeConfig(boolean enable) throws Exception {
    FileBasedConfig cfg = new FileBasedConfig(sitePaths.gerrit_config.toFile(), FS.detect());
    cfg.load();
    cfg.setBoolean("index", null, "onlineUpgrade", enable);
    cfg.save();
  }

  private void assertSearchVersion(ServerContext ctx, int expected) {
    assertThat(
            ctx.getInjector()
                .getInstance(ChangeIndexCollection.class)
                .getSearchIndex()
                .getSchema()
                .getVersion())
        .named("search version")
        .isEqualTo(expected);
  }

  private void assertWriteVersions(ServerContext ctx, Integer... expected) {
    assertThat(
            ctx.getInjector()
                .getInstance(ChangeIndexCollection.class)
                .getWriteIndexes()
                .stream()
                .map(i -> i.getSchema().getVersion()))
        .named("write versions")
        .containsExactlyElementsIn(ImmutableSet.copyOf(expected));
  }

  private void assertReady(int expectedReady) throws Exception {
    Set<Integer> allVersions = ChangeSchemaDefinitions.INSTANCE.getSchemas().keySet();
    GerritIndexStatus status = new GerritIndexStatus(sitePaths);
    assertThat(
            allVersions.stream().collect(toImmutableMap(v -> v, v -> status.getReady(CHANGES, v))))
        .named("ready state for index versions")
        .isEqualTo(allVersions.stream().collect(toImmutableMap(v -> v, v -> v == expectedReady)));
=======
  @AfterClass
  public static void stopElasticServer() {
    if (elasticNodeInfo != null) {
      elasticNodeInfo.node.close();
      elasticNodeInfo.elasticDir.delete();
      elasticNodeInfo = null;
    }
>>>>>>> BRANCH (dbabc2 ReindexIT: Allow insecure directory deletion)
  }
}
