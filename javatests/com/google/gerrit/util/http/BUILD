load("//tools/bzl:junit.bzl", "junit_tests")

junit_tests(
    name = "http_tests",
    srcs = glob(["**/*.java"]),
    deps = [
        "//java/com/google/gerrit/util/http",
        "//javatests/com/google/gerrit/util/http/testutil",
        "//lib:junit",
        "//lib:servlet-api-without-neverlink",
<<<<<<< HEAD   (1a3d84 Enable rolling upgrade to next versions)
=======
        "//lib/easymock",
>>>>>>> BRANCH (622ef7 Merge "Bazel: Remove version suffix from servlet-api-3_1 rul)
        "//lib/truth",
    ],
)
