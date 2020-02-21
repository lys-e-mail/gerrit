load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:junit.bzl", "junit_tests")

ABSTRACT_QUERY_TEST = ["AbstractQueryProjectsTest.java"]

java_library(
    name = "abstract_query_tests",
    testonly = True,
    srcs = ABSTRACT_QUERY_TEST,
    visibility = ["//visibility:public"],
    runtime_deps = ["//java/com/google/gerrit/lucene"],
    deps = [
        "//java/com/google/gerrit/entities",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gerrit/index",
        "//java/com/google/gerrit/index/project",
        "//java/com/google/gerrit/lifecycle",
        "//java/com/google/gerrit/server",
        "//java/com/google/gerrit/server/schema",
        "//java/com/google/gerrit/testing:gerrit-test-util",
        "//lib:guava",
        "//lib:jgit",
        "//lib/guice",
<<<<<<< HEAD   (7dfa6a Merge "Merge branch 'stable-3.0' into stable-3.1" into stabl)
        "//lib/log:log4j",
=======
        "//lib/jgit/org.eclipse.jgit:jgit",
>>>>>>> BRANCH (7bcca9 Merge "Merge branch 'stable-2.16' into stable-3.0" into stab)
        "//lib/truth",
    ],
)

junit_tests(
    name = "lucene_query_test",
    size = "large",
    srcs = glob(
        ["*.java"],
        exclude = ABSTRACT_QUERY_TEST,
    ),
    visibility = ["//visibility:public"],
    deps = [
        ":abstract_query_tests",
        "//java/com/google/gerrit/index/project",
        "//java/com/google/gerrit/testing:gerrit-test-util",
        "//lib:jgit",
        "//lib/guice",
    ],
)
