<<<<<<< HEAD   (acddb5 Merge branch 'stable-2.16' into stable-3.0)
=======
load("@rules_java//java:defs.bzl", "java_library")

[java_library(
    name = n,
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@%s//jar" % n],
) for n in [
    "ant",
    "colt",
    "dev",
    "javax-validation",
    "jsinterop-annotations",
    "tapestry",
    "user",
    "w3c-css-sac",
]]

java_library(
    name = "user-neverlink",
    data = ["//lib:LICENSE-Apache2.0"],
    neverlink = 1,
    visibility = ["//visibility:public"],
    exports = ["@user//jar"],
)

java_library(
    name = "dev-neverlink",
    data = ["//lib:LICENSE-Apache2.0"],
    neverlink = 1,
    visibility = ["//visibility:public"],
    exports = ["@dev//jar"],
)

java_library(
    name = "javax-validation_src",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@javax-validation//jar:src"],
)

java_library(
    name = "jsinterop-annotations_src",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@jsinterop-annotations//jar:src"],
)
>>>>>>> BRANCH (68b414 Merge "Load proto_library from @rules_proto//proto:defs.bzl")
