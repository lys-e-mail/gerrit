/**
 * @license
 * Copyright 2023 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */

import {CommentRange} from './rest-api';

export declare interface SuggestionsPluginApi {
  /**
   * Must only be called once. You cannot register twice. You cannot unregister.
   */
  register(provider: SuggestionsProvider): void;
}

export declare interface SuggestCodeRequest {
  changeNumber: number;
  patchsetNumber: number;
  prompt: string;
  range: CommentRange;
  commentedCode: string;
}

export declare interface SuggestionsProvider {
  /**
   * Gerrit calls this method when ...
   * - ... user types a comment draft
   */
  suggestCode(commentData: SuggestCodeRequest): Promise<SuggestCodeResponse>;
}

declare interface SuggestCodeResponse {
  responseCode: ResponseCode;
  suggestions?: Suggestion[];
}

export declare interface Suggestion {
  replacement: string;
  newRange?: CommentRange;
}

export enum ResponseCode {
  OK = 'OK',
  ERROR = 'ERROR',
}
