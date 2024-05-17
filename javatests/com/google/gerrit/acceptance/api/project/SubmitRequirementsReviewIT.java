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

package com.google.gerrit.acceptance.api.project;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.gerrit.acceptance.AbstractDaemonTest;
import com.google.gerrit.acceptance.RestResponse;
import com.google.gerrit.acceptance.testsuite.project.ProjectOperations;
import com.google.gerrit.entities.Project;
import com.google.gerrit.extensions.common.BatchSubmitRequirementInput;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.common.SubmitRequirementInput;
import com.google.inject.Inject;
import java.util.List;
import org.eclipse.jgit.lib.Config;
import org.junit.Before;
import org.junit.Test;

public class SubmitRequirementsReviewIT extends AbstractDaemonTest {
  @Inject
  private ProjectOperations projectOperations;

  private Project.NameKey defaultMessageProject;
  private Project.NameKey customMessageProject;

  @Before
  public void setUp() throws Exception {
    defaultMessageProject = projectOperations.newProject().create();
    customMessageProject = projectOperations.newProject().create();
  }

  @Test
  public void createSubmitRequirementsChangeWithDefaultMessage() throws Exception {
    SubmitRequirementInput fooInput = new SubmitRequirementInput();
    fooInput.name = "Foo";
    fooInput.description = "SR description";
    fooInput.applicabilityExpression = "topic:foo";
    fooInput.submittabilityExpression = "label:code-review=+2";

    BatchSubmitRequirementInput input = new BatchSubmitRequirementInput();
    input.create = ImmutableList.of(fooInput);

    RestResponse rep =
        adminRestSession.put("/projects/" + defaultMessageProject.get() + "/submit_requirements:review", input);
    rep.assertCreated();

    List<ChangeInfo> result =
        gApi.changes()
            .query("project:" + defaultMessageProject.get() + " AND ref:refs/meta/config")
            .get();
    ChangeInfo changeInfo = Iterables.getOnlyElement(result);
    assertThat(changeInfo.subject).isEqualTo("Review submit requirements change");

    Config config = new Config();
    config.fromText(
        gApi.changes()
            .id(changeInfo.changeId)
            .revision(1)
            .file("project.config")
            .content()
            .asString());
    assertThat(config.getString("submit-requirement", "Foo", "description"))
        .isEqualTo("SR description");
    assertThat(config.getString("submit-requirement", "Foo", "applicableIf"))
        .isEqualTo("topic:foo");
    assertThat(config.getString("submit-requirement", "Foo", "submittableIf"))
        .isEqualTo("label:code-review=+2");
  }

  @Test
  public void createSubmitRequirementsChangeWithCustomMessage() throws Exception {
    SubmitRequirementInput fooInput = new SubmitRequirementInput();
    fooInput.name = "Foo";
    fooInput.description = "SR description";
    fooInput.applicabilityExpression = "topic:foo";
    fooInput.submittabilityExpression = "label:code-review=+2";

    BatchSubmitRequirementInput input = new BatchSubmitRequirementInput();
    input.create = ImmutableList.of(fooInput);
    String customMessage = "test custom message";
    input.commitMessage = customMessage;

    RestResponse rep =
        adminRestSession.put("/projects/" + customMessageProject.get() + "/submit_requirements:review", input);
    rep.assertCreated();

    List<ChangeInfo> result =
        gApi.changes()
            .query("project:" + customMessageProject.get() + " AND ref:refs/meta/config")
            .get();

    ChangeInfo changeInfo = Iterables.getOnlyElement(result);
    assertThat(changeInfo.subject).isEqualTo(customMessage);

    Config config = new Config();
    config.fromText(
        gApi.changes()
            .id(changeInfo.changeId)
            .revision(1)
            .file("project.config")
            .content()
            .asString());
    assertThat(config.getString("submit-requirement", "Foo", "description"))
        .isEqualTo("SR description");
    assertThat(config.getString("submit-requirement", "Foo", "applicableIf"))
        .isEqualTo("topic:foo");
    assertThat(config.getString("submit-requirement", "Foo", "submittableIf"))
        .isEqualTo("label:code-review=+2");
  }
}
