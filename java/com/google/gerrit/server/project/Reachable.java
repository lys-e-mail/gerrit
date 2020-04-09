// Copyright (C) 2017 The Android Open Source Project
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

package com.google.gerrit.server.project;

import com.google.common.flogger.FluentLogger;
import com.google.gerrit.reviewdb.client.Project;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.change.IncludedInResolver;
import com.google.gerrit.server.permissions.PermissionBackend;
import com.google.gerrit.server.permissions.PermissionBackend.RefFilterOptions;
import com.google.gerrit.server.permissions.PermissionBackendException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.List;
import java.util.Map;
<<<<<<< HEAD   (9f470f ChangeQueryBuilder: Use ChangeIsVisibleToPredicate.Factory)
=======
import java.util.Optional;
import org.eclipse.jgit.lib.Constants;
>>>>>>> BRANCH (4a6b05 Elasticsearch: Remove support for EOL 6.x versions)
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Report whether a commit is reachable from a set of commits. This is used for checking if a user
 * has read permissions on a commit.
 */
@Singleton
public class Reachable {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  private final PermissionBackend permissionBackend;

  @Inject
  Reachable(PermissionBackend permissionBackend) {
    this.permissionBackend = permissionBackend;
  }

  /**
   * @return true if a commit is reachable from a given set of refs. This method enforces
   *     permissions on the given set of refs and performs a reachability check. Tags are not
   *     filtered separately and will only be returned if reachable by a provided ref.
   */
  public boolean fromRefs(
<<<<<<< HEAD   (9f470f ChangeQueryBuilder: Use ChangeIsVisibleToPredicate.Factory)
      Project.NameKey project, Repository repo, RevCommit commit, List<Ref> refs) {
=======
      Project.NameKey project, Repository repo, RevCommit commit, Map<String, Ref> refs) {
    return fromRefs(project, repo, commit, refs, Optional.empty());
  }

  private boolean fromRefs(
      Project.NameKey project,
      Repository repo,
      RevCommit commit,
      Map<String, Ref> refs,
      Optional<Provider<? extends CurrentUser>> userProvider) {
>>>>>>> BRANCH (4a6b05 Elasticsearch: Remove support for EOL 6.x versions)
    try (RevWalk rw = new RevWalk(repo)) {
      Map<String, Ref> filtered =
          userProvider
              .map(up -> permissionBackend.user(up.get()))
              .orElse(permissionBackend.currentUser())
              .project(project)
              .filter(refs, repo, RefFilterOptions.defaults());
      return IncludedInResolver.includedInAny(repo, rw, commit, filtered.values());
    } catch (IOException | PermissionBackendException e) {
      logger.atSevere().withCause(e).log(
          "Cannot verify permissions to commit object %s in repository %s", commit.name(), project);
      return false;
    }
  }
<<<<<<< HEAD   (9f470f ChangeQueryBuilder: Use ChangeIsVisibleToPredicate.Factory)
=======

  /** @return true if a commit is reachable from a repo's branches and tags. */
  boolean fromHeadsOrTags(
      Project.NameKey project,
      Repository repo,
      RevCommit commit,
      Provider<? extends CurrentUser> userProvider) {
    try {
      RefDatabase refdb = repo.getRefDatabase();
      Collection<Ref> heads = refdb.getRefsByPrefix(Constants.R_HEADS);
      Collection<Ref> tags = refdb.getRefsByPrefix(Constants.R_TAGS);
      Map<String, Ref> refs = Maps.newHashMapWithExpectedSize(heads.size() + tags.size());
      for (Ref r : Iterables.concat(heads, tags)) {
        refs.put(r.getName(), r);
      }
      return fromRefs(project, repo, commit, refs, Optional.of(userProvider));
    } catch (IOException e) {
      logger.atSevere().withCause(e).log(
          "Cannot verify permissions to commit object %s in repository %s", commit.name(), project);
      return false;
    }
  }
>>>>>>> BRANCH (4a6b05 Elasticsearch: Remove support for EOL 6.x versions)
}
