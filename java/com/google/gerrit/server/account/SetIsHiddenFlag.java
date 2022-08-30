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

import com.google.gerrit.extensions.restapi.ResourceConflictException;
import com.google.gerrit.extensions.restapi.ResourceNotFoundException;
import com.google.gerrit.extensions.restapi.Response;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.ServerInitiated;
import com.google.gerrit.server.permissions.GlobalPermission;
import com.google.gerrit.server.permissions.PermissionBackend;
import com.google.gerrit.server.permissions.PermissionBackendException;
import com.google.inject.Inject;
import com.google.inject.Provider;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jgit.errors.ConfigInvalidException;

public class SetIsHiddenFlag {
  private final Provider<AccountsUpdate> accountsUpdateProvider;
  private final Provider<CurrentUser> self;
  private final PermissionBackend permissionBackend;

  @Inject
  SetIsHiddenFlag(
      @ServerInitiated Provider<AccountsUpdate> accountsUpdateProvider,
      Provider<CurrentUser> self,
      PermissionBackend permissionBackend) {
    this.self = self;
    this.permissionBackend = permissionBackend;
    this.accountsUpdateProvider = accountsUpdateProvider;
  }

  public Response<?> setHidden(CurrentUser account, boolean value)
      throws RestApiException, IOException, ConfigInvalidException, PermissionBackendException {
    if (!self.get().hasSameAccountId(account)) {
      permissionBackend.currentUser().check(GlobalPermission.MODIFY_ACCOUNT);
    }
    String updateMessage = String.format("%s Account via API", value ? "Hide" : "Unhide");
    AtomicBoolean previousValue = new AtomicBoolean(false);
    Optional<AccountState> updatedAccount =
        accountsUpdateProvider
            .get()
            .update(
                updateMessage,
                account.getAccountId(),
                (a, u) -> {
                  previousValue.set(a.account().isHidden());
                  if (a.account().isHidden() != value) {
                    u.setHidden(value);
                  }
                });
    if (!updatedAccount.isPresent()) {
      throw new ResourceNotFoundException("account not found");
    }
    if (value) {
      if (previousValue.get()) {
        return Response.ok();
      } else {
        return Response.created();
      }
    } else {
      if (!previousValue.get()) {
        throw new ResourceConflictException("account not hidden");
      }
      return Response.none();
    }
  }
}
