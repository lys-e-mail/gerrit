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

package com.google.gerrit.lucene;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.collect.ImmutableMap;
<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
import com.google.gerrit.index.IndexConfig;
import com.google.gerrit.lifecycle.LifecycleModule;
=======
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
import com.google.gerrit.server.config.GerritServerConfig;
<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
import com.google.gerrit.server.index.IndexModule;
import com.google.gerrit.server.index.OnlineUpgrader;
import com.google.gerrit.server.index.SingleVersionModule;
import com.google.gerrit.server.index.VersionManager;
=======
import com.google.gerrit.server.index.AbstractIndexModule;
import com.google.gerrit.server.index.AbstractVersionManager;
import com.google.gerrit.server.index.IndexConfig;
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
import org.apache.lucene.search.BooleanQuery;
import org.eclipse.jgit.lib.Config;

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
public class LuceneIndexModule extends AbstractModule {
=======
public class LuceneIndexModule extends AbstractIndexModule {
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  public static LuceneIndexModule singleVersionAllLatest(int threads) {
    return new LuceneIndexModule(ImmutableMap.<String, Integer>of(), threads, false);
  }

  public static LuceneIndexModule singleVersionWithExplicitVersions(
      Map<String, Integer> versions, int threads) {
    return new LuceneIndexModule(versions, threads, false);
  }

  public static LuceneIndexModule latestVersionWithOnlineUpgrade() {
    return new LuceneIndexModule(null, 0, true);
  }

  public static LuceneIndexModule latestVersionWithoutOnlineUpgrade() {
    return new LuceneIndexModule(null, 0, false);
  }

  static boolean isInMemoryTest(Config cfg) {
    return cfg.getBoolean("index", "lucene", "testInmemory", false);
  }

<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
  private final Map<String, Integer> singleVersions;
  private final int threads;
  private final boolean onlineUpgrade;

  private LuceneIndexModule(
      Map<String, Integer> singleVersions, int threads, boolean onlineUpgrade) {
    if (singleVersions != null) {
      checkArgument(!onlineUpgrade, "online upgrade is incompatible with single version map");
    }
    this.singleVersions = singleVersions;
    this.threads = threads;
    this.onlineUpgrade = onlineUpgrade;
=======
  private LuceneIndexModule(Map<String, Integer> singleVersions, int threads) {
    super(singleVersions, threads);
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  }

  @Override
  protected Class<? extends AccountIndex> getAccountIndex() {
    return LuceneAccountIndex.class;
  }

  @Override
  protected Class<? extends ChangeIndex> getChangeIndex() {
    return LuceneChangeIndex.class;
  }

  @Override
  protected Class<? extends GroupIndex> getGroupIndex() {
    return LuceneGroupIndex.class;
  }

  @Override
  protected Class<? extends AbstractVersionManager> getVersionManager() {
    return LuceneVersionManager.class;
  }

  @Override
  protected IndexConfig getIndexConfig(@GerritServerConfig Config cfg) {
    BooleanQuery.setMaxClauseCount(
        cfg.getInt("index", "maxTerms", BooleanQuery.getMaxClauseCount()));
<<<<<<< HEAD   (a7f8e5 Merge branch 'stable-2.14' into stable-2.15)
    return IndexConfig.fromConfig(cfg).separateChangeSubIndexes(true).build();
  }

  private class MultiVersionModule extends LifecycleModule {
    @Override
    public void configure() {
      bind(VersionManager.class).to(LuceneVersionManager.class);
      listener().to(LuceneVersionManager.class);
      if (onlineUpgrade) {
        listener().to(OnlineUpgrader.class);
      }
    }
=======
    return super.getIndexConfig(cfg);
>>>>>>> BRANCH (6ca35f Remove duplication between both index modules)
  }
}
