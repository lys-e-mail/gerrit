/**
 * @license
 * Copyright (C) 2016 The Android Open Source Project
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
import {property} from '@polymer/decorators';
import {PolymerElement} from '@polymer/polymer';
import {check, Constructor} from '../../utils/common-util';
import {appContext} from '../../services/app-context';
import {
  Shortcut,
  ShortcutSection,
  SPECIAL_SHORTCUT,
} from '../../services/shortcuts/shortcuts-config';
import {
  SectionView,
  ShortcutListener,
} from '../../services/shortcuts/shortcuts-service';

<<<<<<< HEAD   (5a82cb Set version to 3.5.0-SNAPSHOT)
export {
  Shortcut,
  ShortcutSection,
  SPECIAL_SHORTCUT,
  ShortcutListener,
  SectionView,
};
=======
/** Enum for all special shortcuts */
export enum SPECIAL_SHORTCUT {
  DOC_ONLY = 'DOC_ONLY',
  GO_KEY = 'GO_KEY',
  V_KEY = 'V_KEY',
}

// The maximum age of a keydown event to be used in a jump navigation. This
// is only for cases when the keyup event is lost.
const GO_KEY_TIMEOUT_MS = 1000;

const V_KEY_TIMEOUT_MS = 1000;

const THROTTLE_INTERVAL_MS = 500;

/**
 * Enum for all shortcut sections, where that shortcut should be applied to.
 */
export enum ShortcutSection {
  ACTIONS = 'Actions',
  DIFFS = 'Diffs',
  EVERYWHERE = 'Global Shortcuts',
  FILE_LIST = 'File list',
  NAVIGATION = 'Navigation',
  REPLY_DIALOG = 'Reply dialog',
}

/**
 * Enum for all possible shortcut names.
 */
export enum Shortcut {
  OPEN_SHORTCUT_HELP_DIALOG = 'OPEN_SHORTCUT_HELP_DIALOG',
  GO_TO_USER_DASHBOARD = 'GO_TO_USER_DASHBOARD',
  GO_TO_OPENED_CHANGES = 'GO_TO_OPENED_CHANGES',
  GO_TO_MERGED_CHANGES = 'GO_TO_MERGED_CHANGES',
  GO_TO_ABANDONED_CHANGES = 'GO_TO_ABANDONED_CHANGES',
  GO_TO_WATCHED_CHANGES = 'GO_TO_WATCHED_CHANGES',

  CURSOR_NEXT_CHANGE = 'CURSOR_NEXT_CHANGE',
  CURSOR_PREV_CHANGE = 'CURSOR_PREV_CHANGE',
  OPEN_CHANGE = 'OPEN_CHANGE',
  NEXT_PAGE = 'NEXT_PAGE',
  PREV_PAGE = 'PREV_PAGE',
  TOGGLE_CHANGE_REVIEWED = 'TOGGLE_CHANGE_REVIEWED',
  TOGGLE_CHANGE_STAR = 'TOGGLE_CHANGE_STAR',
  REFRESH_CHANGE_LIST = 'REFRESH_CHANGE_LIST',

  OPEN_REPLY_DIALOG = 'OPEN_REPLY_DIALOG',
  OPEN_DOWNLOAD_DIALOG = 'OPEN_DOWNLOAD_DIALOG',
  EXPAND_ALL_MESSAGES = 'EXPAND_ALL_MESSAGES',
  COLLAPSE_ALL_MESSAGES = 'COLLAPSE_ALL_MESSAGES',
  UP_TO_DASHBOARD = 'UP_TO_DASHBOARD',
  UP_TO_CHANGE = 'UP_TO_CHANGE',
  TOGGLE_DIFF_MODE = 'TOGGLE_DIFF_MODE',
  REFRESH_CHANGE = 'REFRESH_CHANGE',
  EDIT_TOPIC = 'EDIT_TOPIC',
  DIFF_AGAINST_BASE = 'DIFF_AGAINST_BASE',
  DIFF_AGAINST_LATEST = 'DIFF_AGAINST_LATEST',
  DIFF_BASE_AGAINST_LEFT = 'DIFF_BASE_AGAINST_LEFT',
  DIFF_RIGHT_AGAINST_LATEST = 'DIFF_RIGHT_AGAINST_LATEST',
  DIFF_BASE_AGAINST_LATEST = 'DIFF_BASE_AGAINST_LATEST',

  NEXT_LINE = 'NEXT_LINE',
  PREV_LINE = 'PREV_LINE',
  VISIBLE_LINE = 'VISIBLE_LINE',
  NEXT_CHUNK = 'NEXT_CHUNK',
  PREV_CHUNK = 'PREV_CHUNK',
  TOGGLE_ALL_DIFF_CONTEXT = 'TOGGLE_ALL_DIFF_CONTEXT',
  NEXT_COMMENT_THREAD = 'NEXT_COMMENT_THREAD',
  PREV_COMMENT_THREAD = 'PREV_COMMENT_THREAD',
  EXPAND_ALL_COMMENT_THREADS = 'EXPAND_ALL_COMMENT_THREADS',
  COLLAPSE_ALL_COMMENT_THREADS = 'COLLAPSE_ALL_COMMENT_THREADS',
  LEFT_PANE = 'LEFT_PANE',
  RIGHT_PANE = 'RIGHT_PANE',
  TOGGLE_LEFT_PANE = 'TOGGLE_LEFT_PANE',
  NEW_COMMENT = 'NEW_COMMENT',
  SAVE_COMMENT = 'SAVE_COMMENT',
  OPEN_DIFF_PREFS = 'OPEN_DIFF_PREFS',
  TOGGLE_DIFF_REVIEWED = 'TOGGLE_DIFF_REVIEWED',

  NEXT_FILE = 'NEXT_FILE',
  PREV_FILE = 'PREV_FILE',
  NEXT_FILE_WITH_COMMENTS = 'NEXT_FILE_WITH_COMMENTS',
  PREV_FILE_WITH_COMMENTS = 'PREV_FILE_WITH_COMMENTS',
  NEXT_UNREVIEWED_FILE = 'NEXT_UNREVIEWED_FILE',
  CURSOR_NEXT_FILE = 'CURSOR_NEXT_FILE',
  CURSOR_PREV_FILE = 'CURSOR_PREV_FILE',
  OPEN_FILE = 'OPEN_FILE',
  TOGGLE_FILE_REVIEWED = 'TOGGLE_FILE_REVIEWED',
  TOGGLE_ALL_INLINE_DIFFS = 'TOGGLE_ALL_INLINE_DIFFS',
  TOGGLE_INLINE_DIFF = 'TOGGLE_INLINE_DIFF',
  TOGGLE_HIDE_ALL_COMMENT_THREADS = 'TOGGLE_HIDE_ALL_COMMENT_THREADS',
  OPEN_FILE_LIST = 'OPEN_FILE_LIST',

  OPEN_FIRST_FILE = 'OPEN_FIRST_FILE',
  OPEN_LAST_FILE = 'OPEN_LAST_FILE',

  SEARCH = 'SEARCH',
  SEND_REPLY = 'SEND_REPLY',
  EMOJI_DROPDOWN = 'EMOJI_DROPDOWN',
  TOGGLE_BLAME = 'TOGGLE_BLAME',
}

export type SectionView = Array<{binding: string[][]; text: string}>;

/**
 * The interface for listener for shortcut events.
 */
export type ShortcutListener = (
  viewMap?: Map<ShortcutSection, SectionView>
) => void;

interface ShortcutEnabledElement extends PolymerElement {
  // TODO: should replace with Map so we can have proper type here
  keyboardShortcuts(): {[shortcut: string]: string};
}

interface ShortcutHelpItem {
  shortcut: Shortcut;
  text: string;
}

// TODO(TS): rename to something more meaningful
const _help = new Map<ShortcutSection, ShortcutHelpItem[]>();

function _describe(shortcut: Shortcut, section: ShortcutSection, text: string) {
  if (!_help.has(section)) {
    _help.set(section, []);
  }
  const shortcuts = _help.get(section);
  if (shortcuts) {
    shortcuts.push({shortcut, text});
  }
}

_describe(Shortcut.SEARCH, ShortcutSection.EVERYWHERE, 'Search');
_describe(
  Shortcut.OPEN_SHORTCUT_HELP_DIALOG,
  ShortcutSection.EVERYWHERE,
  'Show this dialog'
);
_describe(
  Shortcut.GO_TO_USER_DASHBOARD,
  ShortcutSection.EVERYWHERE,
  'Go to User Dashboard'
);
_describe(
  Shortcut.GO_TO_OPENED_CHANGES,
  ShortcutSection.EVERYWHERE,
  'Go to Opened Changes'
);
_describe(
  Shortcut.GO_TO_MERGED_CHANGES,
  ShortcutSection.EVERYWHERE,
  'Go to Merged Changes'
);
_describe(
  Shortcut.GO_TO_ABANDONED_CHANGES,
  ShortcutSection.EVERYWHERE,
  'Go to Abandoned Changes'
);
_describe(
  Shortcut.GO_TO_WATCHED_CHANGES,
  ShortcutSection.EVERYWHERE,
  'Go to Watched Changes'
);

_describe(
  Shortcut.CURSOR_NEXT_CHANGE,
  ShortcutSection.ACTIONS,
  'Select next change'
);
_describe(
  Shortcut.CURSOR_PREV_CHANGE,
  ShortcutSection.ACTIONS,
  'Select previous change'
);
_describe(
  Shortcut.OPEN_CHANGE,
  ShortcutSection.ACTIONS,
  'Show selected change'
);
_describe(Shortcut.NEXT_PAGE, ShortcutSection.ACTIONS, 'Go to next page');
_describe(Shortcut.PREV_PAGE, ShortcutSection.ACTIONS, 'Go to previous page');
_describe(
  Shortcut.OPEN_REPLY_DIALOG,
  ShortcutSection.ACTIONS,
  'Open reply dialog to publish comments and add reviewers'
);
_describe(
  Shortcut.OPEN_DOWNLOAD_DIALOG,
  ShortcutSection.ACTIONS,
  'Open download overlay'
);
_describe(
  Shortcut.EXPAND_ALL_MESSAGES,
  ShortcutSection.ACTIONS,
  'Expand all messages'
);
_describe(
  Shortcut.COLLAPSE_ALL_MESSAGES,
  ShortcutSection.ACTIONS,
  'Collapse all messages'
);
_describe(
  Shortcut.REFRESH_CHANGE,
  ShortcutSection.ACTIONS,
  'Reload the change at the latest patch'
);
_describe(
  Shortcut.TOGGLE_CHANGE_REVIEWED,
  ShortcutSection.ACTIONS,
  'Mark/unmark change as reviewed'
);
_describe(
  Shortcut.TOGGLE_FILE_REVIEWED,
  ShortcutSection.ACTIONS,
  'Toggle review flag on selected file'
);
_describe(
  Shortcut.REFRESH_CHANGE_LIST,
  ShortcutSection.ACTIONS,
  'Refresh list of changes'
);
_describe(
  Shortcut.TOGGLE_CHANGE_STAR,
  ShortcutSection.ACTIONS,
  'Star/unstar change'
);
_describe(Shortcut.EDIT_TOPIC, ShortcutSection.ACTIONS, 'Add a change topic');
_describe(
  Shortcut.DIFF_AGAINST_BASE,
  ShortcutSection.ACTIONS,
  'Diff against base'
);
_describe(
  Shortcut.DIFF_AGAINST_LATEST,
  ShortcutSection.ACTIONS,
  'Diff against latest patchset'
);
_describe(
  Shortcut.DIFF_BASE_AGAINST_LEFT,
  ShortcutSection.ACTIONS,
  'Diff base against left'
);
_describe(
  Shortcut.DIFF_RIGHT_AGAINST_LATEST,
  ShortcutSection.ACTIONS,
  'Diff right against latest'
);
_describe(
  Shortcut.DIFF_BASE_AGAINST_LATEST,
  ShortcutSection.ACTIONS,
  'Diff base against latest'
);

_describe(Shortcut.NEXT_LINE, ShortcutSection.DIFFS, 'Go to next line');
_describe(Shortcut.PREV_LINE, ShortcutSection.DIFFS, 'Go to previous line');
_describe(
  Shortcut.DIFF_AGAINST_BASE,
  ShortcutSection.DIFFS,
  'Diff against base'
);
_describe(
  Shortcut.DIFF_AGAINST_LATEST,
  ShortcutSection.DIFFS,
  'Diff against latest patchset'
);
_describe(
  Shortcut.DIFF_BASE_AGAINST_LEFT,
  ShortcutSection.DIFFS,
  'Diff base against left'
);
_describe(
  Shortcut.DIFF_RIGHT_AGAINST_LATEST,
  ShortcutSection.DIFFS,
  'Diff right against latest'
);
_describe(
  Shortcut.DIFF_BASE_AGAINST_LATEST,
  ShortcutSection.DIFFS,
  'Diff base against latest'
);
_describe(
  Shortcut.VISIBLE_LINE,
  ShortcutSection.DIFFS,
  'Move cursor to currently visible code'
);
_describe(Shortcut.NEXT_CHUNK, ShortcutSection.DIFFS, 'Go to next diff chunk');
_describe(
  Shortcut.PREV_CHUNK,
  ShortcutSection.DIFFS,
  'Go to previous diff chunk'
);
_describe(
  Shortcut.TOGGLE_ALL_DIFF_CONTEXT,
  ShortcutSection.DIFFS,
  'Toggle all diff context'
);
_describe(
  Shortcut.NEXT_COMMENT_THREAD,
  ShortcutSection.DIFFS,
  'Go to next comment thread'
);
_describe(
  Shortcut.PREV_COMMENT_THREAD,
  ShortcutSection.DIFFS,
  'Go to previous comment thread'
);
_describe(
  Shortcut.EXPAND_ALL_COMMENT_THREADS,
  ShortcutSection.DIFFS,
  'Expand all comment threads'
);
_describe(
  Shortcut.COLLAPSE_ALL_COMMENT_THREADS,
  ShortcutSection.DIFFS,
  'Collapse all comment threads'
);
_describe(
  Shortcut.TOGGLE_HIDE_ALL_COMMENT_THREADS,
  ShortcutSection.DIFFS,
  'Hide/Display all comment threads'
);
_describe(Shortcut.LEFT_PANE, ShortcutSection.DIFFS, 'Select left pane');
_describe(Shortcut.RIGHT_PANE, ShortcutSection.DIFFS, 'Select right pane');
_describe(
  Shortcut.TOGGLE_LEFT_PANE,
  ShortcutSection.DIFFS,
  'Hide/show left diff'
);
_describe(Shortcut.NEW_COMMENT, ShortcutSection.DIFFS, 'Draft new comment');
_describe(Shortcut.SAVE_COMMENT, ShortcutSection.DIFFS, 'Save comment');
_describe(
  Shortcut.OPEN_DIFF_PREFS,
  ShortcutSection.DIFFS,
  'Show diff preferences'
);
_describe(
  Shortcut.TOGGLE_DIFF_REVIEWED,
  ShortcutSection.DIFFS,
  'Mark/unmark file as reviewed'
);
_describe(
  Shortcut.TOGGLE_DIFF_MODE,
  ShortcutSection.DIFFS,
  'Toggle unified/side-by-side diff'
);
_describe(
  Shortcut.NEXT_UNREVIEWED_FILE,
  ShortcutSection.DIFFS,
  'Mark file as reviewed and go to next unreviewed file'
);
_describe(Shortcut.TOGGLE_BLAME, ShortcutSection.DIFFS, 'Toggle blame');

_describe(Shortcut.NEXT_FILE, ShortcutSection.NAVIGATION, 'Go to next file');
_describe(
  Shortcut.PREV_FILE,
  ShortcutSection.NAVIGATION,
  'Go to previous file'
);
_describe(
  Shortcut.NEXT_FILE_WITH_COMMENTS,
  ShortcutSection.NAVIGATION,
  'Go to next file that has comments'
);
_describe(
  Shortcut.PREV_FILE_WITH_COMMENTS,
  ShortcutSection.NAVIGATION,
  'Go to previous file that has comments'
);
_describe(
  Shortcut.OPEN_FIRST_FILE,
  ShortcutSection.NAVIGATION,
  'Go to first file'
);
_describe(
  Shortcut.OPEN_LAST_FILE,
  ShortcutSection.NAVIGATION,
  'Go to last file'
);
_describe(
  Shortcut.UP_TO_DASHBOARD,
  ShortcutSection.NAVIGATION,
  'Up to dashboard'
);
_describe(Shortcut.UP_TO_CHANGE, ShortcutSection.NAVIGATION, 'Up to change');

_describe(
  Shortcut.CURSOR_NEXT_FILE,
  ShortcutSection.FILE_LIST,
  'Select next file'
);
_describe(
  Shortcut.CURSOR_PREV_FILE,
  ShortcutSection.FILE_LIST,
  'Select previous file'
);
_describe(Shortcut.OPEN_FILE, ShortcutSection.FILE_LIST, 'Go to selected file');
_describe(
  Shortcut.TOGGLE_ALL_INLINE_DIFFS,
  ShortcutSection.FILE_LIST,
  'Show/hide all inline diffs'
);
_describe(
  Shortcut.TOGGLE_HIDE_ALL_COMMENT_THREADS,
  ShortcutSection.FILE_LIST,
  'Hide/Display all comment threads'
);
_describe(
  Shortcut.TOGGLE_INLINE_DIFF,
  ShortcutSection.FILE_LIST,
  'Show/hide selected inline diff'
);

_describe(Shortcut.SEND_REPLY, ShortcutSection.REPLY_DIALOG, 'Send reply');
_describe(
  Shortcut.EMOJI_DROPDOWN,
  ShortcutSection.REPLY_DIALOG,
  'Emoji dropdown'
);

// Must be declared outside behavior implementation to be accessed inside
// behavior functions.

function getKeyboardEvent(e: CustomKeyboardEvent): CustomKeyboardEvent {
  const event = dom(e.detail ? e.detail.keyboardEvent : e);
  // TODO(TS): worth checking if this still holds or not, if no, remove this.
  // When e is a keyboardEvent, e.event is not null.
  if ('event' in event && (event as CustomKeyboardEvent).event) {
    return (event as CustomKeyboardEvent).event;
  }
  return event as CustomKeyboardEvent;
}

/**
 * Shortcut manager, holds all hosts, bindings and listeners.
 */
export class ShortcutManager {
  private readonly activeHosts = new Map<PolymerElement, Map<string, string>>();

  private readonly bindings = new Map<Shortcut, string[]>();

  public _testOnly_getBindings() {
    return this.bindings;
  }

  public _testOnly_isEmpty() {
    return this.activeHosts.size === 0 && this.listeners.size === 0;
  }

  private readonly listeners = new Set<ShortcutListener>();

  bindShortcut(shortcut: Shortcut, ...bindings: string[]) {
    this.bindings.set(shortcut, bindings);
  }

  getBindingsForShortcut(shortcut: Shortcut) {
    return this.bindings.get(shortcut);
  }

  attachHost(host: PolymerElement | ShortcutEnabledElement) {
    if (!('keyboardShortcuts' in host)) {
      return;
    }
    const shortcuts = host.keyboardShortcuts();
    this.activeHosts.set(host, new Map(Object.entries(shortcuts)));
    this.notifyListeners();
    return shortcuts;
  }

  detachHost(host: PolymerElement) {
    if (this.activeHosts.delete(host)) {
      this.notifyListeners();
      return true;
    }
    return false;
  }

  addListener(listener: ShortcutListener) {
    this.listeners.add(listener);
    listener(this.directoryView());
  }

  removeListener(listener: ShortcutListener) {
    return this.listeners.delete(listener);
  }

  getDescription(section: ShortcutSection, shortcutName: Shortcut) {
    const bindings = _help.get(section);
    let desc = '';
    if (bindings) {
      const binding = bindings.find(
        binding => binding.shortcut === shortcutName
      );
      desc = binding ? binding.text : '';
    }
    return desc;
  }

  getShortcut(shortcutName: Shortcut) {
    const bindings = this.bindings.get(shortcutName);
    return bindings
      ? bindings
          .map(binding => this.describeBinding(binding).join('+'))
          .join(',')
      : '';
  }

  activeShortcutsBySection() {
    const activeShortcuts = new Set<string>();
    this.activeHosts.forEach(shortcuts => {
      shortcuts.forEach((_, shortcut) => activeShortcuts.add(shortcut));
    });

    const activeShortcutsBySection = new Map<
      ShortcutSection,
      ShortcutHelpItem[]
    >();
    _help.forEach((shortcutList, section) => {
      shortcutList.forEach(shortcutHelp => {
        if (activeShortcuts.has(shortcutHelp.shortcut)) {
          if (!activeShortcutsBySection.has(section)) {
            activeShortcutsBySection.set(section, []);
          }
          // From previous condition, the `get(section)`
          // should always return a valid result
          activeShortcutsBySection.get(section)!.push(shortcutHelp);
        }
      });
    });
    return activeShortcutsBySection;
  }

  directoryView() {
    const view = new Map<ShortcutSection, SectionView>();
    this.activeShortcutsBySection().forEach((shortcutHelps, section) => {
      const sectionView: Array<{binding: string[][]; text: string}> = [];
      shortcutHelps.forEach(shortcutHelp => {
        const bindingDesc = this.describeBindings(shortcutHelp.shortcut);
        if (!bindingDesc) {
          return;
        }
        this.distributeBindingDesc(bindingDesc).forEach(bindingDesc => {
          sectionView.push({
            binding: bindingDesc,
            text: shortcutHelp.text,
          });
        });
      });
      view.set(section, sectionView);
    });
    return view;
  }

  distributeBindingDesc(bindingDesc: string[][]): string[][][] {
    if (
      bindingDesc.length === 1 ||
      this.comboSetDisplayWidth(bindingDesc) < 21
    ) {
      return [bindingDesc];
    }
    // Find the largest prefix of bindings that is under the
    // size threshold.
    const head = [bindingDesc[0]];
    for (let i = 1; i < bindingDesc.length; i++) {
      head.push(bindingDesc[i]);
      if (this.comboSetDisplayWidth(head) >= 21) {
        head.pop();
        return [head].concat(this.distributeBindingDesc(bindingDesc.slice(i)));
      }
    }
    return [];
  }

  comboSetDisplayWidth(bindingDesc: string[][]) {
    const bindingSizer = (binding: string[]) =>
      binding.reduce((acc, key) => acc + key.length, 0);
    // Width is the sum of strings + (n-1) * 2 to account for the word
    // "or" joining them.
    return (
      bindingDesc.reduce((acc, binding) => acc + bindingSizer(binding), 0) +
      2 * (bindingDesc.length - 1)
    );
  }

  describeBindings(shortcut: Shortcut): string[][] | null {
    const bindings = this.bindings.get(shortcut);
    if (!bindings) {
      return null;
    }
    // TODO(TS): should check base on length to differentiate two
    // cases
    if (bindings[0] === SPECIAL_SHORTCUT.GO_KEY) {
      return bindings
        .slice(1)
        .map(binding => this._describeKey(binding))
        .map(binding => ['g'].concat(binding));
    }
    if (bindings[0] === SPECIAL_SHORTCUT.V_KEY) {
      return bindings
        .slice(1)
        .map(binding => this._describeKey(binding))
        .map(binding => ['v'].concat(binding));
    }

    return bindings
      .filter(binding => binding !== SPECIAL_SHORTCUT.DOC_ONLY)
      .map(binding => this.describeBinding(binding));
  }

  _describeKey(key: string) {
    switch (key) {
      case 'shift':
        return 'Shift';
      case 'meta':
        return 'Meta';
      case 'ctrl':
        return 'Ctrl';
      case 'enter':
        return 'Enter';
      case 'up':
        return '\u2191'; // ↑
      case 'down':
        return '\u2193'; // ↓
      case 'left':
        return '\u2190'; // ←
      case 'right':
        return '\u2192'; // →
      default:
        return key;
    }
  }

  describeBinding(binding: string) {
    // single key bindings
    if (binding.length === 1) {
      return [binding];
    }
    return binding
      .split(':')[0]
      .split('+')
      .map(part => this._describeKey(part));
  }

  notifyListeners() {
    const view = this.directoryView();
    this.listeners.forEach(listener => listener(view));
  }
}

const shortcutManager = new ShortcutManager();

/**
 * Enum for supported modifiers.
 */
export enum Modifier {
  SHIFT_KEY = 'shiftKey',
  CTRL_KEY = 'ctrlKey',
  META_KEY = 'metaKey',
  // Add when you need it
}

interface IronA11yKeysMixinConstructor {
  // Note: this is needed to have same interface as other mixins
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  new (...args: any[]): IronA11yKeysBehavior;
}
/**
 * @polymer
 * @mixinFunction
 */
const InternalKeyboardShortcutMixin = dedupingMixin(
  <T extends Constructor<PolymerElement> & IronA11yKeysMixinConstructor>(
    superClass: T
  ): T & Constructor<KeyboardShortcutMixinInterface> => {
    /**
     * @polymer
     * @mixinClass
     */
    class Mixin extends superClass {
      @property({type: Number})
      _shortcut_go_key_last_pressed: number | null = null;

      @property({type: Number})
      _shortcut_v_key_last_pressed: number | null = null;

      @property({type: Object})
      _shortcut_go_table: Map<string, string> = new Map<string, string>();

      @property({type: Object})
      _shortcut_v_table: Map<string, string> = new Map<string, string>();

      Shortcut = Shortcut;

      ShortcutSection = ShortcutSection;

      modifierPressed(event: CustomKeyboardEvent) {
        /* We are checking for g/v as modifiers pressed. There are cases such as
         * pressing v and then /, where we want the handler for / to be triggered.
         * TODO(dhruvsri): find a way to support that keyboard combination
         */
        const e = getKeyboardEvent(event);
        return (
          e.altKey ||
          e.ctrlKey ||
          e.metaKey ||
          e.shiftKey ||
          !!this._inGoKeyMode() ||
          !!this.inVKeyMode()
        );
      }

      isModifierPressed(e: CustomKeyboardEvent, modifier: Modifier) {
        return getKeyboardEvent(e)[modifier];
      }

      shouldSuppressKeyboardShortcut(event: CustomKeyboardEvent) {
        const e = getKeyboardEvent(event);
        // TODO(TS): maybe override the EventApi, narrow it down to Element always
        const target = (dom(e) as EventApi).rootTarget as Element;
        const tagName = target.tagName;
        const type = target.getAttribute('type');
        if (
          // Suppress shortcuts on <input> and <textarea>, but not on
          // checkboxes, because we want to enable workflows like 'click
          // mark-reviewed and then press ] to go to the next file'.
          (tagName === 'INPUT' && type !== 'checkbox') ||
          tagName === 'TEXTAREA' ||
          // Suppress shortcuts if the key is 'enter'
          // and target is an anchor or button.
          (e.keyCode === 13 && (tagName === 'A' || tagName === 'BUTTON'))
        ) {
          return true;
        }
        const path = e.composedPath();
        for (let i = 0; path && i < path.length; i++) {
          // TODO(TS): narrow this down to Element from EventTarget first
          if ((path[i] as Element).tagName === 'GR-OVERLAY') {
            return true;
          }
        }
        const detail: ShortcutTriggeredEventDetail = {
          event: e,
          goKey: this._inGoKeyMode(),
          vKey: this.inVKeyMode(),
        };
        this.dispatchEvent(
          new CustomEvent('shortcut-triggered', {
            detail,
            composed: true,
            bubbles: true,
          })
        );
        return false;
      }

      // Alias for getKeyboardEvent.
      getKeyboardEvent(e: CustomKeyboardEvent) {
        return getKeyboardEvent(e);
      }

      // TODO(TS): maybe remove, no reference in the code base
      getRootTarget(e: CustomKeyboardEvent) {
        // TODO(TS): worth checking if we can limit this to EventApi only
        // dom currently returns DomNativeApi|EventApi
        return (dom(getKeyboardEvent(e)) as EventApi).rootTarget;
      }

      bindShortcut(shortcut: Shortcut, ...bindings: string[]) {
        shortcutManager.bindShortcut(shortcut, ...bindings);
      }

      createTitle(shortcutName: Shortcut, section: ShortcutSection) {
        const desc = shortcutManager.getDescription(section, shortcutName);
        const shortcut = shortcutManager.getShortcut(shortcutName);
        return desc && shortcut ? `${desc} (shortcut: ${shortcut})` : '';
      }

      _throttleWrap(fn: (e: Event) => void) {
        let lastCall: number | undefined;
        return (e: Event) => {
          if (
            lastCall !== undefined &&
            Date.now() - lastCall < THROTTLE_INTERVAL_MS
          ) {
            return;
          }
          lastCall = Date.now();
          fn(e);
        };
      }

      _addOwnKeyBindings(shortcut: Shortcut, handler: string) {
        const bindings = shortcutManager.getBindingsForShortcut(shortcut);
        if (!bindings) {
          return;
        }
        if (bindings[0] === SPECIAL_SHORTCUT.DOC_ONLY) {
          return;
        }
        if (bindings[0] === SPECIAL_SHORTCUT.GO_KEY) {
          bindings
            .slice(1)
            .forEach(binding => this._shortcut_go_table.set(binding, handler));
        } else if (bindings[0] === SPECIAL_SHORTCUT.V_KEY) {
          // for each binding added with the go/v key, we set the handler to be
          // handleVKeyAction. handleVKeyAction then looks up in th
          // shortcut_table to see what the relevant handler should be
          bindings
            .slice(1)
            .forEach(binding => this._shortcut_v_table.set(binding, handler));
        } else {
          this.addOwnKeyBinding(bindings.join(' '), handler);
        }
      }

      /** @override */
      connectedCallback() {
        super.connectedCallback();
        const shortcuts = shortcutManager.attachHost(this);
        if (!shortcuts) {
          return;
        }

        for (const key of Object.keys(shortcuts)) {
          // TODO(TS): not needed if convert shortcuts to Map
          this._addOwnKeyBindings(key as Shortcut, shortcuts[key]);
        }

        // each component that uses this behaviour must be aware if go key is
        // pressed or not, since it needs to check it as a modifier
        this.addOwnKeyBinding('g:keydown', '_handleGoKeyDown');
        this.addOwnKeyBinding('g:keyup', '_handleGoKeyUp');

        // If any of the shortcuts utilized GO_KEY, then they are handled
        // directly by this behavior.
        if (this._shortcut_go_table.size > 0) {
          this._shortcut_go_table.forEach((_, key) => {
            this.addOwnKeyBinding(key, '_handleGoAction');
          });
        }

        this.addOwnKeyBinding('v:keydown', '_handleVKeyDown');
        this.addOwnKeyBinding('v:keyup', '_handleVKeyUp');
        if (this._shortcut_v_table.size > 0) {
          this._shortcut_v_table.forEach((_, key) => {
            this.addOwnKeyBinding(key, '_handleVAction');
          });
        }
      }

      /** @override */
      disconnectedCallback() {
        if (shortcutManager.detachHost(this)) {
          this.removeOwnKeyBindings();
        }
        super.disconnectedCallback();
      }

      keyboardShortcuts() {
        return {};
      }

      addKeyboardShortcutDirectoryListener(listener: ShortcutListener) {
        shortcutManager.addListener(listener);
      }

      removeKeyboardShortcutDirectoryListener(listener: ShortcutListener) {
        shortcutManager.removeListener(listener);
      }

      _handleVKeyDown(e: CustomKeyboardEvent) {
        if (this.shouldSuppressKeyboardShortcut(e)) return;
        this._shortcut_v_key_last_pressed = Date.now();
      }

      _handleVKeyUp() {
        setTimeout(() => {
          this._shortcut_v_key_last_pressed = null;
        }, V_KEY_TIMEOUT_MS);
      }

      private inVKeyMode() {
        return !!(
          this._shortcut_v_key_last_pressed &&
          Date.now() - this._shortcut_v_key_last_pressed <= V_KEY_TIMEOUT_MS
        );
      }

      _handleVAction(e: CustomKeyboardEvent) {
        if (
          !this.inVKeyMode() ||
          !this._shortcut_v_table.has(e.detail.key) ||
          this.shouldSuppressKeyboardShortcut(e)
        ) {
          return;
        }
        e.preventDefault();
        const handler = this._shortcut_v_table.get(e.detail.key);
        if (handler) {
          // TODO(TS): should fix this
          // eslint-disable-next-line @typescript-eslint/no-explicit-any
          (this as any)[handler](e);
        }
      }

      _handleGoKeyDown(e: CustomKeyboardEvent) {
        if (this.shouldSuppressKeyboardShortcut(e)) return;
        this._shortcut_go_key_last_pressed = Date.now();
      }

      _handleGoKeyUp() {
        // Set go_key_last_pressed to null `GO_KEY_TIMEOUT_MS` after keyup event
        // so that users can trigger `g + i` by pressing g and i quickly.
        setTimeout(() => {
          this._shortcut_go_key_last_pressed = null;
        }, GO_KEY_TIMEOUT_MS);
      }

      _inGoKeyMode() {
        return !!(
          this._shortcut_go_key_last_pressed &&
          Date.now() - this._shortcut_go_key_last_pressed <= GO_KEY_TIMEOUT_MS
        );
      }

      _handleGoAction(e: CustomKeyboardEvent) {
        if (
          !this._inGoKeyMode() ||
          !this._shortcut_go_table.has(e.detail.key) ||
          this.shouldSuppressKeyboardShortcut(e)
        ) {
          return;
        }
        e.preventDefault();
        const handler = this._shortcut_go_table.get(e.detail.key);
        if (handler) {
          // TODO(TS): should fix this
          // eslint-disable-next-line @typescript-eslint/no-explicit-any
          (this as any)[handler](e);
        }
      }
    }

    return Mixin;
  }
);
>>>>>>> BRANCH (76110d Merge branch 'stable-3.3' into stable-3.4)

export const KeyboardShortcutMixin = <T extends Constructor<PolymerElement>>(
  superClass: T
) => {
  /**
   * @polymer
   * @mixinClass
   */
  class Mixin extends superClass {
    // This enables `Shortcut` to be used in the html template.
    Shortcut = Shortcut;

    // This enables `ShortcutSection` to be used in the html template.
    ShortcutSection = ShortcutSection;

    private readonly shortcuts = appContext.shortcutsService;

    /** Used to disable shortcuts when the element is not visible. */
    private observer?: IntersectionObserver;

    /**
     * Enabling shortcuts only when the element is visible (see `observer`
     * above) is a great feature, but often what you want is for the *page* to
     * be visible, not the specific child element that registers keyboard
     * shortcuts. An example is the FileList in the ChangeView. So we allow
     * a broader observer target to be specified here, and fall back to
     * `this` as the default.
     */
    @property({type: Object})
    observerTarget: Element = this;

    /** Are shortcuts currently enabled? True only when element is visible. */
    private bindingsEnabled = false;

    override connectedCallback() {
      super.connectedCallback();
      this.createVisibilityObserver();
      this.enableBindings();
    }

    override disconnectedCallback() {
      this.destroyVisibilityObserver();
      this.disableBindings();
      super.disconnectedCallback();
    }

    /**
     * Creates an intersection observer that enables bindings when the
     * element is visible and disables them when the element is hidden.
     */
    private createVisibilityObserver() {
      if (!this.hasKeyboardShortcuts()) return;
      if (this.observer) return;
      this.observer = new IntersectionObserver(entries => {
        check(entries.length === 1, 'Expected one observer entry.');
        const isVisible = entries[0].isIntersecting;
        if (isVisible) {
          this.enableBindings();
        } else {
          this.disableBindings();
        }
      });
      this.observer.observe(this.observerTarget);
    }

    private destroyVisibilityObserver() {
      if (this.observer) this.observer.unobserve(this.observerTarget);
    }

    /**
     * Enables all the shortcuts returned by keyboardShortcuts().
     * This is a private method being called when the element becomes
     * connected or visible.
     */
    private enableBindings() {
      if (!this.hasKeyboardShortcuts()) return;
      if (this.bindingsEnabled) return;
      this.bindingsEnabled = true;

      this.shortcuts.attachHost(this, this.keyboardShortcuts());
    }

    /**
     * Disables all the shortcuts returned by keyboardShortcuts().
     * This is a private method being called when the element becomes
     * disconnected or invisible.
     */
    private disableBindings() {
      if (!this.bindingsEnabled) return;
      this.bindingsEnabled = false;
      this.shortcuts.detachHost(this);
    }

    private hasKeyboardShortcuts() {
      return this.keyboardShortcuts().length > 0;
    }

    keyboardShortcuts(): ShortcutListener[] {
      return [];
    }
  }

  return Mixin as T & Constructor<KeyboardShortcutMixinInterface>;
};

/** The interface corresponding to KeyboardShortcutMixin */
export interface KeyboardShortcutMixinInterface {
  keyboardShortcuts(): ShortcutListener[];
}
