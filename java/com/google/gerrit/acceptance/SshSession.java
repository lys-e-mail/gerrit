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

package com.google.gerrit.acceptance;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import com.google.gerrit.acceptance.testsuite.account.TestAccount;
import com.google.gerrit.acceptance.testsuite.account.TestSshKeys;
<<<<<<< HEAD   (411d9a Merge "Clean up polygerrit_plugin rule" into stable-3.4)
=======
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.Session;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
>>>>>>> BRANCH (d8c23d Merge "Add initial stream-events acceptance tests" into stab)
import java.net.InetSocketAddress;
<<<<<<< HEAD   (411d9a Merge "Clean up polygerrit_plugin rule" into stable-3.4)
=======
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
>>>>>>> BRANCH (d8c23d Merge "Add initial stream-events acceptance tests" into stab)

public abstract class SshSession {
  protected final TestSshKeys sshKeys;
  protected final InetSocketAddress addr;
  protected final TestAccount account;
  protected String error;

  public SshSession(TestSshKeys sshKeys, InetSocketAddress addr, TestAccount account) {
    this.sshKeys = sshKeys;
    this.addr = addr;
    this.account = account;
  }

  public abstract void open() throws Exception;

  public abstract void close();

  public abstract String exec(String command) throws Exception;

  public abstract int execAndReturnStatus(String command) throws Exception;

  public Reader execAndReturnReader(String command) throws Exception {
    ChannelExec channel = (ChannelExec) getSession().openChannel("exec");
    channel.setCommand(command);
    channel.connect();

    return new InputStreamReader(channel.getInputStream(), StandardCharsets.UTF_8) {
      @Override
      public void close() throws IOException {
        super.close();
        channel.disconnect();
      }
    };
  }

  private boolean hasError() {
    return error != null;
  }

  public String getError() {
    return error;
  }

  public void assertSuccess() {
    assertWithMessage(getError()).that(hasError()).isFalse();
  }

  public void assertFailure() {
    assertThat(hasError()).isTrue();
  }

  public void assertFailure(String error) {
    assertThat(hasError()).isTrue();
    assertThat(getError()).contains(error);
  }

  public String getUrl() {
    StringBuilder b = new StringBuilder();
    b.append("ssh://");
    b.append(account.username().get());
    b.append("@");
    b.append(addr.getAddress().getHostAddress());
    b.append(":");
    b.append(addr.getPort());
    return b.toString();
  }

  protected String getUsername() {
    return account
        .username()
        .orElseThrow(
            () ->
                new IllegalStateException(
                    "account " + account.accountId() + " must have a username to use SSH"));
  }
}
