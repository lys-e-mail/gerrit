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

package com.google.gerrit.server.account;

import com.google.gerrit.entities.Account;
import com.google.gerrit.server.config.AllUsersName;
import com.google.gerrit.server.config.AuthConfig;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Optional;
import org.eclipse.jgit.lib.Repository;

/** Factory for {@link AccountConfig}. */
@Singleton
public class AccountConfigFactory {

  /**
   * Config that holds {@link AccountConfig} related properties from {@link AuthConfig}.
   *
   * <p>This allows to bind a custom provider for {@code AuthConfig#defaultNewAccountHidden}
   */
  @ImplementedBy(ConfigImpl.class)
  public interface Config {

    /**
     * The value of {@code AuthConfig#defaultNewAccountHidden}, {@link Optional#empty} if not set.
     */
    Optional<Boolean> defaultNewAccountHidden();
  }

  /** Default implementation {@link Config} */
  @Singleton
  public static class ConfigImpl implements Config {
    private final Optional<Boolean> defaultNewAccountHidden;

    @Inject
    public ConfigImpl(AuthConfig authConfig) {
      this.defaultNewAccountHidden = authConfig.getDefaultNewAccountHidden();
    }

    @Override
    public Optional<Boolean> defaultNewAccountHidden() {
      return defaultNewAccountHidden;
    }
  }

  private final Optional<Boolean> defaultNewAccountHidden;

  @Inject
  public AccountConfigFactory(Config config) {
    this.defaultNewAccountHidden = config.defaultNewAccountHidden();
  }

  public AccountConfig create(
      Account.Id accountId, AllUsersName allUsersName, Repository allUsersRepo) {
    return new AccountConfig(accountId, allUsersName, allUsersRepo, defaultNewAccountHidden);
  }
}
