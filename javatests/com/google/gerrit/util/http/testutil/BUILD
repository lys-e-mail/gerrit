load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "testutil",
    testonly = True,
    srcs = glob(["**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//lib:guava",
<<<<<<< HEAD   (1a3d84 Enable rolling upgrade to next versions)
        "//lib:jgit",
=======
>>>>>>> BRANCH (622ef7 Merge "Bazel: Remove version suffix from servlet-api-3_1 rul)
        "//lib:servlet-api",
        "//lib/httpcomponents:httpclient",
    ],
)
