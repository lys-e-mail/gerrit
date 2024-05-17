// Copyright (C) 2016 The Android Open Source Project
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

package com.google.gerrit.extensions.api.accounts;

import com.google.gerrit.common.ConvertibleToProto;
import com.google.gerrit.extensions.restapi.DefaultInput;
import java.util.List;
import java.util.Objects;

@ConvertibleToProto
public class AccountInput {
  @DefaultInput public String username;
  public String name;
  public String displayName;
  public String email;
  public String sshKey;
  public String httpPassword;
  public List<String> groups;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AccountInput)) {
      return false;
    }
    AccountInput other = (AccountInput) o;
    return Objects.equals(username, other.username)
        && Objects.equals(name, other.name)
        && Objects.equals(displayName, other.displayName)
        && Objects.equals(email, other.email)
        && Objects.equals(sshKey, other.sshKey)
        && Objects.equals(httpPassword, other.httpPassword)
        && Objects.equals(groups, other.groups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, name, displayName, email, sshKey, httpPassword, groups);
  }
}
