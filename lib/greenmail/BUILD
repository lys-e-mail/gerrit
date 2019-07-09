package(default_visibility = ["//visibility:public"])

POST_JDK8_DEPS = [":javax-activation"]

java_library(
    name = "javax-activation",
<<<<<<< HEAD   (c5faad Merge branch 'stable-2.16' into stable-3.0)
    testonly = True,
=======
    testonly = 1,
>>>>>>> BRANCH (755e5b Merge branch 'stable-2.15' into stable-2.16)
    data = ["//lib:LICENSE-DO_NOT_DISTRIBUTE"],
    exports = ["@javax-activation//jar"],
)

java_library(
    name = "greenmail",
    testonly = True,
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@greenmail//jar"],
    runtime_deps = select({
        "//:java9": POST_JDK8_DEPS,
        "//:java_next": POST_JDK8_DEPS,
        "//conditions:default": [],
    }),
)
