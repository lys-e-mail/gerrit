load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "oauth",
    srcs = glob(["**/*.java"]),
    resource_strip_prefix = "resources",
    resources = ["//resources/com/google/gerrit/httpd/auth/oauth"],
    visibility = ["//visibility:public"],
    deps = [
        "//java/com/google/gerrit/common:annotations",
        "//java/com/google/gerrit/entities",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gerrit/httpd",
        "//java/com/google/gerrit/server",
        "//lib:gson",
        "//lib:guava",
<<<<<<< HEAD   (1a3d84 Enable rolling upgrade to next versions)
        "//lib:jgit",
=======
>>>>>>> BRANCH (622ef7 Merge "Bazel: Remove version suffix from servlet-api-3_1 rul)
        "//lib:servlet-api",
        "//lib/commons:codec",
        "//lib/flogger:api",
        "//lib/guice",
        "//lib/guice:guice-servlet",
    ],
)
