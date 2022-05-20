load("//tools/bzl:junit.bzl", "junit_tests")

junit_tests(
    name = "tests",
    srcs = glob(["*.java"]),
    deps = [
        "//java/com/google/gerrit/server",
        "//java/com/google/gerrit/testing:gerrit-test-util",
        "//javatests/com/google/gerrit/util/http/testutil",
        "//lib:junit",
        "//lib/truth",
        "//lib/truth:truth-java8-extension",
<<<<<<< HEAD   (462bb1 Merge branch 'stable-2.16' into stable-3.0)
        "@servlet-api//jar",
=======
        "@jgit-lib//jar",
        "@servlet-api-3_1//jar",
>>>>>>> BRANCH (500346 Set PerThreadCache as readonly after creating a new patch-se)
    ],
)
