/**
 * @license
 * Copyright 2024 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
export interface Autocomletion {
  completionContent: string;
  completionHint: string;
}

/**
 * Caching for autocompleting text, e.g. comments.
 *
 * If the user continues typing text that matches the completion hint, then keep the hint.
 *
 * If the user backspaces, then continue using previous hint.
 */
export class AutocompleteCache {
  private cache: Autocomletion[] = [];

  constructor(private readonly capacity = 5) {}

  get(content: string): string | undefined {
    if (content === '') return undefined;
    for (let i = this.cache.length - 1; i >= 0; i--) {
      const {completionContent, completionHint} = this.cache[i];
      const completionFull = completionContent + completionHint;
      if (completionContent.length > content.length) continue;
      if (!completionFull.startsWith(content)) continue;
      if (completionFull === content) continue;
      return completionFull.substring(content.length);
    }
    return undefined;
  }

  set(content: string, hint: string) {
    const completion = {completionContent: content, completionHint: hint};
    const index = this.cache.findIndex(c => c.completionContent === content);
    if (index !== -1) {
      this.cache.splice(index, 1);
    } else if (this.cache.length >= this.capacity) {
      this.cache.shift();
    }
    this.cache.push(completion);
  }
}
