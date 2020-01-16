<<<<<<< HEAD   (4d4a94 Merge branch 'stable-3.0' into stable-3.1)
// Copyright (C) 2009 The Android Open Source Project
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

package com.google.gerrit.pgm.init;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.google.gerrit.pgm.init.api.ConsoleUI;
import com.google.gerrit.server.config.SitePaths;
import java.nio.file.Paths;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LibrariesTest {
  @Mock ConsoleUI ui;
  @Mock StaleLibraryRemover remover;

  @Test
  public void create() throws Exception {
    final SitePaths site = new SitePaths(Paths.get("."));

    Libraries lib =
        new Libraries(
            () -> new LibraryDownloader(ui, site, remover), Collections.emptyList(), false);

    assertNotNull(lib.mysqlDriver);
    verifyZeroInteractions(ui);
    verifyZeroInteractions(remover);
  }
}
=======
>>>>>>> BRANCH (36dcc2 ChangeEditModifier: Reject invalid file paths as '400 Bad Re)
