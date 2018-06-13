load("//lib/jgit:jgit.bzl", "jgit_dep")

java_library(
    name = "jgit",
    data = ["//lib:LICENSE-jgit"],
    visibility = ["//visibility:public"],
    exports = [jgit_dep("@jgit-lib//jar")],
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
    runtime_deps = [
        ":javaewah",
        "//lib/log:api",
    ],
=======
    runtime_deps = [":javaewah"],
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
)

alias(
    name = "jgit-source",
    actual = jgit_dep("@jgit-lib//jar:src"),
    visibility = ["//visibility:public"],
)

java_library(
    name = "javaewah",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@javaewah//jar"],
)
