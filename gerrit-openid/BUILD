<<<<<<< HEAD   (4847de Use --header-text-color css variable for some classes in gr-)
=======
load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "openid",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**/*"]),
    visibility = ["//visibility:public"],
    deps = [
        # We want all these deps to be provided_deps
        "//gerrit-common:annotations",
        "//gerrit-common:server",
        "//gerrit-extension-api:api",
        "//gerrit-gwtexpui:server",
        "//gerrit-httpd:httpd",
        "//gerrit-reviewdb:server",
        "//gerrit-server:server",
        "//lib:guava",
        "//lib:gwtorm",
        "//lib:servlet-api-3_1",
        "//lib/commons:codec",
        "//lib/guice",
        "//lib/guice:guice-servlet",
        "//lib/jgit/org.eclipse.jgit:jgit",
        "//lib/log:api",
        "//lib/openid:consumer",
    ],
)
>>>>>>> BRANCH (3ab6d2 Merge branch 'stable-2.14' into stable-2.15)
