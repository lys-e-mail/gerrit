<<<<<<< HEAD   (63770b Upgrade JGit to 5.3.5.201909031855-r)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:gwt.bzl", "gwt_module")

gwt_module(
    name = "client",
    srcs = glob(["common/**/*.java"]),
    exported_deps = [
        "//java/com/google/gerrit/extensions:client",
        "//java/com/google/gerrit/reviewdb:client",
        "//java/com/google/gwtexpui/safehtml",
        "//java/org/eclipse/jgit:Edit",
        "//java/org/eclipse/jgit:client",
        "//lib:gwtjsonrpc",
        "//lib:gwtjsonrpc_src",
    ],
    gwt_xml = "PrettyFormatter.gwt.xml",
    visibility = ["//visibility:public"],
    deps = ["//lib/gwt:user-neverlink"],
)

>>>>>>> BRANCH (69d4ad Revert "Revert "GerritServer: Silence non-critical logs from)
java_library(
    name = "server",
    srcs = glob(["common/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//java/com/google/gerrit/reviewdb:server",
        "//lib:guava",
        "//lib/jgit/org.eclipse.jgit:jgit",
    ],
)
