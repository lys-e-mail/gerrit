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
<<<<<<< HEAD   (7dfa6a Merge "Merge branch 'stable-3.0' into stable-3.1" into stabl)
        "//lib/guice",
=======
        "//lib/log:log4j",
>>>>>>> BRANCH (7bcca9 Merge "Merge branch 'stable-2.16' into stable-3.0" into stab)
    ],
)
