# NOTE: GerritLauncher must be a single, self-contained class. Do not add any
# additional srcs or deps to this rule.
java_library(
    name = "launcher",
    srcs = ["GerritLauncher.java"],
<<<<<<< HEAD   (cf40b3 Fix DbInjector creation in WebAppinitializer)
    resources = [":workspace-root.txt"],
    visibility = ["//visibility:public"],
)

# The root of the workspace is non-hermetic, but we need it for
# on-the-flyPolyGerrit updates.
genrule(
    name = "gen_root",
    outs = ["workspace-root.txt"],
    cmd = ("cat bazel-out/stable-status.txt | " +
           "grep STABLE_WORKSPACE_ROOT | cut -d ' ' -f 2 > $@"),
    stamp = 1,
=======
>>>>>>> BRANCH (17cea2 Merge branch 'stable-2.15' into stable-2.16)
    visibility = ["//visibility:public"],
)
