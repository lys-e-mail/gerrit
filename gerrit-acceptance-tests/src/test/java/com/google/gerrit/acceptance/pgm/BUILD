<<<<<<< HEAD   (20e6ff Merge "Fix automatic H2 cache invalidation on serialVersionU)
=======
load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

acceptance_tests(
    srcs = glob(
        ["*IT.java"],
        exclude = ["ElasticReindexIT.java"],
    ),
    group = "pgm",
    labels = ["pgm"],
    vm_args = ["-Xmx512m"],
    deps = [":util"],
)

acceptance_tests(
    srcs = [
        "ElasticReindexIT.java",
    ],
    group = "elastic",
    labels = [
        "docker",
        "elastic",
        "flaky",
        "pgm",
    ],
    deps = [
        ":util",
        "//gerrit-elasticsearch:elasticsearch_test_utils",
    ],
)

java_library(
    name = "util",
    testonly = 1,
    srcs = [
        "AbstractReindexTests.java",
        "IndexUpgradeController.java",
    ],
    deps = ["//gerrit-acceptance-tests:lib"],
)
>>>>>>> BRANCH (e178d5 Merge branch 'stable-2.14' into stable-2.15)
