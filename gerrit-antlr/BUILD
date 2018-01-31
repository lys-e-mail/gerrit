load("//tools/bzl:genrule2.bzl", "genrule2")

java_library(
    name = "query_exception",
<<<<<<< HEAD   (ed482e Merge "polygerrit: Add note in README about npm install" int)
    srcs = [
        "src/main/java/com/google/gerrit/server/query/QueryParseException.java",
        "src/main/java/com/google/gerrit/server/query/QueryRequiresAuthException.java",
    ],
=======
    srcs = ["src/main/java/com/google/gerrit/server/query/QueryParseException.java"],
>>>>>>> BRANCH (e48671 Fix build for bazel 0.9.0)
    visibility = ["//visibility:public"],
)

genrule2(
    name = "query_antlr",
    srcs = ["src/main/antlr3/com/google/gerrit/server/query/Query.g"],
<<<<<<< HEAD   (ed482e Merge "polygerrit: Add note in README about npm install" int)
    outs = ["query_antlr.srcjar"],
    cmd = " && ".join([
        "$(location //lib/antlr:antlr-tool) -o $$TMP $<",
        "cd $$TMP",
        "$$ROOT/$(location @bazel_tools//tools/zip:zipper) cC $$ROOT/$@ $$(find *)",
=======
    out = "query_antlr.srcjar",
    cmd = " && ".join([
        "$(location //lib/antlr:antlr-tool) -o $$TMP $<",
        "cd $$TMP",
        "zip $$ROOT/$@ $$(find . -type f )",
>>>>>>> BRANCH (e48671 Fix build for bazel 0.9.0)
    ]),
    tools = [
        "//lib/antlr:antlr-tool",
        "@bazel_tools//tools/zip:zipper",
    ],
)

java_library(
    name = "query_parser",
    srcs = [":query_antlr"],
    visibility = ["//visibility:public"],
    deps = [
        ":query_exception",
        "//lib/antlr:java_runtime",
    ],
)
