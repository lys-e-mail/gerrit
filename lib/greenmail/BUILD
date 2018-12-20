package(default_visibility = ["//visibility:public"])

POST_JDK8_DEPS = [":javax-activation"]

java_library(
    name = "javax-activation",
    testonly = 1,
    data = ["//lib:LICENSE-DO_NOT_DISTRIBUTE"],
    exports = ["@javax-activation//jar"],
)

java_library(
    name = "greenmail",
<<<<<<< HEAD   (db068c Merge "Make gr-diff-selection work with native Shadow DOM")
    testonly = 1,
=======
    testonly = True,
>>>>>>> BRANCH (4baf3b Merge changes from topic "testonly-cleanup" into stable-2.16)
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@greenmail//jar"],
    runtime_deps = select({
        "//:java9": POST_JDK8_DEPS,
        "//:java_next": POST_JDK8_DEPS,
        "//conditions:default": [],
    }),
)
