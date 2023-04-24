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

package com.google.gerrit.extensions.common;

/** API response containing values from the {@code change} section of {@code gerrit.config}. */
public class ChangeConfigInfo {
  public Boolean allowBlame;
  public Boolean disablePrivateChanges;
  public int updateDelay;
  public Boolean submitWholeTopic;
  public String mergeabilityComputationBehavior;
<<<<<<< HEAD   (9c7adf Set version to 3.8.0-SNAPSHOT)
  public Boolean enableRobotComments;
=======
  public Boolean enableAttentionSet;
  public Boolean enableAssignee;
  public Boolean conflictsPredicateEnabled;
>>>>>>> BRANCH (eeb3a1 Merge "Merge branch 'stable-3.6' into stable-3.7" into stabl)
}
