load("@rules_java//java:defs.bzl", "java_library")

ANNOTATIONS = [
    "Nullable.java",
    "UsedAt.java",
]

java_library(
    name = "annotations",
    srcs = ANNOTATIONS,
    visibility = ["//visibility:public"],
    deps = ["//lib:guava"],
)

java_library(
    name = "server",
    srcs = glob(
        ["**/*.java"],
        exclude = ANNOTATIONS,
    ),
    visibility = ["//visibility:public"],
    deps = [
        ":annotations",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gerrit/prettify:server",
        "//java/com/google/gerrit/reviewdb:server",
<<<<<<< HEAD   (6f57e5 Merge branch 'stable-2.16' into stable-3.0)
        "//java/com/google/gwtorm",
=======
>>>>>>> BRANCH (d16b84 Merge branch 'stable-2.15' into stable-2.16)
        "//lib:guava",
        "//lib:servlet-api-3_1",
        "//lib/auto:auto-value",
        "//lib/auto:auto-value-annotations",
        "//lib/flogger:api",
        "//lib/jgit/org.eclipse.jgit:jgit",
    ],
)

# ":version" should not be in the dependency graph of the acceptance
# tests to avoid spurious test re-runs. That's because the content of
# //:version.txt is changed when the outcome of `git describe` is changed.
java_library(
    name = "version",
    resources = [":Version"],
    visibility = ["//visibility:public"],
)

genrule(
    name = "gen_version",
    srcs = ["//:version.txt"],
    outs = ["Version"],
    cmd = "cat $< > $@",
)
