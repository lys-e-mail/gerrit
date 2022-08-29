// Copyright (C) 2022 The Android Open Source Project
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

package com.google.gerrit.testing;

import com.google.gerrit.entities.Project;
import com.google.gerrit.server.git.meta.VersionedMetaData;
import java.io.IOException;
import java.util.Optional;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

/** A {@link VersionedMetaData} that allows to read a row {@link Config} */
public class ReadVersionedMetaData extends VersionedMetaData {

  private Optional<Config> config;
  private final Project.NameKey repoName;
  private final Repository repo;
  private final String refName;
  private final String configName;

  public ReadVersionedMetaData(
      Project.NameKey repoName, Repository repo, String ref, String configName) {
    this.repoName = repoName;
    this.repo = repo;
    this.refName = ref;
    this.configName = configName;
  }

  @Override
  protected String getRefName() {
    return refName;
  }

  @Override
  protected void onLoad() throws IOException, ConfigInvalidException {
    config = Optional.ofNullable(readConfig(configName));
  }

  @Override
  protected boolean onSave(CommitBuilder commit) throws IOException, ConfigInvalidException {
    throw new UnsupportedOperationException();
  }

  public Config getConfig() throws ConfigInvalidException, IOException {
    this.load(repoName, repo);

    return config.orElseThrow(
        () ->
            new IllegalStateException(
                String.format("Could not load config from ref %s", getRefName())));
  }
}
