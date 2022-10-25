/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {getAppContext} from './app-context';
import '../test/common-test-setup';
import {ServiceWorkerInstaller} from './service-worker-installer';
import {assert} from '@open-wc/testing';
import {createDefaultPreferences} from '../constants/constants';
import {waitUntilObserved} from '../test/test-utils';
<<<<<<< HEAD   (ff92bd Merge "Simple refactor: Remove an unused method param from `)
import {testResolver} from '../test/common-test-setup';
import {userModelToken} from '../models/user/user-model';
=======
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)

suite('service worker installer tests', () => {
  test('init', async () => {
    const registerStub = sinon.stub(window.navigator.serviceWorker, 'register');
    const flagsService = getAppContext().flagsService;
<<<<<<< HEAD   (ff92bd Merge "Simple refactor: Remove an unused method param from `)
    const reportingService = getAppContext().reportingService;
    const userModel = testResolver(userModelToken);
    sinon.stub(flagsService, 'isEnabled').returns(true);
    new ServiceWorkerInstaller(flagsService, reportingService, userModel);
=======
    const userModel = getAppContext().userModel;
    sinon.stub(flagsService, 'isEnabled').returns(true);
    new ServiceWorkerInstaller(flagsService, userModel);
>>>>>>> BRANCH (8ab81b Merge branch 'stable-3.6' into stable-3.7)
    const prefs = {
      ...createDefaultPreferences(),
      allow_browser_notifications: true,
    };
    userModel.setPreferences(prefs);
    await waitUntilObserved(
      userModel.preferences$,
      pref => pref.allow_browser_notifications === true
    );
    await waitUntilObserved(
      userModel.preferences$,
      pref => pref.allow_browser_notifications === true
    );
    assert.isTrue(registerStub.called);
  });
});
