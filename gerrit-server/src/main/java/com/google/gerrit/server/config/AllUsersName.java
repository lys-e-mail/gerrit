<<<<<<< HEAD   (439d48 Merge "Scripts: Use bash in shebang" into stable-2.16)
=======
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

package com.google.gerrit.server.config;

import com.google.gerrit.reviewdb.client.Project;

/** Special name of the project in which meta data for all users is stored. */
public class AllUsersName extends Project.NameKey {
  private static final long serialVersionUID = 1L;

  public AllUsersName(String name) {
    super(name);
  }
}
>>>>>>> BRANCH (d7b29f Consistently define default serialVersionUID)
