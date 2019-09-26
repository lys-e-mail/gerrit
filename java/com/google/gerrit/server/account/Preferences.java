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
package com.google.gerrit.server.account;

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
=======
import static com.google.common.base.Preconditions.checkState;
import static com.google.gerrit.server.config.ConfigUtil.loadSection;
import static com.google.gerrit.server.config.ConfigUtil.skipField;
import static com.google.gerrit.server.config.ConfigUtil.storeSection;
import static com.google.gerrit.server.git.UserConfigSections.CHANGE_TABLE;
import static com.google.gerrit.server.git.UserConfigSections.CHANGE_TABLE_COLUMN;
import static com.google.gerrit.server.git.UserConfigSections.KEY_ID;
import static com.google.gerrit.server.git.UserConfigSections.KEY_TARGET;
import static com.google.gerrit.server.git.UserConfigSections.KEY_URL;
import static java.util.Objects.requireNonNull;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.flogger.FluentLogger;
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)
import com.google.gerrit.common.Nullable;
import com.google.gerrit.extensions.client.DiffPreferencesInfo;
import com.google.gerrit.extensions.client.DiffPreferencesInfo.Whitespace;
import com.google.gerrit.extensions.client.EditPreferencesInfo;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.DateFormat;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.DefaultBase;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.DiffView;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.DownloadCommand;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.EmailFormat;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.EmailStrategy;
import com.google.gerrit.extensions.client.GeneralPreferencesInfo.TimeFormat;
import com.google.gerrit.extensions.client.KeyMapType;
import com.google.gerrit.extensions.client.MenuItem;
<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
import com.google.gerrit.extensions.client.Theme;
=======
import com.google.gerrit.extensions.restapi.BadRequestException;
import com.google.gerrit.reviewdb.client.Account;
import com.google.gerrit.reviewdb.client.RefNames;
import com.google.gerrit.server.config.AllUsersName;
import com.google.gerrit.server.git.UserConfigSections;
import com.google.gerrit.server.git.ValidationError;
import com.google.gerrit.server.git.meta.MetaDataUpdate;
import com.google.gerrit.server.git.meta.VersionedMetaData;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)
import java.util.Optional;

@AutoValue
public abstract class Preferences {
  @AutoValue
  public abstract static class General {
    public abstract Optional<Integer> changesPerPage();

    public abstract Optional<String> downloadScheme();

    public abstract Optional<DownloadCommand> downloadCommand();

    public abstract Optional<DateFormat> dateFormat();

    public abstract Optional<TimeFormat> timeFormat();

    public abstract Optional<Boolean> expandInlineDiffs();

    public abstract Optional<Boolean> highlightAssigneeInChangeTable();

    public abstract Optional<Boolean> relativeDateInChangeTable();

    public abstract Optional<DiffView> diffView();

    public abstract Optional<Boolean> sizeBarInChangeTable();

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
    public abstract Optional<Boolean> legacycidInChangeTable();
=======
      storeSection(
          cfg,
          UserConfigSections.GENERAL,
          null,
          mergedGeneralPreferencesInput,
          parseDefaultGeneralPreferences(defaultCfg, null));
      setChangeTable(cfg, mergedGeneralPreferencesInput.changeTable);
      setMy(cfg, mergedGeneralPreferencesInput.my);
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)

    public abstract Optional<Boolean> muteCommonPathPrefixes();

    public abstract Optional<Boolean> signedOffBy();

    public abstract Optional<EmailStrategy> emailStrategy();

    public abstract Optional<EmailFormat> emailFormat();

    public abstract Optional<DefaultBase> defaultBaseForMerges();

    public abstract Optional<Boolean> publishCommentsOnPush();

    public abstract Optional<Boolean> workInProgressByDefault();

    public abstract Optional<ImmutableList<MenuItem>> my();

    public abstract Optional<ImmutableList<String>> changeTable();

    public abstract Optional<ImmutableMap<String, String>> urlAliases();

    @AutoValue.Builder
    public abstract static class Builder {
      abstract Builder changesPerPage(@Nullable Integer val);

      abstract Builder downloadScheme(@Nullable String val);

      abstract Builder downloadCommand(@Nullable DownloadCommand val);

      abstract Builder dateFormat(@Nullable DateFormat val);

      abstract Builder timeFormat(@Nullable TimeFormat val);

      abstract Builder expandInlineDiffs(@Nullable Boolean val);

      abstract Builder highlightAssigneeInChangeTable(@Nullable Boolean val);

      abstract Builder relativeDateInChangeTable(@Nullable Boolean val);

      abstract Builder diffView(@Nullable DiffView val);

      abstract Builder sizeBarInChangeTable(@Nullable Boolean val);

      abstract Builder legacycidInChangeTable(@Nullable Boolean val);

      abstract Builder muteCommonPathPrefixes(@Nullable Boolean val);

      abstract Builder signedOffBy(@Nullable Boolean val);

      abstract Builder emailStrategy(@Nullable EmailStrategy val);

      abstract Builder emailFormat(@Nullable EmailFormat val);

      abstract Builder defaultBaseForMerges(@Nullable DefaultBase val);

      abstract Builder publishCommentsOnPush(@Nullable Boolean val);

      abstract Builder workInProgressByDefault(@Nullable Boolean val);

      abstract Builder my(@Nullable ImmutableList<MenuItem> val);

      abstract Builder changeTable(@Nullable ImmutableList<String> val);

      abstract Builder urlAliases(@Nullable ImmutableMap<String, String> val);

      abstract General build();
    }

    public static General fromInfo(GeneralPreferencesInfo info) {
      return (new AutoValue_Preferences_General.Builder())
          .changesPerPage(info.changesPerPage)
          .downloadScheme(info.downloadScheme)
          .downloadCommand(info.downloadCommand)
          .dateFormat(info.dateFormat)
          .timeFormat(info.timeFormat)
          .expandInlineDiffs(info.expandInlineDiffs)
          .highlightAssigneeInChangeTable(info.highlightAssigneeInChangeTable)
          .relativeDateInChangeTable(info.relativeDateInChangeTable)
          .diffView(info.diffView)
          .sizeBarInChangeTable(info.sizeBarInChangeTable)
          .legacycidInChangeTable(info.legacycidInChangeTable)
          .muteCommonPathPrefixes(info.muteCommonPathPrefixes)
          .signedOffBy(info.signedOffBy)
          .emailStrategy(info.emailStrategy)
          .emailFormat(info.emailFormat)
          .defaultBaseForMerges(info.defaultBaseForMerges)
          .publishCommentsOnPush(info.publishCommentsOnPush)
          .workInProgressByDefault(info.workInProgressByDefault)
          .my(info.my == null ? null : ImmutableList.copyOf(info.my))
          .changeTable(info.changeTable == null ? null : ImmutableList.copyOf(info.changeTable))
          .urlAliases(info.urlAliases == null ? null : ImmutableMap.copyOf(info.urlAliases))
          .build();
    }

    public GeneralPreferencesInfo toInfo() {
      GeneralPreferencesInfo info = new GeneralPreferencesInfo();
      info.changesPerPage = changesPerPage().orElse(null);
      info.downloadScheme = downloadScheme().orElse(null);
      info.downloadCommand = downloadCommand().orElse(null);
      info.dateFormat = dateFormat().orElse(null);
      info.timeFormat = timeFormat().orElse(null);
      info.expandInlineDiffs = expandInlineDiffs().orElse(null);
      info.highlightAssigneeInChangeTable = highlightAssigneeInChangeTable().orElse(null);
      info.relativeDateInChangeTable = relativeDateInChangeTable().orElse(null);
      info.diffView = diffView().orElse(null);
      info.sizeBarInChangeTable = sizeBarInChangeTable().orElse(null);
      info.legacycidInChangeTable = legacycidInChangeTable().orElse(null);
      info.muteCommonPathPrefixes = muteCommonPathPrefixes().orElse(null);
      info.signedOffBy = signedOffBy().orElse(null);
      info.emailStrategy = emailStrategy().orElse(null);
      info.emailFormat = emailFormat().orElse(null);
      info.defaultBaseForMerges = defaultBaseForMerges().orElse(null);
      info.publishCommentsOnPush = publishCommentsOnPush().orElse(null);
      info.workInProgressByDefault = workInProgressByDefault().orElse(null);
      info.my = my().orElse(null);
      info.changeTable = changeTable().orElse(null);
      info.urlAliases = urlAliases().orElse(null);
      return info;
    }
  }

  @AutoValue
  public abstract static class Edit {
    public abstract Optional<Integer> tabSize();

    public abstract Optional<Integer> lineLength();

    public abstract Optional<Integer> indentUnit();

    public abstract Optional<Integer> cursorBlinkRate();

    public abstract Optional<Boolean> hideTopMenu();

    public abstract Optional<Boolean> showTabs();

    public abstract Optional<Boolean> showWhitespaceErrors();

    public abstract Optional<Boolean> syntaxHighlighting();

    public abstract Optional<Boolean> hideLineNumbers();

    public abstract Optional<Boolean> matchBrackets();

    public abstract Optional<Boolean> lineWrapping();

    public abstract Optional<Boolean> indentWithTabs();

    public abstract Optional<Boolean> autoCloseBrackets();

    public abstract Optional<Boolean> showBase();

    public abstract Optional<Theme> theme();

    public abstract Optional<KeyMapType> keyMapType();

    @AutoValue.Builder
    public abstract static class Builder {
      abstract Builder tabSize(@Nullable Integer val);

      abstract Builder lineLength(@Nullable Integer val);

      abstract Builder indentUnit(@Nullable Integer val);

      abstract Builder cursorBlinkRate(@Nullable Integer val);

      abstract Builder hideTopMenu(@Nullable Boolean val);

      abstract Builder showTabs(@Nullable Boolean val);

      abstract Builder showWhitespaceErrors(@Nullable Boolean val);

      abstract Builder syntaxHighlighting(@Nullable Boolean val);

      abstract Builder hideLineNumbers(@Nullable Boolean val);

      abstract Builder matchBrackets(@Nullable Boolean val);

      abstract Builder lineWrapping(@Nullable Boolean val);

      abstract Builder indentWithTabs(@Nullable Boolean val);

      abstract Builder autoCloseBrackets(@Nullable Boolean val);

      abstract Builder showBase(@Nullable Boolean val);

      abstract Builder theme(@Nullable Theme val);

      abstract Builder keyMapType(@Nullable KeyMapType val);

      abstract Edit build();
    }

    public static Edit fromInfo(EditPreferencesInfo info) {
      return (new AutoValue_Preferences_Edit.Builder())
          .tabSize(info.tabSize)
          .lineLength(info.lineLength)
          .indentUnit(info.indentUnit)
          .cursorBlinkRate(info.cursorBlinkRate)
          .hideTopMenu(info.hideTopMenu)
          .showTabs(info.showTabs)
          .showWhitespaceErrors(info.showWhitespaceErrors)
          .syntaxHighlighting(info.syntaxHighlighting)
          .hideLineNumbers(info.hideLineNumbers)
          .matchBrackets(info.matchBrackets)
          .lineWrapping(info.lineWrapping)
          .indentWithTabs(info.indentWithTabs)
          .autoCloseBrackets(info.autoCloseBrackets)
          .showBase(info.showBase)
          .theme(info.theme)
          .keyMapType(info.keyMapType)
          .build();
    }

    public EditPreferencesInfo toInfo() {
      EditPreferencesInfo info = new EditPreferencesInfo();
      info.tabSize = tabSize().orElse(null);
      info.lineLength = lineLength().orElse(null);
      info.indentUnit = indentUnit().orElse(null);
      info.cursorBlinkRate = cursorBlinkRate().orElse(null);
      info.hideTopMenu = hideTopMenu().orElse(null);
      info.showTabs = showTabs().orElse(null);
      info.showWhitespaceErrors = showWhitespaceErrors().orElse(null);
      info.syntaxHighlighting = syntaxHighlighting().orElse(null);
      info.hideLineNumbers = hideLineNumbers().orElse(null);
      info.matchBrackets = matchBrackets().orElse(null);
      info.lineWrapping = lineWrapping().orElse(null);
      info.indentWithTabs = indentWithTabs().orElse(null);
      info.autoCloseBrackets = autoCloseBrackets().orElse(null);
      info.showBase = showBase().orElse(null);
      info.theme = theme().orElse(null);
      info.keyMapType = keyMapType().orElse(null);
      return info;
    }
  }

  @AutoValue
  public abstract static class Diff {
    public abstract Optional<Integer> context();

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
    public abstract Optional<Integer> tabSize();
=======
  private static GeneralPreferencesInfo parseGeneralPreferences(
      Config cfg, @Nullable Config defaultCfg, @Nullable GeneralPreferencesInfo input)
      throws ConfigInvalidException {
    GeneralPreferencesInfo r =
        loadSection(
            cfg,
            UserConfigSections.GENERAL,
            null,
            new GeneralPreferencesInfo(),
            defaultCfg != null
                ? parseDefaultGeneralPreferences(defaultCfg, input)
                : GeneralPreferencesInfo.defaults(),
            input);
    if (input != null) {
      r.changeTable = input.changeTable;
      r.my = input.my;
    } else {
      r.changeTable = parseChangeTableColumns(cfg, defaultCfg);
      r.my = parseMyMenus(cfg, defaultCfg);
    }
    return r;
  }
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)

    public abstract Optional<Integer> fontSize();

    public abstract Optional<Integer> lineLength();

    public abstract Optional<Integer> cursorBlinkRate();

    public abstract Optional<Boolean> expandAllComments();

    public abstract Optional<Boolean> intralineDifference();

    public abstract Optional<Boolean> manualReview();

    public abstract Optional<Boolean> showLineEndings();

    public abstract Optional<Boolean> showTabs();

    public abstract Optional<Boolean> showWhitespaceErrors();

    public abstract Optional<Boolean> syntaxHighlighting();

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
    public abstract Optional<Boolean> hideTopMenu();

    public abstract Optional<Boolean> autoHideDiffTableHeader();
=======
  public static GeneralPreferencesInfo readDefaultGeneralPreferences(
      AllUsersName allUsersName, Repository allUsersRepo)
      throws IOException, ConfigInvalidException {
    return parseGeneralPreferences(readDefaultConfig(allUsersName, allUsersRepo), null, null);
  }
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)

    public abstract Optional<Boolean> hideLineNumbers();

    public abstract Optional<Boolean> renderEntireFile();

    public abstract Optional<Boolean> hideEmptyPane();

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
    public abstract Optional<Boolean> matchBrackets();
=======
  public static GeneralPreferencesInfo updateDefaultGeneralPreferences(
      MetaDataUpdate md, GeneralPreferencesInfo input) throws IOException, ConfigInvalidException {
    VersionedDefaultPreferences defaultPrefs = new VersionedDefaultPreferences();
    defaultPrefs.load(md);
    storeSection(
        defaultPrefs.getConfig(),
        UserConfigSections.GENERAL,
        null,
        input,
        GeneralPreferencesInfo.defaults());
    setMy(defaultPrefs.getConfig(), input.my);
    setChangeTable(defaultPrefs.getConfig(), input.changeTable);
    defaultPrefs.commit(md);
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)

    public abstract Optional<Boolean> lineWrapping();

    public abstract Optional<Theme> theme();

    public abstract Optional<Whitespace> ignoreWhitespace();

    public abstract Optional<Boolean> retainHeader();

    public abstract Optional<Boolean> skipDeleted();

    public abstract Optional<Boolean> skipUnchanged();

    public abstract Optional<Boolean> skipUncommented();

    @AutoValue.Builder
    public abstract static class Builder {
      abstract Builder context(@Nullable Integer val);

      abstract Builder tabSize(@Nullable Integer val);

      abstract Builder fontSize(@Nullable Integer val);

      abstract Builder lineLength(@Nullable Integer val);

      abstract Builder cursorBlinkRate(@Nullable Integer val);

      abstract Builder expandAllComments(@Nullable Boolean val);

      abstract Builder intralineDifference(@Nullable Boolean val);

      abstract Builder manualReview(@Nullable Boolean val);

<<<<<<< HEAD   (5b4f3a Merge "RestApiServlet: Introduce constant for HTTP 429 code")
      abstract Builder showLineEndings(@Nullable Boolean val);

      abstract Builder showTabs(@Nullable Boolean val);

      abstract Builder showWhitespaceErrors(@Nullable Boolean val);

      abstract Builder syntaxHighlighting(@Nullable Boolean val);
=======
  private static void unsetSection(Config cfg, String section) {
    cfg.unsetSection(section, null);
    for (String subsection : cfg.getSubsections(section)) {
      cfg.unsetSection(section, subsection);
    }
  }
>>>>>>> BRANCH (048705 Merge branch 'stable-2.16' into stable-3.0)

      abstract Builder hideTopMenu(@Nullable Boolean val);

      abstract Builder autoHideDiffTableHeader(@Nullable Boolean val);

      abstract Builder hideLineNumbers(@Nullable Boolean val);

      abstract Builder renderEntireFile(@Nullable Boolean val);

      abstract Builder hideEmptyPane(@Nullable Boolean val);

      abstract Builder matchBrackets(@Nullable Boolean val);

      abstract Builder lineWrapping(@Nullable Boolean val);

      abstract Builder theme(@Nullable Theme val);

      abstract Builder ignoreWhitespace(@Nullable Whitespace val);

      abstract Builder retainHeader(@Nullable Boolean val);

      abstract Builder skipDeleted(@Nullable Boolean val);

      abstract Builder skipUnchanged(@Nullable Boolean val);

      abstract Builder skipUncommented(@Nullable Boolean val);

      abstract Diff build();
    }

    public static Diff fromInfo(DiffPreferencesInfo info) {
      return (new AutoValue_Preferences_Diff.Builder())
          .context(info.context)
          .tabSize(info.tabSize)
          .fontSize(info.fontSize)
          .lineLength(info.lineLength)
          .cursorBlinkRate(info.cursorBlinkRate)
          .expandAllComments(info.expandAllComments)
          .intralineDifference(info.intralineDifference)
          .manualReview(info.manualReview)
          .showLineEndings(info.showLineEndings)
          .showTabs(info.showTabs)
          .showWhitespaceErrors(info.showWhitespaceErrors)
          .syntaxHighlighting(info.syntaxHighlighting)
          .hideTopMenu(info.hideTopMenu)
          .autoHideDiffTableHeader(info.autoHideDiffTableHeader)
          .hideLineNumbers(info.hideLineNumbers)
          .renderEntireFile(info.renderEntireFile)
          .hideEmptyPane(info.hideEmptyPane)
          .matchBrackets(info.matchBrackets)
          .lineWrapping(info.lineWrapping)
          .theme(info.theme)
          .ignoreWhitespace(info.ignoreWhitespace)
          .retainHeader(info.retainHeader)
          .skipDeleted(info.skipDeleted)
          .skipUnchanged(info.skipUnchanged)
          .skipUncommented(info.skipUncommented)
          .build();
    }

    public DiffPreferencesInfo toInfo() {
      DiffPreferencesInfo info = new DiffPreferencesInfo();
      info.context = context().orElse(null);
      info.tabSize = tabSize().orElse(null);
      info.fontSize = fontSize().orElse(null);
      info.lineLength = lineLength().orElse(null);
      info.cursorBlinkRate = cursorBlinkRate().orElse(null);
      info.expandAllComments = expandAllComments().orElse(null);
      info.intralineDifference = intralineDifference().orElse(null);
      info.manualReview = manualReview().orElse(null);
      info.showLineEndings = showLineEndings().orElse(null);
      info.showTabs = showTabs().orElse(null);
      info.showWhitespaceErrors = showWhitespaceErrors().orElse(null);
      info.syntaxHighlighting = syntaxHighlighting().orElse(null);
      info.hideTopMenu = hideTopMenu().orElse(null);
      info.autoHideDiffTableHeader = autoHideDiffTableHeader().orElse(null);
      info.hideLineNumbers = hideLineNumbers().orElse(null);
      info.renderEntireFile = renderEntireFile().orElse(null);
      info.hideEmptyPane = hideEmptyPane().orElse(null);
      info.matchBrackets = matchBrackets().orElse(null);
      info.lineWrapping = lineWrapping().orElse(null);
      info.theme = theme().orElse(null);
      info.ignoreWhitespace = ignoreWhitespace().orElse(null);
      info.retainHeader = retainHeader().orElse(null);
      info.skipDeleted = skipDeleted().orElse(null);
      info.skipUnchanged = skipUnchanged().orElse(null);
      info.skipUncommented = skipUncommented().orElse(null);
      return info;
    }
  }
}
