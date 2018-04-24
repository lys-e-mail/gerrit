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

package com.google.gerrit.elasticsearch;

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
import static com.google.common.base.Preconditions.checkArgument;

import com.google.gerrit.index.IndexConfig;
import com.google.gerrit.lifecycle.LifecycleModule;
import com.google.gerrit.server.config.GerritServerConfig;
import com.google.gerrit.server.index.IndexModule;
import com.google.gerrit.server.index.OnlineUpgrader;
import com.google.gerrit.server.index.SingleVersionModule;
import com.google.gerrit.server.index.VersionManager;
=======
import com.google.gerrit.server.index.AbstractIndexModule;
import com.google.gerrit.server.index.AbstractVersionManager;
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
import com.google.gerrit.server.index.account.AccountIndex;
import com.google.gerrit.server.index.change.ChangeIndex;
import com.google.gerrit.server.index.group.GroupIndex;
<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
=======
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
import java.util.Map;

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
public class ElasticIndexModule extends AbstractModule {
=======
public class ElasticIndexModule extends AbstractIndexModule {

>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  public static ElasticIndexModule singleVersionWithExplicitVersions(
      Map<String, Integer> versions, int threads) {
    return new ElasticIndexModule(versions, threads, false);
  }

  public static ElasticIndexModule latestVersionWithOnlineUpgrade() {
    return new ElasticIndexModule(null, 0, true);
  }

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
  public static ElasticIndexModule latestVersionWithoutOnlineUpgrade() {
    return new ElasticIndexModule(null, 0, false);
  }

  private final Map<String, Integer> singleVersions;
  private final int threads;
  private final boolean onlineUpgrade;

  private ElasticIndexModule(
      Map<String, Integer> singleVersions, int threads, boolean onlineUpgrade) {
    if (singleVersions != null) {
      checkArgument(!onlineUpgrade, "online upgrade is incompatible with single version map");
    }
    this.singleVersions = singleVersions;
    this.threads = threads;
    this.onlineUpgrade = onlineUpgrade;
=======
  private ElasticIndexModule(Map<String, Integer> singleVersions, int threads) {
    super(singleVersions, threads);
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  }

  @Override
  protected Class<? extends AccountIndex> getAccountIndex() {
    return ElasticAccountIndex.class;
  }

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
  @Provides
  @Singleton
  IndexConfig getIndexConfig(@GerritServerConfig Config cfg) {
    return IndexConfig.fromConfig(cfg).separateChangeSubIndexes(true).build();
=======
  @Override
  protected Class<? extends ChangeIndex> getChangeIndex() {
    return ElasticChangeIndex.class;
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  }

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
  private class MultiVersionModule extends LifecycleModule {
    @Override
    public void configure() {
      bind(VersionManager.class).to(ElasticVersionManager.class);
      listener().to(ElasticVersionManager.class);
      if (onlineUpgrade) {
        listener().to(OnlineUpgrader.class);
      }
    }
=======
  @Override
  protected Class<? extends GroupIndex> getGroupIndex() {
    return ElasticGroupIndex.class;
  }

  @Override
  protected Class<? extends AbstractVersionManager> getVersionManager() {
    return ElasticVersionManager.class;
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  }
}
