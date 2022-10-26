/**
 * @license
 * Copyright 2021 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {Observable, combineLatest} from 'rxjs';
<<<<<<< HEAD   (9f3627 Merge changes I2cbc6c31,I8792650f)
=======
import {Finalizable} from '../../services/registry';
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
import {define} from '../dependency';
import {DiffViewMode} from '../../api/diff';
import {UserModel} from '../user/user-model';
import {Model} from '../model';
import {select} from '../../utils/observable-util';

// This value is somewhat arbitrary and not based on research or calculations.
const MAX_UNIFIED_DEFAULT_WINDOW_WIDTH_PX = 850;

export interface BrowserState {
  /**
   * We maintain the screen width in the state so that the app can react to
   * changes in the width such as automatically changing to unified diff view
   */
  screenWidth?: number;
}

const initialState: BrowserState = {};

export const browserModelToken = define<BrowserModel>('browser-model');

<<<<<<< HEAD   (9f3627 Merge changes I2cbc6c31,I8792650f)
export class BrowserModel extends Model<BrowserState> {
=======
export class BrowserModel extends Model<BrowserState> implements Finalizable {
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
  private readonly isScreenTooSmall$ = select(
    this.state$,
    state =>
      !!state.screenWidth &&
      state.screenWidth < MAX_UNIFIED_DEFAULT_WINDOW_WIDTH_PX
  );

  readonly diffViewMode$: Observable<DiffViewMode> = select(
    combineLatest([
      this.isScreenTooSmall$,
      this.userModel.preferenceDiffViewMode$,
    ]),
    ([isScreenTooSmall, preferenceDiffViewMode]) =>
      isScreenTooSmall ? DiffViewMode.UNIFIED : preferenceDiffViewMode
  );

  constructor(readonly userModel: UserModel) {
    super(initialState);
  }

  /* Observe the screen width so that the app can react to changes to it */
  observeWidth() {
    return new ResizeObserver(entries => {
      entries.forEach(entry => {
        this.setScreenWidth(entry.contentRect.width);
      });
    });
  }

  // Private but used in tests.
  setScreenWidth(screenWidth: number) {
    this.updateState({screenWidth});
  }
}
