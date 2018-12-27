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

package com.google.gerrit.server.change;

import com.google.gerrit.reviewdb.client.Change;
import com.google.gerrit.reviewdb.client.PatchSet;
import com.google.gerrit.reviewdb.server.ReviewDb;
import com.google.gerrit.server.project.ProjectState;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

/**
 * Cache of {@link ChangeKind} per commit.
 * <p>
 * This is immutable conditioned on the merge strategy (unless the JGit strategy
 * implementation changes, which might invalidate old entries).
 */
<<<<<<< HEAD   (4ecf2e Update JGit to latest 4.5.x release)
public interface ChangeKindCache {
=======
public class ChangeKindCache {
  private static final Logger log =
      LoggerFactory.getLogger(ChangeKindCache.class);

  private static final String ID_CACHE = "change_kind";

  public static Module module() {
    return new CacheModule() {
      @Override
      protected void configure() {
        cache(ID_CACHE,
            Key.class,
            ChangeKind.class)
          .maximumWeight(0)
          .loader(Loader.class);
      }
    };
  }

  public static class Key implements Serializable {
    private static final long serialVersionUID = 1L;

    private final ObjectId prior;
    private final ObjectId next;
    private final String strategyName;
    private transient Repository repo;

    private Key(ObjectId prior, ObjectId next, String strategyName,
        Repository repo) {
      this.prior = prior.copy();
      this.next = next.copy();
      this.strategyName = strategyName;
      this.repo = repo;
    }

    public ObjectId getPrior() {
      return prior;
    }

    public ObjectId getNext() {
      return next;
    }

    public String getStrategyName() {
      return strategyName;
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Key) {
        Key k = (Key) o;
        return Objects.equal(prior, k.prior)
            && Objects.equal(next, k.next)
            && Objects.equal(strategyName, k.strategyName);
      }
      return false;
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(prior, next, strategyName);
    }
  }

  @Singleton
  private static class Loader extends CacheLoader<Key, ChangeKind> {
    @Override
    public ChangeKind load(Key key) throws IOException {
      RevWalk walk = new RevWalk(key.repo);
      try {
        RevCommit prior = walk.parseCommit(key.prior);
        walk.parseBody(prior);
        RevCommit next = walk.parseCommit(key.next);
        walk.parseBody(next);

        if (!next.getFullMessage().equals(prior.getFullMessage())) {
          if (next.getTree() == prior.getTree() && isSameParents(prior, next)) {
            return ChangeKind.NO_CODE_CHANGE;
          } else {
            return ChangeKind.REWORK;
          }
        }

        if (prior.getParentCount() != 1 || next.getParentCount() != 1) {
          // Trivial rebases done by machine only work well on 1 parent.
          return ChangeKind.REWORK;
        }

        if (next.getTree() == prior.getTree() &&
           isSameParents(prior, next)) {
          return ChangeKind.TRIVIAL_REBASE;
        }

        // A trivial rebase can be detected by looking for the next commit
        // having the same tree as would exist when the prior commit is
        // cherry-picked onto the next commit's new first parent.
        ThreeWayMerger merger = MergeUtil.newThreeWayMerger(
            key.repo, MergeUtil.createDryRunInserter(key.repo), key.strategyName);
        merger.setBase(prior.getParent(0));
        if (merger.merge(next.getParent(0), prior)
            && merger.getResultTreeId().equals(next.getTree())) {
          return ChangeKind.TRIVIAL_REBASE;
        } else {
          return ChangeKind.REWORK;
        }
      } finally {
        key.repo = null;
        walk.close();
      }
    }

    private static boolean isSameParents(RevCommit prior, RevCommit next) {
      if (prior.getParentCount() != next.getParentCount()) {
        return false;
      } else if (prior.getParentCount() == 0) {
        return true;
      }
      return prior.getParent(0).equals(next.getParent(0));
    }
  }

  private final LoadingCache<Key, ChangeKind> cache;
  private final boolean useRecursiveMerge;

  @Inject
  ChangeKindCache(
      @GerritServerConfig Config serverConfig,
      @Named(ID_CACHE) LoadingCache<Key, ChangeKind> cache) {
    this.cache = cache;
    this.useRecursiveMerge = MergeUtil.useRecursiveMerge(serverConfig);
  }

>>>>>>> BRANCH (4a3c2d Update JGit to latest 4.5.x release)
  public ChangeKind getChangeKind(ProjectState project, Repository repo,
      ObjectId prior, ObjectId next);

  public ChangeKind getChangeKind(ReviewDb db, Change change, PatchSet patch);
}
