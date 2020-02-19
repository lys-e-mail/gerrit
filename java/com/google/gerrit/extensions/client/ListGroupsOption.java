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

package com.google.gerrit.extensions.client;

<<<<<<< HEAD   (de6373 Merge "Fix a dependency injection runtime error in DeleteZom)
=======
import java.util.EnumSet;
import java.util.Set;

>>>>>>> BRANCH (9ec0b9 ChangeNotificationsIT: Fix eclipse warning(s) about static u)
/** Output options available when using {@code /groups/} RPCs. */
public enum ListGroupsOption implements ListOption {
  /** Return information on the direct group members. */
  MEMBERS(0),

  /** Return information on the directly included groups. */
  INCLUDES(1);

  private final int value;

  ListGroupsOption(int v) {
    this.value = v;
  }

  @Override
  public int getValue() {
    return value;
  }
<<<<<<< HEAD   (de6373 Merge "Fix a dependency injection runtime error in DeleteZom)
=======

  public static Set<ListGroupsOption> fromBits(int v) {
    EnumSet<ListGroupsOption> r = EnumSet.noneOf(ListGroupsOption.class);
    for (ListGroupsOption o : ListGroupsOption.values()) {
      if ((v & (1 << o.value)) != 0) {
        r.add(o);
        v &= ~(1 << o.value);
      }
      if (v == 0) {
        return r;
      }
    }
    if (v != 0) {
      throw new IllegalArgumentException("unknown " + Integer.toHexString(v));
    }
    return r;
  }

  public static int toBits(Set<ListGroupsOption> set) {
    int r = 0;
    for (ListGroupsOption o : set) {
      r |= 1 << o.value;
    }
    return r;
  }
>>>>>>> BRANCH (9ec0b9 ChangeNotificationsIT: Fix eclipse warning(s) about static u)
}
