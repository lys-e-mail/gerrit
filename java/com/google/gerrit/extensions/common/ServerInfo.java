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

<<<<<<< HEAD   (7b1ba6 Merge branch 'stable-2.16' into stable-3.0)
=======
import java.util.List;
import java.util.Map;

>>>>>>> BRANCH (d4ba7f Merge changes from topic "motd" into stable-2.16)
public class ServerInfo {
  public AccountsInfo accounts;
  public AuthInfo auth;
  public ChangeConfigInfo change;
  public DownloadInfo download;
  public GerritInfo gerrit;
  public List<MessageOfTheDayInfo> messages;
  public Boolean noteDbEnabled;
  public PluginConfigInfo plugin;
  public SshdInfo sshd;
  public SuggestInfo suggest;
  public UserConfigInfo user;
  public ReceiveInfo receive;
  public String defaultTheme;
}
