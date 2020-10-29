load(":rules.bzl", "polygerrit_bundle", "wct_suite")
load("//tools/js:eslint.bzl", "eslint")

package(default_visibility = ["//visibility:public"])

polygerrit_bundle(
    name = "polygerrit_ui",
    srcs = glob(
        [
            "**/*.js",
        ],
        exclude = [
            "node_modules/**",
            "node_modules_licenses/**",
            "test/**",
            "**/*_test.html",
            "**/*_test.js",
        ],
    ),
    outs = ["polygerrit_ui.zip"],
    entry_point = "elements/gr-app.html",
)

filegroup(
    name = "pg_code",
    srcs = glob(
        [
            "**/*.html",
            "**/*.js",
        ],
        exclude = [
            "node_modules/**",
            "node_modules_licenses/**",
        ],
    ),
)

filegroup(
    name = "pg_code_without_test",
    srcs = glob(
        [
            "**/*.html",
            "**/*.js",
        ],
        exclude = [
            "node_modules/**",
            "node_modules_licenses/**",
            "**/*_test.html",
            "test/**",
            "samples/**",
            "**/*_test.js",
        ],
    ),
)

# Workaround for https://github.com/bazelbuild/bazel/issues/1305
filegroup(
    name = "test-srcs-fg",
    srcs = [
        "test/common-test-setup.js",
        "test/index.html",
        ":pg_code",
        "@ui_dev_npm//:node_modules",
        "@ui_npm//:node_modules",
    ],
)

wct_suite(
    name = "wct",
    srcs = [":test-srcs-fg"],
    split_count = 4,
)

# Define the eslinter for polygerrit-ui app
# The eslint macro creates 2 rules: lint_test and lint_bin
eslint(
    name = "lint",
    srcs = [":test-srcs-fg"],
    config = ".eslintrc-bazel.js",
    # The .eslintrc-bazel.js extends the .eslintrc.js config, pass it as a dependency
    data = [".eslintrc.js"],
    extensions = [
        ".html",
        ".js",
    ],
    ignore = ".eslintignore",
    plugins = [
        "@npm//eslint-config-google",
        "@npm//eslint-plugin-html",
        "@npm//eslint-plugin-import",
        "@npm//eslint-plugin-jsdoc",
        "@npm//eslint-plugin-prettier",
    ],
)

# Workaround for https://github.com/bazelbuild/bazel/issues/1305
filegroup(
    name = "polylint-fg",
    srcs = [
        ":pg_code_without_test",
        "@ui_npm//:node_modules",
    ],
)

sh_test(
    name = "polylint_test",
    size = "large",
    srcs = ["polylint_test.sh"],
    args = [
        "$(location @tools_npm//polymer-cli/bin:polymer)",
        "$(location polymer.json)",
    ],
    data = [
<<<<<<< HEAD   (5aac23 Update git submodules)
        "polymer.json",
        ":polylint-fg",
        "@tools_npm//polymer-cli/bin:polymer",
=======
        ":pg_code",
        "//polygerrit-ui:polygerrit_components.bower_components.zip",
    ],
    # Should not run sandboxed.
    tags = [
        "local",
        "manual",
    ],
)

DIRECTORIES = [
    "admin",
    "change",
    "change-list",
    "core",
    "diff",
    "edit",
    "plugins",
    "settings",
    "shared",
    "gr-app",
]

[sh_test(
    name = "template_test_" + directory,
    size = "enormous",
    srcs = ["template_test.sh"],
    args = [directory],
    data = [
        ":pg_code",
        ":template_test_srcs",
        "//polygerrit-ui:polygerrit_components.bower_components.zip",
    ],
    tags = [
        # Should not run sandboxed.
        "local",
        "manual",
        "template",
    ],
) for directory in DIRECTORIES]

# Embed bundle
polygerrit_bundle(
    name = "polygerrit_embed_ui",
    srcs = glob(
        [
            "**/*.html",
            "**/*.js",
        ],
        exclude = [
            "bower_components/**",
            "test/**",
            "**/*_test.html",
        ],
    ),
    outs = ["polygerrit_embed_ui.zip"],
    app = "embed/embed.html",
)

filegroup(
    name = "embed_test_files",
    srcs = glob(
        [
            "embed/**/*_test.html",
        ],
    ),
)

filegroup(
    name = "template_test_srcs",
    srcs = [
        "template_test_srcs/convert_for_template_tests.py",
        "template_test_srcs/template_test.js",
    ],
)

sh_test(
    name = "embed_test",
    size = "small",
    srcs = ["embed_test.sh"],
    data = [
        "embed/test.html",
        "test/common-test-setup.html",
        ":embed_test_files",
        ":pg_code.zip",
        ":test_components.zip",
>>>>>>> BRANCH (1012bf Merge branch 'stable-3.0' into stable-3.1)
    ],
    # Should not run sandboxed.
    tags = [
        "local",
        "manual",
    ],
)
