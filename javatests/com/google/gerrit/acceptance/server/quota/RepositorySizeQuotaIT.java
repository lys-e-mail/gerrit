// Copyright (C) 2019 The Android Open Source Project
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

package com.google.gerrit.acceptance.server.quota;

import static com.google.common.truth.Truth.assertThat;
import static com.google.gerrit.server.quota.QuotaGroupDefinitions.REPOSITORY_SIZE_GROUP;
import static com.google.gerrit.server.quota.QuotaResponse.ok;
import static com.google.gerrit.testing.GerritJUnit.assertThrows;
<<<<<<< HEAD   (6e730e Merge "AccountPatchReview mariadb: fix key length" into stab)
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
=======
import static org.easymock.EasyMock.anyLong;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.resetToStrict;
import static org.easymock.EasyMock.verify;
>>>>>>> BRANCH (cda59e RepositorySizeQuotaIT: change try/catch to assertThrows)

import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.UseLocalDisk;
import com.google.gerrit.extensions.config.FactoryModule;
import com.google.gerrit.server.CurrentUser;
import com.google.gerrit.server.quota.QuotaBackend;
import com.google.gerrit.server.quota.QuotaResponse;
import com.google.inject.Module;
import java.util.Collections;
<<<<<<< HEAD   (6e730e Merge "AccountPatchReview mariadb: fix key length" into stab)
import org.eclipse.jgit.api.errors.TooLargeObjectInPackException;
=======
import org.easymock.EasyMock;
import org.eclipse.jgit.api.errors.TooLargePackException;
>>>>>>> BRANCH (cda59e RepositorySizeQuotaIT: change try/catch to assertThrows)
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.Before;
import org.junit.Test;

@UseLocalDisk
public class RepositorySizeQuotaIT extends AbstractDaemonTest {
  private static final QuotaBackend.WithResource quotaBackendWithResource =
      mock(QuotaBackend.WithResource.class);
  private static final QuotaBackend.WithUser quotaBackendWithUser =
      mock(QuotaBackend.WithUser.class);

  @Override
  public Module createModule() {
    return new FactoryModule() {
      @Override
      public void configure() {
        bind(QuotaBackend.class)
            .toInstance(
                new QuotaBackend() {
                  @Override
                  public WithUser currentUser() {
                    return quotaBackendWithUser;
                  }

                  @Override
                  public WithUser user(CurrentUser user) {
                    return quotaBackendWithUser;
                  }
                });
      }
    };
  }

  @Before
  public void setUp() {
    clearInvocations(quotaBackendWithResource);
    clearInvocations(quotaBackendWithUser);
  }

  @Test
  public void pushWithAvailableTokens() throws Exception {
<<<<<<< HEAD   (6e730e Merge "AccountPatchReview mariadb: fix key length" into stab)
    when(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .thenReturn(singletonAggregation(ok(276L)));
    when(quotaBackendWithResource.requestTokens(eq(REPOSITORY_SIZE_GROUP), anyLong()))
        .thenReturn(singletonAggregation(ok()));
    when(quotaBackendWithUser.project(project)).thenReturn(quotaBackendWithResource);
=======
    expect(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .andReturn(singletonAggregation(ok(277L)))
        .times(2);
    expect(quotaBackendWithResource.requestTokens(eq(REPOSITORY_SIZE_GROUP), anyLong()))
        .andReturn(singletonAggregation(ok()));
    expect(quotaBackendWithUser.project(project)).andReturn(quotaBackendWithResource).anyTimes();
    replay(quotaBackendWithResource);
    replay(quotaBackendWithUser);
>>>>>>> BRANCH (cda59e RepositorySizeQuotaIT: change try/catch to assertThrows)
    pushCommit();
    verify(quotaBackendWithResource, times(2)).availableTokens(REPOSITORY_SIZE_GROUP);
  }

  @Test
  public void pushWithNotSufficientTokens() throws Exception {
    long availableTokens = 1L;
<<<<<<< HEAD   (6e730e Merge "AccountPatchReview mariadb: fix key length" into stab)
    when(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .thenReturn(singletonAggregation(ok(availableTokens)));
    when(quotaBackendWithUser.project(project)).thenReturn(quotaBackendWithResource);
    TooLargeObjectInPackException thrown =
        assertThrows(TooLargeObjectInPackException.class, () -> pushCommit());
    assertThat(thrown).hasMessageThat().contains("Object too large");
    assertThat(thrown)
        .hasMessageThat()
        .contains(String.format("Max object size limit is %d bytes.", availableTokens));
=======
    expect(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .andReturn(singletonAggregation(ok(availableTokens)))
        .anyTimes();
    expect(quotaBackendWithUser.project(project)).andReturn(quotaBackendWithResource).anyTimes();
    replay(quotaBackendWithResource);
    replay(quotaBackendWithUser);
    assertThat(assertThrows(TooLargePackException.class, () -> pushCommit()).getMessage())
        .contains(
            String.format(
                "Pack exceeds the limit of %d bytes, rejecting the pack", availableTokens));
    verify(quotaBackendWithUser);
    verify(quotaBackendWithResource);
>>>>>>> BRANCH (cda59e RepositorySizeQuotaIT: change try/catch to assertThrows)
  }

  @Test
  public void errorGettingAvailableTokens() throws Exception {
    String msg = "quota error";
<<<<<<< HEAD   (6e730e Merge "AccountPatchReview mariadb: fix key length" into stab)
    when(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .thenReturn(singletonAggregation(QuotaResponse.error(msg)));
    when(quotaBackendWithUser.project(project)).thenReturn(quotaBackendWithResource);
    assertThrows(TransportException.class, () -> pushCommit());
=======
    expect(quotaBackendWithResource.availableTokens(REPOSITORY_SIZE_GROUP))
        .andReturn(singletonAggregation(QuotaResponse.error(msg)))
        .anyTimes();
    expect(quotaBackendWithUser.project(project)).andReturn(quotaBackendWithResource).anyTimes();
    replay(quotaBackendWithResource);
    replay(quotaBackendWithUser);

    assertThrows(TransportException.class, () -> pushCommit());

    verify(quotaBackendWithUser);
    verify(quotaBackendWithResource);
>>>>>>> BRANCH (cda59e RepositorySizeQuotaIT: change try/catch to assertThrows)
  }

  private void pushCommit() throws Exception {
    createCommitAndPush(testRepo, "refs/heads/master", "test 01", "file.test", "some content");
  }

  private static QuotaResponse.Aggregated singletonAggregation(QuotaResponse response) {
    return QuotaResponse.Aggregated.create(Collections.singleton(response));
  }
}
