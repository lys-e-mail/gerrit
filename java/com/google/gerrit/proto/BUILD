<<<<<<< HEAD   (63770b Upgrade JGit to 5.3.5.201909031855-r)
java_library(
    name = "proto",
    srcs = ["Protos.java"],
    visibility = ["//visibility:public"],
=======
load("@rules_java//java:defs.bzl", "java_binary")

java_binary(
    name = "ProtoGen",
    srcs = ["ProtoGen.java"],
    resource_strip_prefix = "resources",
    resources = ["//resources/com/google/gerrit/proto"],
    visibility = ["//proto:__pkg__"],
>>>>>>> BRANCH (69d4ad Revert "Revert "GerritServer: Silence non-critical logs from)
    deps = [
        "//lib:protobuf",
    ],
)
