load("//tools/bzl:junit.bzl", "junit_tests")

junit_tests(
    name = "server_tests",
    srcs = glob(["*.java"]),
    tags = ["no_windows"],
    deps = [
        "//java/com/google/gerrit/common:server",
        "//java/com/google/gerrit/common:version",
        "//java/com/google/gerrit/entities",
        "//java/com/google/gerrit/launcher",
<<<<<<< HEAD   (3c0ef6 Merge branch 'stable-3.0' into stable-3.1)
=======
        "//java/com/google/gerrit/testing:gerrit-test-util",
>>>>>>> BRANCH (ef62b5 Bazel: Remove more unused dependencies flagged by unused_dep)
        "//lib:guava",
        "//lib/auto:auto-value",
        "//lib/auto:auto-value-annotations",
        "//lib/truth",
    ],
)
