/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {AppContext} from './app-context';
import {create, Finalizable, Registry} from './registry';
import {DependencyToken} from '../models/dependency';
import {FlagsServiceImplementation} from './flags/flags_impl';
import {GrReporting} from './gr-reporting/gr-reporting_impl';
import {Auth} from './gr-auth/gr-auth_impl';
import {GrRestApiServiceImpl} from './gr-rest-api/gr-rest-api-impl';
import {ChangeModel, changeModelToken} from '../models/change/change-model';
import {FilesModel, filesModelToken} from '../models/change/files-model';
import {ChecksModel, checksModelToken} from '../models/checks/checks-model';
import {GrStorageService, storageServiceToken} from './storage/gr-storage_impl';
import {UserModel, userModelToken} from '../models/user/user-model';
import {
  CommentsModel,
  commentsModelToken,
} from '../models/comments/comments-model';
import {RouterModel, routerModelToken} from './router/router-model';
import {
  ShortcutsService,
  shortcutsServiceToken,
} from './shortcuts/shortcuts-service';
import {assertIsDefined} from '../utils/common-util';
import {ConfigModel, configModelToken} from '../models/config/config-model';
import {BrowserModel, browserModelToken} from '../models/browser/browser-model';
import {
  HighlightService,
  highlightServiceToken,
} from './highlight/highlight-service';
import {
  AccountsModel,
  accountsModelToken,
} from '../models/accounts-model/accounts-model';
import {
  DashboardViewModel,
  dashboardViewModelToken,
} from '../models/views/dashboard';
import {
  SettingsViewModel,
  settingsViewModelToken,
} from '../models/views/settings';
import {GrRouter, routerToken} from '../elements/core/gr-router/gr-router';
import {AdminViewModel, adminViewModelToken} from '../models/views/admin';
import {
  AgreementViewModel,
  agreementViewModelToken,
} from '../models/views/agreement';
import {ChangeViewModel, changeViewModelToken} from '../models/views/change';
import {
  DocumentationViewModel,
  documentationViewModelToken,
} from '../models/views/documentation';
import {GroupViewModel, groupViewModelToken} from '../models/views/group';
import {PluginViewModel, pluginViewModelToken} from '../models/views/plugin';
import {RepoViewModel, repoViewModelToken} from '../models/views/repo';
import {SearchViewModel, searchViewModelToken} from '../models/views/search';
<<<<<<< HEAD   (baef2e Limit index query results in Move Change REST API)
import {navigationToken} from '../elements/core/gr-navigation/gr-navigation';
import {
  PluginLoader,
  pluginLoaderToken,
} from '../elements/shared/gr-js-api-interface/gr-plugin-loader';
import {authServiceToken} from './gr-auth/gr-auth';
import {
  ServiceWorkerInstaller,
  serviceWorkerInstallerToken,
} from './service-worker-installer';
=======
import {
  NavigationService,
  navigationToken,
} from '../elements/core/gr-navigation/gr-navigation';
>>>>>>> BRANCH (0a4ddc gr-change-actions: use change-model for latestPatchNum)

/**
 * The AppContext lazy initializator for all services
 */
export function createAppContext(): AppContext & Finalizable {
  const appRegistry: Registry<AppContext> = {
    flagsService: (_ctx: Partial<AppContext>) =>
      new FlagsServiceImplementation(),
    reportingService: (ctx: Partial<AppContext>) => {
      assertIsDefined(ctx.flagsService, 'flagsService)');
      return new GrReporting(ctx.flagsService);
    },
    authService: (_ctx: Partial<AppContext>) => new Auth(),
    restApiService: (ctx: Partial<AppContext>) => {
      assertIsDefined(ctx.authService, 'authService');
      return new GrRestApiServiceImpl(ctx.authService);
    },
  };
  return create<AppContext>(appRegistry);
}

export type Creator<T> = () => T & Finalizable;

// Dependencies are provided as creator functions to ensure that they are
// not created until they are utilized.
// This is mainly useful in tests: E.g. don't create a
// change-model in change-model_test.ts because it creates one in the test
// after setting up stubs.
export function createAppDependencies(
<<<<<<< HEAD   (baef2e Limit index query results in Move Change REST API)
  appContext: AppContext,
  resolver: <T>(token: DependencyToken<T>) => T
): Map<DependencyToken<unknown>, Creator<unknown>> {
  return new Map<DependencyToken<unknown>, Creator<unknown>>([
    [authServiceToken, () => appContext.authService],
    [routerModelToken, () => new RouterModel()],
    [userModelToken, () => new UserModel(appContext.restApiService)],
    [browserModelToken, () => new BrowserModel(resolver(userModelToken))],
    [accountsModelToken, () => new AccountsModel(appContext.restApiService)],
    [adminViewModelToken, () => new AdminViewModel()],
    [agreementViewModelToken, () => new AgreementViewModel()],
    [changeViewModelToken, () => new ChangeViewModel()],
    [dashboardViewModelToken, () => new DashboardViewModel()],
    [documentationViewModelToken, () => new DocumentationViewModel()],
    [groupViewModelToken, () => new GroupViewModel()],
    [pluginViewModelToken, () => new PluginViewModel()],
    [repoViewModelToken, () => new RepoViewModel()],
    [
      searchViewModelToken,
      () =>
        new SearchViewModel(
          appContext.restApiService,
          resolver(userModelToken),
          () => resolver(navigationToken)
        ),
    ],
    [settingsViewModelToken, () => new SettingsViewModel()],
    [
      routerToken,
      () =>
        new GrRouter(
          appContext.reportingService,
          resolver(routerModelToken),
          appContext.restApiService,
          resolver(adminViewModelToken),
          resolver(agreementViewModelToken),
          resolver(changeViewModelToken),
          resolver(dashboardViewModelToken),
          resolver(documentationViewModelToken),
          resolver(groupViewModelToken),
          resolver(pluginViewModelToken),
          resolver(repoViewModelToken),
          resolver(searchViewModelToken),
          resolver(settingsViewModelToken)
        ),
    ],
    [navigationToken, () => resolver(routerToken)],
    [
      changeModelToken,
      () =>
        new ChangeModel(
          resolver(navigationToken),
          resolver(changeViewModelToken),
          appContext.restApiService,
          resolver(userModelToken)
        ),
    ],
    [
      commentsModelToken,
      () =>
        new CommentsModel(
          resolver(changeViewModelToken),
          resolver(changeModelToken),
          resolver(accountsModelToken),
          appContext.restApiService,
          appContext.reportingService
        ),
    ],
    [
      filesModelToken,
      () =>
        new FilesModel(
          resolver(changeModelToken),
          resolver(commentsModelToken),
          appContext.restApiService
        ),
    ],
    [
      configModelToken,
      () =>
        new ConfigModel(resolver(changeModelToken), appContext.restApiService),
    ],
    [
      pluginLoaderToken,
      () =>
        new PluginLoader(
          appContext.reportingService,
          appContext.restApiService
        ),
    ],
    [
      checksModelToken,
      () =>
        new ChecksModel(
          resolver(changeViewModelToken),
          resolver(changeModelToken),
          appContext.reportingService,
          resolver(pluginLoaderToken).pluginsModel
        ),
    ],
    [
      shortcutsServiceToken,
      () =>
        new ShortcutsService(
          resolver(userModelToken),
          appContext.reportingService
        ),
    ],
    [storageServiceToken, () => new GrStorageService()],
    [
      highlightServiceToken,
      () => new HighlightService(appContext.reportingService),
    ],
    [
      serviceWorkerInstallerToken,
      () =>
        new ServiceWorkerInstaller(
          appContext.flagsService,
          appContext.reportingService,
          resolver(userModelToken)
        ),
    ],
  ]);
=======
  appContext: AppContext
): Map<DependencyToken<unknown>, Finalizable> {
  const dependencies = new Map<DependencyToken<unknown>, Finalizable>();
  const browserModel = new BrowserModel(appContext.userModel);
  dependencies.set(browserModelToken, browserModel);

  const adminViewModel = new AdminViewModel();
  dependencies.set(adminViewModelToken, adminViewModel);
  const agreementViewModel = new AgreementViewModel();
  dependencies.set(agreementViewModelToken, agreementViewModel);
  const changeViewModel = new ChangeViewModel();
  dependencies.set(changeViewModelToken, changeViewModel);
  const dashboardViewModel = new DashboardViewModel();
  dependencies.set(dashboardViewModelToken, dashboardViewModel);
  const diffViewModel = new DiffViewModel();
  dependencies.set(diffViewModelToken, diffViewModel);
  const documentationViewModel = new DocumentationViewModel();
  dependencies.set(documentationViewModelToken, documentationViewModel);
  const editViewModel = new EditViewModel();
  dependencies.set(editViewModelToken, editViewModel);
  const groupViewModel = new GroupViewModel();
  dependencies.set(groupViewModelToken, groupViewModel);
  const pluginViewModel = new PluginViewModel();
  dependencies.set(pluginViewModelToken, pluginViewModel);
  const repoViewModel = new RepoViewModel();
  dependencies.set(repoViewModelToken, repoViewModel);
  const searchViewModel = new SearchViewModel(
    appContext.restApiService,
    appContext.userModel,
    () => dependencies.get(navigationToken) as unknown as NavigationService
  );
  dependencies.set(searchViewModelToken, searchViewModel);
  const settingsViewModel = new SettingsViewModel();
  dependencies.set(settingsViewModelToken, settingsViewModel);

  const router = new GrRouter(
    appContext.reportingService,
    appContext.routerModel,
    appContext.restApiService,
    adminViewModel,
    agreementViewModel,
    changeViewModel,
    dashboardViewModel,
    diffViewModel,
    documentationViewModel,
    editViewModel,
    groupViewModel,
    pluginViewModel,
    repoViewModel,
    searchViewModel,
    settingsViewModel
  );
  dependencies.set(routerToken, router);
  dependencies.set(navigationToken, router);

  const changeModel = new ChangeModel(
    appContext.routerModel,
    appContext.restApiService,
    appContext.userModel
  );
  dependencies.set(changeModelToken, changeModel);

  const accountsModel = new AccountsModel(appContext.restApiService);

  const commentsModel = new CommentsModel(
    appContext.routerModel,
    changeModel,
    accountsModel,
    appContext.restApiService,
    appContext.reportingService
  );
  dependencies.set(commentsModelToken, commentsModel);

  const filesModel = new FilesModel(
    changeModel,
    commentsModel,
    appContext.restApiService
  );
  dependencies.set(filesModelToken, filesModel);

  const configModel = new ConfigModel(changeModel, appContext.restApiService);
  dependencies.set(configModelToken, configModel);

  const checksModel = new ChecksModel(
    appContext.routerModel,
    changeViewModel,
    changeModel,
    appContext.reportingService,
    appContext.pluginsModel
  );

  dependencies.set(checksModelToken, checksModel);

  const shortcutsService = new ShortcutsService(
    appContext.userModel,
    appContext.reportingService
  );
  dependencies.set(shortcutsServiceToken, shortcutsService);

  return dependencies;
>>>>>>> BRANCH (0a4ddc gr-change-actions: use change-model for latestPatchNum)
}
