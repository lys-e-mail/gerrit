<<<<<<< HEAD   (aebb3f Merge branch 'stable-2.15' into stable-2.16)
=======
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
>>>>>>> BRANCH (1ce5c4 Merge branch 'stable-2.14' into stable-2.15)
