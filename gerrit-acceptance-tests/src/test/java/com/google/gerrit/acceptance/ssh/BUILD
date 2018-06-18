<<<<<<< HEAD   (9a3249 Update git submodules)
=======
load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

java_library(
    name = "util",
    testonly = 1,
    srcs = ["AbstractIndexTests.java"],
    deps = ["//gerrit-acceptance-tests:lib"],
)

acceptance_tests(
    srcs = glob(
        ["*IT.java"],
        exclude = ["ElasticIndexIT.java"],
    ),
    group = "ssh",
    labels = ["ssh"],
    deps = [
        ":util",
        "//lib/commons:compress",
    ],
)

acceptance_tests(
    srcs = ["ElasticIndexIT.java"],
    group = "elastic",
    labels = [
        "elastic",
        "docker",
        "ssh",
    ],
    deps = [
        ":util",
        "//gerrit-elasticsearch:elasticsearch",
        "//gerrit-elasticsearch:elasticsearch_test_utils",
        "//lib/commons:compress",
    ],
)
>>>>>>> BRANCH (877b49 Merge branch 'stable-2.14' into stable-2.15)
