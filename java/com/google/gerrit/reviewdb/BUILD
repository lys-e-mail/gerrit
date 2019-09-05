<<<<<<< HEAD   (63770b Upgrade JGit to 5.3.5.201909031855-r)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:gwt.bzl", "gwt_module")

>>>>>>> BRANCH (69d4ad Revert "Revert "GerritServer: Silence non-critical logs from)
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
