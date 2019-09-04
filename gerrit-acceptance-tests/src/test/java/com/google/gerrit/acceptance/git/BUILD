<<<<<<< HEAD   (4847de Use --header-text-color css variable for some classes in gr-)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

[acceptance_tests(
    srcs = [f],
    group = f[:f.index(".")],
    labels = ["git"],
    deps = [
        ":push_for_review",
        ":submodule_util",
    ],
) for f in glob(["*IT.java"])]

java_library(
    name = "push_for_review",
    testonly = 1,
    srcs = ["AbstractPushForReview.java"],
    deps = [
        "//gerrit-acceptance-tests:lib",
        "//lib/joda:joda-time",
    ],
)

java_library(
    name = "submodule_util",
    testonly = 1,
    srcs = ["AbstractSubmoduleSubscription.java"],
    deps = ["//gerrit-acceptance-tests:lib"],
)
>>>>>>> BRANCH (3ab6d2 Merge branch 'stable-2.14' into stable-2.15)
