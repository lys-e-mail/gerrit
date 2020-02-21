load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "logging",
    srcs = glob(
        ["**/*.java"],
    ),
    visibility = ["//visibility:public"],
    deps = [
        "//java/com/google/gerrit/common:annotations",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gerrit/server/util/time",
        "//lib:gson",
        "//lib:guava",
        "//lib:jgit",
        "//lib/auto:auto-value",
        "//lib/auto:auto-value-annotations",
        "//lib/flogger:api",
<<<<<<< HEAD   (294522 Update git submodules)
        "//lib/guice",
=======
        "//lib/log:log4j",
>>>>>>> BRANCH (c492db Merge branch 'stable-2.16' into stable-3.0)
    ],
)
