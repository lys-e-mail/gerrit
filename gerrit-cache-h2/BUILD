<<<<<<< HEAD   (87d95b Merge "Expand docs on how to include external dependencies i)
=======
load("//tools/bzl:junit.bzl", "junit_tests")

java_library(
    name = "cache-h2",
    srcs = glob(["src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "//gerrit-common:server",
        "//gerrit-extension-api:api",
        "//gerrit-server:server",
        "//lib:caffeine",
        "//lib:caffeine_guava",
        "//lib:guava",
        "//lib:h2",
        "//lib/guice",
        "//lib/jgit/org.eclipse.jgit:jgit",
        "//lib/log:api",
    ],
)

junit_tests(
    name = "tests",
    srcs = glob(["src/test/java/**/*.java"]),
    deps = [
        ":cache-h2",
        "//gerrit-server:server",
        "//lib:caffeine",
        "//lib:caffeine_guava",
        "//lib:guava",
        "//lib:h2",
        "//lib:junit",
        "//lib/guice",
    ],
)
>>>>>>> BRANCH (3ffeee Merge "Merge branch 'stable-2.14' into stable-2.15" into sta)
