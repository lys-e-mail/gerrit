/**
 * @license
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

export interface FlagsService {
  isEnabled(experimentId: string): boolean;
  enabledExperiments: string[];
}

/**
 * @desc Experiment ids used in Gerrit.
 */
export enum KnownExperimentId {
<<<<<<< HEAD   (83befb Merge branch 'stable-3.4')
  // Note that this flag is not supposed to be used by Gerrit itself, but can
  // be used by plugins. The new Checks UI will show up, if a plugin registers
  // with the new Checks plugin API.
  CI_REBOOT_CHECKS = 'UiFeature__ci_reboot_checks',
=======
>>>>>>> BRANCH (b2e672 Remove KnownExperimentId from gr-comment-thread)
  NEW_IMAGE_DIFF_UI = 'UiFeature__new_image_diff_ui',
<<<<<<< HEAD   (83befb Merge branch 'stable-3.4')
  COMMENT_CONTEXT = 'UiFeature__comment_context',
=======
>>>>>>> BRANCH (b2e672 Remove KnownExperimentId from gr-comment-thread)
  TOKEN_HIGHLIGHTING = 'UiFeature__token_highlighting',
}
