/**
 * @license
 * Copyright 2022 Google LLC
 * SPDX-License-Identifier: Apache-2.0
 */
import {css, html, LitElement} from 'lit';
import {customElement, property, state} from 'lit/decorators.js';
import {
  htmlEscape,
  sanitizeHtml,
  sanitizeHtmlToFragment,
} from '../../../utils/inner-html-util';
import {unescapeHTML} from '../../../utils/syntax-util';
import '@polymer/marked-element';
import {resolve} from '../../../models/dependency';
import {subscribe} from '../../lit/subscription-controller';
import {configModelToken} from '../../../models/config/config-model';
import {CommentLinks, EmailAddress} from '../../../api/rest-api';
import {linkifyUrlsAndApplyRewrite} from '../../../utils/link-util';
import '../gr-account-chip/gr-account-chip';
import {KnownExperimentId} from '../../../services/flags/flags';
import {getAppContext} from '../../../services/app-context';

/**
 * This element optionally renders markdown and also applies some regex
 * replacements to linkify key parts of the text defined by the host's config.
 */
@customElement('gr-formatted-text')
export class GrFormattedText extends LitElement {
  @property({type: String})
  content = '';

  @property({type: Boolean})
  markdown = false;

  @state()
  private repoCommentLinks: CommentLinks = {};

  private readonly flagsService = getAppContext().flagsService;

  private readonly getConfigModel = resolve(this, configModelToken);

  /**
   * Note: Do not use sharedStyles or other styles here that should not affect
   * the generated HTML of the markdown.
   */
  static override styles = [
    css`
      a {
        color: var(--link-color);
      }
      p,
      ul,
      code,
      blockquote {
        margin: 0 0 var(--spacing-m) 0;
        max-width: var(--gr-formatted-text-prose-max-width, none);
      }
      p:last-child,
      ul:last-child,
      blockquote:last-child,
      pre:last-child {
        margin: 0;
      }
      blockquote {
        border-left: var(--spacing-xxs) solid var(--comment-quote-marker-color);
        padding: 0 var(--spacing-m);
      }
      code {
        background-color: var(--background-color-secondary);
        border: var(--spacing-xxs) solid var(--border-color);
        display: block;
        font-family: var(--monospace-font-family);
        font-size: var(--font-size-code);
        line-height: var(--line-height-mono);
        margin: var(--spacing-m) 0;
        padding: var(--spacing-xxs) var(--spacing-s);
        overflow-x: auto;
        /* Pre will preserve whitespace and line breaks but not wrap */
        white-space: pre;
      }
      /* Non-multiline code elements need display:inline to shrink and not take
         a whole row */
      :not(pre) > code {
        display: inline;
      }
      p {
        /* prose will automatically wrap but inline <code> blocks won't and we
           should overflow in that case rather than wrapping or leaking out */
        overflow-x: auto;
      }
      li {
        margin-left: var(--spacing-xl);
      }
      gr-account-chip {
        display: inline;
      }
      .plaintext {
        font: inherit;
        white-space: var(--linked-text-white-space, pre-wrap);
        word-wrap: var(--linked-text-word-wrap, break-word);
      }
    `,
  ];

  constructor() {
    super();
    subscribe(
      this,
      () => this.getConfigModel().repoCommentLinks$,
      repoCommentLinks => (this.repoCommentLinks = repoCommentLinks)
    );
  }

  override render() {
    if (this.markdown) {
      return this.renderAsMarkdown();
    } else {
      return this.renderAsPlaintext();
    }
  }

  private renderAsPlaintext() {
    const linkedText = linkifyUrlsAndApplyRewrite(
      htmlEscape(this.content).toString(),
      this.repoCommentLinks
    );

    return html`
      <pre class="plaintext">${sanitizeHtmlToFragment(linkedText)}</pre>
    `;
  }

  private renderAsMarkdown() {
    // <marked-element> internals will be in charge of calling our custom
    // renderer so we wrap 'this.rewriteText' so that 'this' is preserved via
    // closure.
    const boundRewriteText = (text: string) =>
      linkifyUrlsAndApplyRewrite(text, this.repoCommentLinks);

    // We are overriding some marked-element renderers for a few reasons:
    // 1. Disable inline images as a design/policy choice.
    // 2. Inline code blocks ("codespan") do not unescape HTML characters when
    //    rendering without <pre> and so we must do this manually.
    //    <marked-element> is already escaping these internally. See test
    //    covering this.
    // 3. Multiline code blocks ("code") is similarly handling escaped
    //    characters using <pre>. The convention is to only use <pre> for multi-
    //    line code blocks so it is not used for inline code blocks. See test
    //    for this.
    // 4. Rewrite plain text ("text") to apply linking and other config-based
    //    rewrites. Text within code blocks is not passed here.
    // 5. Open links in a new tab by rendering with target="_blank" attribute.
    function customRenderer(renderer: {[type: string]: Function}) {
      renderer['link'] = (href: string, title: string, text: string) =>
        /* HTML */
        `<a
          href="${href}"
          target="_blank"
          ${title ? `title="${title}"` : ''}
          rel="noopener"
          >${text}</a
        >`;
      renderer['image'] = (href: string, _title: string, text: string) =>
        `![${text}](${href})`;
      renderer['codespan'] = (text: string) =>
        `<code>${unescapeHTML(text)}</code>`;
      renderer['code'] = (text: string) => `<pre><code>${text}</code></pre>`;
      renderer['text'] = boundRewriteText;
    }

    // The child with slot is optional but allows us control over the styling.
    // The `callback` property lets us do a final sanitization of the output
    // HTML string before it is rendered by `<marked-element>` in case any
    // rewrites have been abused to attempt an XSS attack.
    return html`
      <marked-element
        .markdown=${this.escapeAllButBlockQuotes(this.content)}
        .breaks=${true}
        .renderer=${customRenderer}
        .callback=${(_error: string | null, contents: string) =>
          sanitizeHtml(contents)}
      >
        <div slot="markdown-html"></div>
      </marked-element>
    `;
  }

  private escapeAllButBlockQuotes(text: string) {
    // Escaping the message should be done first to make sure user's literal
    // input does not get rendered without affecting html added in later steps.
    text = htmlEscape(text).toString();
    // Unescape block quotes '>'. This is slightly dangerous as '>' can be used
    // in HTML fragments, but it is insufficient on it's own.
    text = text.replace(/(^|\n)&gt;/g, '$1>');

    return text;
  }

  override updated() {
    // Look for @mentions and replace them with an account-label chip.
    if (this.flagsService.isEnabled(KnownExperimentId.MENTION_USERS)) {
      this.convertEmailsToAccountChips();
    }
  }

  private convertEmailsToAccountChips() {
    for (const emailLink of this.renderRoot.querySelectorAll(
      'a[href^="mailto"]'
    )) {
      const previous = emailLink.previousSibling;
      // This Regexp matches the beginning of the MENTIONS_REGEX at the end of
      // an element.
      if (
        previous?.nodeName === '#text' &&
        previous?.textContent?.match(/(^|\s)@$/)
      ) {
        const accountChip = document.createElement('gr-account-chip');
        accountChip.account = {
          email: emailLink.textContent as EmailAddress,
        };
        accountChip.removable = false;
        // Remove the trailing @ from the previous element.
        previous.textContent = previous.textContent.slice(0, -1);
        emailLink.parentNode?.replaceChild(accountChip, emailLink);
      }
    }
  }
}

declare global {
  interface HTMLElementTagNameMap {
    'gr-formatted-text': GrFormattedText;
  }
}
<<<<<<< HEAD   (e0f47a Lookup existing changes by exact Change-Id string in Receive)
=======
@customElement('gr-formatted-text')
export class GrFormattedText extends LitElement {
  @property({type: String})
  content?: string;

  @property({type: Object})
  config?: CommentLinks;

  @property({type: Boolean, reflect: true})
  noTrailingMargin = false;

  static override get styles() {
    return [
      css`
        :host {
          display: block;
          font-family: var(--font-family);
        }
        a {
          color: var(--link-color);
        }
        p,
        ul,
        code,
        blockquote,
        gr-linked-text.pre {
          margin: 0 0 var(--spacing-m) 0;
        }
        p,
        ul,
        code,
        blockquote {
          max-width: var(--gr-formatted-text-prose-max-width, none);
        }
        :host([noTrailingMargin]) p:last-child,
        :host([noTrailingMargin]) ul:last-child,
        :host([noTrailingMargin]) blockquote:last-child,
        :host([noTrailingMargin]) gr-linked-text.pre:last-child {
          margin: 0;
        }
        blockquote {
          border-left: 1px solid #aaa;
          padding: 0 var(--spacing-m);
        }
        code {
          display: block;
          white-space: pre-wrap;
          background-color: var(--background-color-secondary);
          border: 1px solid var(--border-color);
          border-left-width: var(--spacing-s);
          margin: var(--spacing-m) 0;
          padding: var(--spacing-s) var(--spacing-m);
          overflow-x: scroll;
        }
        li {
          list-style-type: disc;
          margin-left: var(--spacing-xl);
        }
        .inline-code,
        code {
          font-family: var(--monospace-font-family);
          font-size: var(--font-size-code);
          line-height: var(--line-height-mono);
          background-color: var(--background-color-secondary);
          border: 1px solid var(--border-color);
          padding: 1px var(--spacing-s);
        }
      `,
    ];
  }

  override render() {
    if (!this.content) return;
    const blocks = this._computeBlocks(this.content);
    return html`${blocks.map(block => this.renderBlock(block))}`;
  }

  /**
   * Given a source string, parse into an array of block objects. Each block
   * has a `type` property which takes any of the following values.
   * * 'paragraph' (Paragraph of regular text)
   * * 'quote' (Block quote.)
   * * 'pre' (Pre-formatted text.)
   * * 'list' (Unordered list.)
   * * 'code' (code blocks.)
   *
   * For blocks of type 'paragraph' there is a list of spans that is the content
   * for that paragraph.
   *
   * For blocks of type 'pre' and 'code' there is a `text`
   * property that maps to a string of the block's content.
   *
   * For blocks of type 'list', there is an `items` property that maps to a
   * list of strings representing the list items.
   *
   * For blocks of type 'quote', there is a `blocks` property that maps to a
   * list of blocks contained in the quote.
   *
   * NOTE: Strings appearing in all block objects are NOT escaped.
   */
  _computeBlocks(content: string): Block[] {
    const result: Block[] = [];
    const lines = content.replace(/[\s\n\r\t]+$/g, '').split('\n');

    for (let i = 0; i < lines.length; i++) {
      if (!lines[i].length) {
        continue;
      }

      if (this.isCodeMarkLine(lines[i])) {
        const startOfCode = i + 1;
        const endOfCode = this.getEndOfSection(
          lines,
          startOfCode,
          line => !this.isCodeMarkLine(line)
        );
        // If the code extends to the end then there is no closing``` and the
        // opening``` should not be counted as a multiline code block.
        const lineAfterCode = lines[endOfCode];
        if (lineAfterCode && this.isCodeMarkLine(lineAfterCode)) {
          result.push({
            type: 'code',
            // Does not include either of the ``` lines
            text: lines.slice(startOfCode, endOfCode).join('\n'),
          });
          i = endOfCode; // advances past the closing```
          continue;
        }
      }
      if (this.isSingleLineCode(lines[i])) {
        // no guard check as _isSingleLineCode tested on the pattern
        const codeContent = lines[i].match(CODE_MARKER_PATTERN)![2];
        result.push({type: 'code', text: codeContent});
      } else if (this.isList(lines[i])) {
        const endOfList = this.getEndOfSection(lines, i + 1, line =>
          this.isList(line)
        );
        result.push(this.makeList(lines.slice(i, endOfList)));
        i = endOfList - 1;
      } else if (this.isQuote(lines[i])) {
        const endOfQuote = this.getEndOfSection(lines, i + 1, line =>
          this.isQuote(line)
        );
        const blockLines = lines
          .slice(i, endOfQuote)
          .map(l => l.replace(/^[ ]?>[ ]?/, ''));
        result.push({
          type: 'quote',
          blocks: this._computeBlocks(blockLines.join('\n')),
        });
        i = endOfQuote - 1;
      } else if (this.isPreFormat(lines[i])) {
        // include pre or all regular lines but stop at next new line
        const predicate = (line: string) =>
          this.isPreFormat(line) ||
          (this.isRegularLine(line) &&
            !this.isWhitespaceLine(line) &&
            line.length > 0);
        const endOfPre = this.getEndOfSection(lines, i + 1, predicate);
        result.push({
          type: 'pre',
          text: lines.slice(i, endOfPre).join('\n'),
        });
        i = endOfPre - 1;
      } else {
        const endOfRegularLines = this.getEndOfSection(lines, i + 1, line =>
          this.isRegularLine(line)
        );
        result.push({
          type: 'paragraph',
          spans: this.computeInlineItems(
            lines.slice(i, endOfRegularLines).join('\n')
          ),
        });
        i = endOfRegularLines - 1;
      }
    }

    return result;
  }

  private computeInlineItems(content: string): InlineItem[] {
    const result: InlineItem[] = [];
    const textSpans = content.split(INLINE_PATTERN);
    for (let i = 0; i < textSpans.length; ++i) {
      // Because INLINE_PATTERN has a single capturing group, string.split will
      // return strings before and after each match as well as the matched
      // group. These are always interleaved starting with a non-matched string
      // which may be empty.
      if (textSpans[i].length === 0) {
        // No point in processing empty strings.
        continue;
      } else if (i % 2 === 0) {
        // A non-matched string.
        result.push({type: 'text', text: textSpans[i]});
      } else if (textSpans[i].startsWith('`')) {
        result.push({type: 'code', text: textSpans[i].slice(1, -1)});
      } else {
        const m = textSpans[i].match(EXTRACT_LINK_PATTERN);
        if (!m) {
          result.push({type: 'text', text: textSpans[i]});
        } else {
          // eslint-disable-next-line @typescript-eslint/no-unused-vars
          const [_, text, url] = m;
          // Disallow javascript protocol in the href as an XSS mitigation
          if (url.trimStart().startsWith('javascript:')) {
            result.push({type: 'link', text, url: ''});
          } else {
            result.push({type: 'link', text, url});
          }
        }
      }
    }
    return result;
  }

  private getEndOfSection(
    lines: string[],
    startIndex: number,
    sectionPredicate: (line: string) => boolean
  ) {
    const index = lines
      .slice(startIndex)
      .findIndex(line => !sectionPredicate(line));
    return index === -1 ? lines.length : index + startIndex;
  }

  /**
   * Take a block of comment text that contains a list, generate appropriate
   * block objects and append them to the output list.
   *
   * * Item one.
   * * Item two.
   * * item three.
   *
   * TODO(taoalpha): maybe we should also support nested list
   *
   * @param lines The block containing the list.
   */
  private makeList(lines: string[]): Block {
    return {
      type: 'list',
      items: lines.map(line => {
        return {
          spans: this.computeInlineItems(line.substring(1).trim()),
        };
      }),
    };
  }

  private isRegularLine(line: string): boolean {
    return (
      !this.isQuote(line) &&
      !this.isCodeMarkLine(line) &&
      !this.isSingleLineCode(line) &&
      !this.isList(line) &&
      !this.isPreFormat(line)
    );
  }

  private isQuote(line: string): boolean {
    return line.startsWith('> ') || line.startsWith(' > ');
  }

  private isCodeMarkLine(line: string): boolean {
    return line.trim() === '```';
  }

  private isSingleLineCode(line: string): boolean {
    return CODE_MARKER_PATTERN.test(line);
  }

  private isPreFormat(line: string): boolean {
    return /^[ \t]/.test(line) && !this.isWhitespaceLine(line);
  }

  private isList(line: string): boolean {
    return /^[-*] /.test(line);
  }

  private isWhitespaceLine(line: string): boolean {
    return /^\s+$/.test(line);
  }

  private renderInlineText(content: string): TemplateResult {
    return html`
      <gr-linked-text
        .config=${this.config}
        content=${content}
        pre
        inline
      ></gr-linked-text>
    `;
  }

  private renderLink(text: string, url: string): TemplateResult {
    return html`<a href=${url}>${text}</a>`;
  }

  private renderInlineCode(text: string): TemplateResult {
    return html`<span class="inline-code">${text}</span>`;
  }

  private renderInlineItem(span: InlineItem): TemplateResult {
    switch (span.type) {
      case 'text':
        return this.renderInlineText(span.text);
      case 'link':
        return this.renderLink(span.text, span.url);
      case 'code':
        return this.renderInlineCode(span.text);
      default:
        return html``;
    }
  }

  private renderListItem(item: ListItem): TemplateResult {
    return html` <li>
      ${item.spans.map(item => this.renderInlineItem(item))}
    </li>`;
  }

  private renderBlock(block: Block): TemplateResult {
    switch (block.type) {
      case 'paragraph':
        return html` <p>
          ${block.spans.map(item => this.renderInlineItem(item))}
        </p>`;
      case 'quote':
        return html`
          <blockquote>
            ${block.blocks.map(subBlock => this.renderBlock(subBlock))}
          </blockquote>
        `;
      case 'code':
        return html`<code>${block.text}</code>`;
      case 'pre':
        return html`<pre><code>${block.text}</code></pre>`;
      case 'list':
        return html`
          <ul>
            ${block.items.map(item => this.renderListItem(item))}
          </ul>
        `;
    }
  }
}
>>>>>>> BRANCH (28e062 Merge branch 'stable-3.5' into stable-3.6)
