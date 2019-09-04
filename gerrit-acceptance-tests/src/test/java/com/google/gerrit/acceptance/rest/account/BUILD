<<<<<<< HEAD   (4847de Use --header-text-color css variable for some classes in gr-)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

acceptance_tests(
    srcs = glob(["*IT.java"]),
    group = "rest_account",
    labels = ["rest"],
    deps = [":util"],
)

java_library(
    name = "util",
    testonly = 1,
    srcs = [
        "AccountAssert.java",
        "CapabilityInfo.java",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//gerrit-acceptance-tests:lib",
        "//gerrit-reviewdb:server",
        "//lib:gwtorm",
        "//lib:junit",
    ],
)
>>>>>>> BRANCH (3ab6d2 Merge branch 'stable-2.14' into stable-2.15)
