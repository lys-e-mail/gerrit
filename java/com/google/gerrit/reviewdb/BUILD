load("//tools/bzl:gwt.bzl", "gwt_module")

package(
    default_visibility = ["//visibility:public"],
)

<<<<<<< HEAD   (5ac0d6 Merge "Remove @VisibleForTesting annotation on ChangeKindCac)
=======
gwt_module(
    name = "client",
    srcs = glob(["client/**/*.java"]),
    gwt_xml = "ReviewDB.gwt.xml",
    deps = [
        "//java/com/google/gerrit/extensions:client",
        "//lib:gwtorm-client",
        "//lib:gwtorm-client_src",
    ],
)

>>>>>>> BRANCH (ec26f0 Merge branch 'stable-2.15' into stable-2.16)
java_library(
    name = "server",
    srcs = glob(["**/*.java"]),
    deps = [
        "//java/com/google/gerrit/common:annotations",
        "//java/com/google/gerrit/extensions:api",
        "//lib:guava",
        "//lib:gwtorm",
        "//lib:protobuf",
        "//proto:entities_java_proto",
    ],
)
