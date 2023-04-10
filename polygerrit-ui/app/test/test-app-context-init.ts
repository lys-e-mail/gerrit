/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */

// Init app context before any other imports
import {create, Registry, Finalizable} from '../services/registry';
import {AppContext} from '../services/app-context';
import {grReportingMock} from '../services/gr-reporting/gr-reporting_mock';
import {grRestApiMock} from './mocks/gr-rest-api_mock';
import {grStorageMock} from '../services/storage/gr-storage_mock';
import {GrAuthMock} from '../services/gr-auth/gr-auth_mock';
import {FlagsServiceImplementation} from '../services/flags/flags_impl';
import {MockHighlightService} from '../services/highlight/highlight-service-mock';
import {createAppDependencies, Creator} from '../services/app-context-init';
import {navigationToken} from '../elements/core/gr-navigation/gr-navigation';
import {DependencyToken} from '../models/dependency';
import {storageServiceToken} from '../services/storage/gr-storage_impl';
import {highlightServiceToken} from '../services/highlight/highlight-service';

export function createTestAppContext(): AppContext & Finalizable {
  const appRegistry: Registry<AppContext> = {
    flagsService: (_ctx: Partial<AppContext>) =>
      new FlagsServiceImplementation(),
    reportingService: (_ctx: Partial<AppContext>) => grReportingMock,
    authService: (_ctx: Partial<AppContext>) => new GrAuthMock(),
    restApiService: (_ctx: Partial<AppContext>) => grRestApiMock,
  };
  return create<AppContext>(appRegistry);
}

export function createTestDependencies(
  appContext: AppContext,
  resolver: <T>(token: DependencyToken<T>) => T
): Map<DependencyToken<unknown>, Creator<unknown>> {
<<<<<<< HEAD   (baef2e Limit index query results in Move Change REST API)
  const dependencies = createAppDependencies(appContext, resolver);
  dependencies.set(storageServiceToken, () => grStorageMock);
=======
  const dependencies = new Map<DependencyToken<unknown>, Creator<unknown>>();
  const browserModel = () => new BrowserModel(appContext.userModel);
  dependencies.set(browserModelToken, browserModel);

  const adminViewModelCreator = () => new AdminViewModel();
  dependencies.set(adminViewModelToken, adminViewModelCreator);
  const agreementViewModelCreator = () => new AgreementViewModel();
  dependencies.set(agreementViewModelToken, agreementViewModelCreator);
  const changeViewModelCreator = () => new ChangeViewModel();
  dependencies.set(changeViewModelToken, changeViewModelCreator);
  const dashboardViewModelCreator = () => new DashboardViewModel();
  dependencies.set(dashboardViewModelToken, dashboardViewModelCreator);
  const diffViewModelCreator = () => new DiffViewModel();
  dependencies.set(diffViewModelToken, diffViewModelCreator);
  const documentationViewModelCreator = () => new DocumentationViewModel();
  dependencies.set(documentationViewModelToken, documentationViewModelCreator);
  const editViewModelCreator = () => new EditViewModel();
  dependencies.set(editViewModelToken, editViewModelCreator);
  const groupViewModelCreator = () => new GroupViewModel();
  dependencies.set(groupViewModelToken, groupViewModelCreator);
  const pluginViewModelCreator = () => new PluginViewModel();
  dependencies.set(pluginViewModelToken, pluginViewModelCreator);
  const repoViewModelCreator = () => new RepoViewModel();
  dependencies.set(repoViewModelToken, repoViewModelCreator);
  const searchViewModelCreator = () =>
    new SearchViewModel(appContext.restApiService, appContext.userModel, () =>
      resolver(navigationToken)
    );
  dependencies.set(searchViewModelToken, searchViewModelCreator);
  const settingsViewModelCreator = () => new SettingsViewModel();
  dependencies.set(settingsViewModelToken, settingsViewModelCreator);

  const routerCreator = () =>
    new GrRouter(
      appContext.reportingService,
      appContext.routerModel,
      appContext.restApiService,
      resolver(adminViewModelToken),
      resolver(agreementViewModelToken),
      resolver(changeViewModelToken),
      resolver(dashboardViewModelToken),
      resolver(diffViewModelToken),
      resolver(documentationViewModelToken),
      resolver(editViewModelToken),
      resolver(groupViewModelToken),
      resolver(pluginViewModelToken),
      resolver(repoViewModelToken),
      resolver(searchViewModelToken),
      resolver(settingsViewModelToken)
    );
  dependencies.set(routerToken, routerCreator);
>>>>>>> BRANCH (0a4ddc gr-change-actions: use change-model for latestPatchNum)
  dependencies.set(navigationToken, () => {
    return {
      setUrl: () => {},
      replaceUrl: () => {},
      finalize: () => {},
    };
  });
  dependencies.set(
    highlightServiceToken,
    () => new MockHighlightService(appContext.reportingService)
  );
  return dependencies;
}
