<<<<<<< HEAD   (f09add Merge "ElasticReindexIT: implement this class from stable")
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
        "exclusive",
        "pgm",
    ],
    deps = [
        ":util",
        "//gerrit-elasticsearch:elasticsearch",
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
>>>>>>> BRANCH (e978e8 Merge branch 'stable-2.14' into stable-2.15)
