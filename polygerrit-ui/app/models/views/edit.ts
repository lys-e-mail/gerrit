/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {
  EDIT,
  NumericChangeId,
  RepoName,
  RevisionPatchSetNum,
} from '../../api/rest-api';
import {GerritView} from '../../services/router/router-model';
import {
  encodeURL,
  getBaseUrl,
  getPatchRangeExpression,
} from '../../utils/url-util';
import {define} from '../dependency';
import {Model} from '../model';
import {ViewState} from './base';

export interface EditViewState extends ViewState {
  view: GerritView.EDIT;
  changeNum: NumericChangeId;
  repo: RepoName;
  path: string;
  patchNum: RevisionPatchSetNum;
  lineNum?: number;
}

export function createEditUrl(state: Omit<EditViewState, 'view'>): string {
  if (state.patchNum === undefined) {
    state = {...state, patchNum: EDIT};
  }
  let range = getPatchRangeExpression(state);
  if (range.length) range = '/' + range;

  let suffix = `${range}/${encodeURL(state.path || '', true)}`;
  suffix += ',edit';

  if (state.lineNum) {
    suffix += '#';
    suffix += state.lineNum;
  }

<<<<<<< HEAD   (9f3627 Merge changes I2cbc6c31,I8792650f)
  if (state.repo) {
    const encodedProject = encodeURL(state.repo, true);
=======
  if (state.project) {
    const encodedProject = encodeURL(state.project, true);
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
    return `${getBaseUrl()}/c/${encodedProject}/+/${state.changeNum}${suffix}`;
  } else {
    return `${getBaseUrl()}/c/${state.changeNum}${suffix}`;
  }
}

export const editViewModelToken = define<EditViewModel>('edit-view-model');

export class EditViewModel extends Model<EditViewState | undefined> {
  constructor() {
    super(undefined);
  }
}
