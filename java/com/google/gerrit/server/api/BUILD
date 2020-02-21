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
<<<<<<< HEAD   (7dfa6a Merge "Merge branch 'stable-3.0' into stable-3.1" into stabl)
=======
        "//java/com/google/gerrit/reviewdb:server",
>>>>>>> BRANCH (7bcca9 Merge "Merge branch 'stable-2.16' into stable-3.0" into stab)
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
