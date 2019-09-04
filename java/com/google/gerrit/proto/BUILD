<<<<<<< HEAD   (acddb5 Merge branch 'stable-2.16' into stable-3.0)
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
>>>>>>> BRANCH (68b414 Merge "Load proto_library from @rules_proto//proto:defs.bzl")
    deps = [
        "//lib:protobuf",
    ],
)
