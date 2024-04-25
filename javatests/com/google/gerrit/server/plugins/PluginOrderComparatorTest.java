// Copyright (C) 2024 The Android Open Source Project
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

package com.google.gerrit.server.plugins;

import static com.google.common.truth.Truth.assertThat;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.jar.Manifest;
import org.junit.Test;

public class PluginOrderComparatorTest {
  private static final Path FIRST_PLUGIN = Paths.get("01-plugin.jar");
  private static final Path LAST_PLUGIN = Paths.get("99-plugin.jar");
  private static final Map.Entry<String, Path> FIRST_ENTRY = Map.entry("first", FIRST_PLUGIN);
  private static final Map.Entry<String, Path> SECOND_ENTRY = Map.entry("second", LAST_PLUGIN);
  private static final Manifest EMPTY_MANIFEST = newManifest("");
  private static final Manifest API_MODULE_MANIFEST =
      newManifest("Gerrit-ApiModule: com.google.gerrit.UnitTest");

  @Test
  public void shouldOrderPluginsBasedOnFileName() {
    PluginOrderComparator comparator = new PluginOrderComparator(pluginPath -> EMPTY_MANIFEST);

    assertThat(comparator.compare(FIRST_ENTRY, SECOND_ENTRY)).isEqualTo(-1);
    assertThat(comparator.compare(SECOND_ENTRY, FIRST_ENTRY)).isEqualTo(1);
  }

  @Test
  public void shouldReturnPluginWithApiModuleFirst() {
    // return empty manifest for the first plugin and manifest with ApiModule for the last
    System.out.println(API_MODULE_MANIFEST.getMainAttributes().getValue("Gerrit-ApiModule"));
    PluginOrderComparator.ManifestLoader loader =
        pluginPath -> {
          if (pluginPath.equals(FIRST_PLUGIN)) {
            return EMPTY_MANIFEST;
          }
          if (pluginPath.equals(LAST_PLUGIN)) {
            return API_MODULE_MANIFEST;
          }
          throw new IllegalArgumentException("unsupported path: " + pluginPath);
        };

    PluginOrderComparator comparator = new PluginOrderComparator(loader);

    assertThat(comparator.compare(FIRST_ENTRY, SECOND_ENTRY)).isEqualTo(1);
    assertThat(comparator.compare(SECOND_ENTRY, FIRST_ENTRY)).isEqualTo(-1);
  }

  private static Manifest newManifest(String content) {
    String withEmptyLine = content + "\n";
    try {
      Manifest manifest = new Manifest();
      manifest.read(new ByteArrayInputStream(withEmptyLine.getBytes(UTF_8)));
      return manifest;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
