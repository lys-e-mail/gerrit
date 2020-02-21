load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "api",
    srcs = glob(
        ["**/*.java"],
    ),
    visibility = ["//visibility:public"],
    deps = [
        "//java/com/google/gerrit/common:annotations",
        "//java/com/google/gerrit/common:server",
        "//java/com/google/gerrit/entities",
        "//java/com/google/gerrit/exceptions",
        "//java/com/google/gerrit/extensions:api",
<<<<<<< HEAD   (294522 Update git submodules)
=======
        "//java/com/google/gerrit/reviewdb:server",
>>>>>>> BRANCH (c492db Merge branch 'stable-2.16' into stable-3.0)
        "//java/com/google/gerrit/server",
        "//java/com/google/gerrit/server/restapi",
        "//java/com/google/gerrit/util/cli",
        "//lib:args4j",
        "//lib:guava",
        "//lib:jgit",
        "//lib:servlet-api",
        "//lib/guice",
        "//lib/guice:guice-assistedinject",
    ],
)
