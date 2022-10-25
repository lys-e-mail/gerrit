/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {Observable} from 'rxjs';
<<<<<<< HEAD   (ff92bd Merge "Simple refactor: Remove an unused method param from `)
=======
import {Finalizable} from '../registry';
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
import {
  NumericChangeId,
  RevisionPatchSetNum,
  BasePatchSetNum,
} from '../../types/common';
import {Model} from '../../models/model';
import {select} from '../../utils/observable-util';
<<<<<<< HEAD   (ff92bd Merge "Simple refactor: Remove an unused method param from `)
import {define} from '../../models/dependency';
=======
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)

export enum GerritView {
  ADMIN = 'admin',
  AGREEMENTS = 'agreements',
  CHANGE = 'change',
  DASHBOARD = 'dashboard',
  DIFF = 'diff',
  DOCUMENTATION_SEARCH = 'documentation-search',
  EDIT = 'edit',
  GROUP = 'group',
  PLUGIN_SCREEN = 'plugin-screen',
  REPO = 'repo',
  SEARCH = 'search',
  SETTINGS = 'settings',
}

export interface RouterState {
  // Note that this router model view must be updated before view model state.
  view?: GerritView;
  changeNum?: NumericChangeId;
  patchNum?: RevisionPatchSetNum;
  basePatchNum?: BasePatchSetNum;
}

<<<<<<< HEAD   (ff92bd Merge "Simple refactor: Remove an unused method param from `)
export const routerModelToken = define<RouterModel>('router-model');
export class RouterModel extends Model<RouterState> {
=======
export class RouterModel extends Model<RouterState> implements Finalizable {
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
  readonly routerView$: Observable<GerritView | undefined> = select(
    this.state$,
    state => state.view
  );

  readonly routerChangeNum$: Observable<NumericChangeId | undefined> = select(
    this.state$,
    state => state.changeNum
  );

  readonly routerPatchNum$: Observable<RevisionPatchSetNum | undefined> =
    select(this.state$, state => state.patchNum);

  readonly routerBasePatchNum$: Observable<BasePatchSetNum | undefined> =
    select(this.state$, state => state.basePatchNum);

  constructor() {
    super({});
  }
}
