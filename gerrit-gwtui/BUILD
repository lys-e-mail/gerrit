<<<<<<< HEAD   (7880b0 Add capability check when adding plugin entries to admin nav)
=======
load(
    "//tools/bzl:gwt.bzl",
    "gen_ui_module",
    "gwt_genrule",
    "gwt_user_agent_permutations",
)
load("//tools/bzl:java.bzl", "java_library2")
load("//tools/bzl:junit.bzl", "junit_tests")
load("//tools/bzl:license.bzl", "license_test")

gwt_genrule()

gwt_genrule(suffix = "_r")

gen_ui_module(name = "ui_module")

gen_ui_module(
    name = "ui_module",
    suffix = "_r",
)

gwt_user_agent_permutations()

java_library2(
    name = "client-lib",
    srcs = glob(["src/main/**/*.java"]),
    exported_deps = [":ui_module"],
    resources = glob(["src/main/**/*"]),
    visibility = ["//visibility:public"],
    deps = [
        "//gerrit-gwtui-common:client-lib",
        "//lib/gwt:dev",
        "//lib/gwt:user",
    ],
)

license_test(
    name = "ui_module_license_test",
    target = ":ui_module",
)

junit_tests(
    name = "ui_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        ":ui_module",
        "//java/com/google/gerrit/common:client",
        "//java/com/google/gerrit/extensions:client",
        "//lib:junit",
        "//lib/gwt:dev",
        "//lib/gwt:user",
        "//lib/truth",
    ],
)
>>>>>>> BRANCH (444d33 Merge branch 'stable-2.15' into stable-2.16)
