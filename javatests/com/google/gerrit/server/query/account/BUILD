load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:junit.bzl", "junit_tests")

ABSTRACT_QUERY_TEST = ["AbstractQueryAccountsTest.java"]

java_library(
    name = "abstract_query_tests",
    testonly = True,
    srcs = ABSTRACT_QUERY_TEST,
    visibility = ["//visibility:public"],
    runtime_deps = [
<<<<<<< HEAD   (294522 Update git submodules)
        "//java/com/google/gerrit/lucene",
=======
>>>>>>> BRANCH (c492db Merge branch 'stable-2.16' into stable-3.0)
        "//prolog:gerrit-prolog-common",
    ],
    deps = [
        "//java/com/google/gerrit/entities",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gerrit/index",
        "//java/com/google/gerrit/lifecycle",
        "//java/com/google/gerrit/server",
        "//java/com/google/gerrit/server/schema",
        "//java/com/google/gerrit/testing:gerrit-test-util",
        "//lib:guava",
        "//lib:jgit",
        "//lib/guice",
<<<<<<< HEAD   (294522 Update git submodules)
        "//lib/log:log4j",
=======
        "//lib/jgit/org.eclipse.jgit:jgit",
>>>>>>> BRANCH (c492db Merge branch 'stable-2.16' into stable-3.0)
        "//lib/truth",
        "//lib/truth:truth-java8-extension",
<<<<<<< HEAD   (294522 Update git submodules)
        "//resources:log4j-config",
=======
>>>>>>> BRANCH (c492db Merge branch 'stable-2.16' into stable-3.0)
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
        "//java/com/google/gerrit/server",
        "//java/com/google/gerrit/testing:gerrit-test-util",
        "//lib:jgit",
        "//lib/guice",
    ],
)
