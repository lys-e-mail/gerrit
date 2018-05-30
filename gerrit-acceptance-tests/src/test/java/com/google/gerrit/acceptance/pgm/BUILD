load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

acceptance_tests(
    srcs = glob(
        ["*IT.java"],
        exclude = ["ElasticReindexIT.java"],
    ),
    group = "pgm",
    labels = ["pgm"],
<<<<<<< HEAD   (094671 Merge branch 'stable-2.14' into stable-2.15)
    vm_args = ["-Xmx512m"],
    deps = [":util"],
)

java_library(
    name = "util",
    testonly = 1,
    srcs = ["IndexUpgradeController.java"],
    deps = ["//gerrit-acceptance-tests:lib"],
=======
    deps = [":util"],
>>>>>>> BRANCH (c99a18 ElasticIndexVersionDiscovery: Convert to Java stream API)
)

acceptance_tests(
    srcs = [
        "ElasticReindexIT.java",
    ],
    group = "elastic",
    labels = [
        "docker",
        "elastic",
        "pgm",
    ],
<<<<<<< HEAD   (094671 Merge branch 'stable-2.14' into stable-2.15)
    deps = [
        ":util",
        "//gerrit-elasticsearch:elasticsearch_test_utils",
    ],
=======
    deps = [":util"],
)

java_library(
    name = "util",
    testonly = 1,
    srcs = ["AbstractReindexTests.java"],
    deps = ["//gerrit-acceptance-tests:lib"],
>>>>>>> BRANCH (c99a18 ElasticIndexVersionDiscovery: Convert to Java stream API)
)
