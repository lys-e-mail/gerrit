load("//tools/bzl:genrule2.bzl", "genrule2")
<<<<<<< HEAD   (a82fe4 Merge branch 'stable-2.16' into stable-3.0)
=======
load(
    "//tools/bzl:gwt.bzl",
    "GWT_COMPILER_ARGS",
    "GWT_JVM_ARGS",
    "GWT_PLUGIN_DEPS_NEVERLINK",
    "GWT_TRANSITIVE_DEPS",
    "gwt_binary",
    _gwt_plugin_deps = "GWT_PLUGIN_DEPS",
)
>>>>>>> BRANCH (65c6c5 Merge branch 'stable-2.15' into stable-2.16)

GWT_PLUGIN_DEPS = _gwt_plugin_deps

PLUGIN_DEPS = ["//plugins:plugin-lib"]

PLUGIN_DEPS_NEVERLINK = ["//plugins:plugin-lib-neverlink"]

PLUGIN_TEST_DEPS = [
    "//java/com/google/gerrit/acceptance:lib",
    "//lib/bouncycastle:bcpg",
    "//lib/bouncycastle:bcpkix",
    "//lib/bouncycastle:bcprov",
]

def gerrit_plugin(
        name,
        deps = [],
        provided_deps = [],
        srcs = [],
        resources = [],
        manifest_entries = [],
        dir_name = None,
        target_suffix = "",
        **kwargs):
    native.java_library(
        name = name + "__plugin",
        srcs = srcs,
        resources = resources,
        deps = provided_deps + deps + PLUGIN_DEPS_NEVERLINK,
        visibility = ["//visibility:public"],
        **kwargs
    )

    static_jars = []

    if not dir_name:
        dir_name = name

    native.java_binary(
        name = "%s__non_stamped" % name,
        deploy_manifest_lines = manifest_entries + ["Gerrit-ApiType: plugin"],
        main_class = "Dummy",
        runtime_deps = [
            ":%s__plugin" % name,
        ] + static_jars,
        visibility = ["//visibility:public"],
        **kwargs
    )

    # TODO(davido): Remove manual merge of manifest file when this feature
    # request is implemented: https://github.com/bazelbuild/bazel/issues/2009
    genrule2(
        name = name + target_suffix,
        stamp = 1,
        srcs = ["%s__non_stamped_deploy.jar" % name],
        cmd = " && ".join([
            "GEN_VERSION=$$(cat bazel-out/stable-status.txt | grep -w STABLE_BUILD_%s_LABEL | cut -d ' ' -f 2)" % dir_name.upper(),
            "cd $$TMP",
            "unzip -q $$ROOT/$<",
            "echo \"Implementation-Version: $$GEN_VERSION\n$$(cat META-INF/MANIFEST.MF)\" > META-INF/MANIFEST.MF",
            "zip -qr $$ROOT/$@ .",
        ]),
        outs = ["%s%s.jar" % (name, target_suffix)],
        visibility = ["//visibility:public"],
    )
