// Copyright (C) 2017 The Android Open Source Project
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

package com.google.gerrit.httpd.raw;

import static com.google.common.truth.Truth.assertThat;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

<<<<<<< HEAD   (eb61fb Upgrade gitiles to 0.3-2)
import com.google.common.collect.ImmutableList;
import com.google.gerrit.extensions.api.GerritApi;
import com.google.gerrit.extensions.api.accounts.Accounts;
import com.google.gerrit.extensions.api.config.Config;
import com.google.gerrit.extensions.api.config.Server;
import com.google.gerrit.extensions.common.ServerInfo;
import com.google.gerrit.extensions.restapi.AuthException;
import com.google.gerrit.util.http.testutil.FakeHttpServletRequest;
import com.google.gerrit.util.http.testutil.FakeHttpServletResponse;
=======
import com.google.gerrit.testing.GerritBaseTests;
import java.net.URISyntaxException;
import java.util.Map;
>>>>>>> BRANCH (999dd7 Merge branch 'stable-2.16' into stable-3.0)
import org.junit.Test;

public class IndexServletTest {

  @Test
<<<<<<< HEAD   (eb61fb Upgrade gitiles to 0.3-2)
  public void renderTemplate() throws Exception {
    Accounts accountsApi = createMock(Accounts.class);
    expect(accountsApi.self()).andThrow(new AuthException("user needs to be authenticated"));
=======
  public void noPathAndNoCDN() throws URISyntaxException {
    Map<String, Object> data = IndexServlet.getTemplateData("http://example.com/", null, null);
    assertThat(data.get("canonicalPath")).isEqualTo("");
    assertThat(data.get("staticResourcePath").toString()).isEqualTo("");
  }
>>>>>>> BRANCH (999dd7 Merge branch 'stable-2.16' into stable-3.0)

<<<<<<< HEAD   (eb61fb Upgrade gitiles to 0.3-2)
    Server serverApi = createMock(Server.class);
    expect(serverApi.getVersion()).andReturn("123");
    expect(serverApi.topMenus()).andReturn(ImmutableList.of());
    ServerInfo serverInfo = new ServerInfo();
    serverInfo.defaultTheme = "my-default-theme";
    expect(serverApi.getInfo()).andReturn(serverInfo);
=======
  @Test
  public void pathAndNoCDN() throws URISyntaxException {
    Map<String, Object> data =
        IndexServlet.getTemplateData("http://example.com/gerrit/", null, null);
    assertThat(data.get("canonicalPath")).isEqualTo("/gerrit");
    assertThat(data.get("staticResourcePath").toString()).isEqualTo("/gerrit");
  }
>>>>>>> BRANCH (999dd7 Merge branch 'stable-2.16' into stable-3.0)

<<<<<<< HEAD   (eb61fb Upgrade gitiles to 0.3-2)
    Config configApi = createMock(Config.class);
    expect(configApi.server()).andReturn(serverApi);
=======
  @Test
  public void noPathAndCDN() throws URISyntaxException {
    Map<String, Object> data =
        IndexServlet.getTemplateData("http://example.com/", "http://my-cdn.com/foo/bar/", null);
    assertThat(data.get("canonicalPath")).isEqualTo("");
    assertThat(data.get("staticResourcePath").toString()).isEqualTo("http://my-cdn.com/foo/bar/");
  }
>>>>>>> BRANCH (999dd7 Merge branch 'stable-2.16' into stable-3.0)

<<<<<<< HEAD   (eb61fb Upgrade gitiles to 0.3-2)
    GerritApi gerritApi = createMock(GerritApi.class);
    expect(gerritApi.accounts()).andReturn(accountsApi);
    expect(gerritApi.config()).andReturn(configApi);
=======
  @Test
  public void pathAndCDN() throws URISyntaxException {
    Map<String, Object> data =
        IndexServlet.getTemplateData(
            "http://example.com/gerrit", "http://my-cdn.com/foo/bar/", null);
    assertThat(data.get("canonicalPath")).isEqualTo("/gerrit");
    assertThat(data.get("staticResourcePath").toString()).isEqualTo("http://my-cdn.com/foo/bar/");
  }
>>>>>>> BRANCH (999dd7 Merge branch 'stable-2.16' into stable-3.0)

    String testCanonicalUrl = "foo-url";
    String testCdnPath = "bar-cdn";
    String testFaviconURL = "zaz-url";
    IndexServlet servlet =
        new IndexServlet(testCanonicalUrl, testCdnPath, testFaviconURL, gerritApi);

    FakeHttpServletResponse response = new FakeHttpServletResponse();

    replay(gerritApi);
    replay(configApi);
    replay(serverApi);
    replay(accountsApi);

    servlet.doGet(new FakeHttpServletRequest(), response);

    verify(gerritApi);
    verify(configApi);
    verify(serverApi);
    verify(accountsApi);

    String output = response.getActualBodyString();
    assertThat(output).contains("<!DOCTYPE html>");
    assertThat(output).contains("window.CANONICAL_PATH = '" + testCanonicalUrl);
    assertThat(output).contains("<link rel=\"preload\" href=\"" + testCdnPath);
    assertThat(output)
        .contains(
            "<link rel=\"icon\" type=\"image/x-icon\" href=\""
                + testCanonicalUrl
                + "/"
                + testFaviconURL);
    assertThat(output)
        .contains(
            "window.INITIAL_DATA = JSON.parse("
                + "'\\x7b\\x22\\/config\\/server\\/version\\x22: \\x22123\\x22, "
                + "\\x22\\/config\\/server\\/info\\x22: \\x7b\\x22default_theme\\x22:"
                + "\\x22my-default-theme\\x22\\x7d, \\x22\\/config\\/server\\/top-menus\\x22: "
                + "\\x5b\\x5d\\x7d');</script>");
  }
}
