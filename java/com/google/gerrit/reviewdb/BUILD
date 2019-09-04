<<<<<<< HEAD   (acddb5 Merge branch 'stable-2.16' into stable-3.0)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:gwt.bzl", "gwt_module")

>>>>>>> BRANCH (68b414 Merge "Load proto_library from @rules_proto//proto:defs.bzl")
package(
    default_visibility = ["//visibility:public"],
)

java_library(
    name = "server",
    srcs = glob(["**/*.java"]),
    deps = [
        "//java/com/google/gerrit/common:annotations",
        "//java/com/google/gerrit/extensions:api",
        "//java/com/google/gwtorm",
        "//lib:guava",
        "//lib:protobuf",
        "//proto:entities_java_proto",
    ],
)
