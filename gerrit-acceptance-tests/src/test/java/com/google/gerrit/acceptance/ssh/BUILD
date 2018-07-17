<<<<<<< HEAD   (2971c4 Documentation: Add commitmsg hook info)
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
        "docker",
        "elastic",
        "exclusive",
        "ssh",
    ],
    deps = [
        ":util",
        "//gerrit-elasticsearch:elasticsearch",
        "//gerrit-elasticsearch:elasticsearch_test_utils",
        "//lib/commons:compress",
    ],
)
>>>>>>> BRANCH (0f9cd5 Polygerrit: Always create new changes as WIP)
