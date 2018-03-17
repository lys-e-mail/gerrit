<<<<<<< HEAD   (4505cc Merge "Fix javascript errors in gr-admin-view components")
=======
load("//gerrit-acceptance-tests:tests.bzl", "acceptance_tests")

acceptance_tests(
    srcs = glob(["*IT.java"]),
    group = "server_notedb",
    labels = [
        "notedb",
        "server",
    ],
    # TODO(dborowitz): Fix leaks in local disk tests so we can reduce heap size.
    # http://crbug.com/gerrit/8567
    vm_args = ["-Xmx1024m"],
)
>>>>>>> BRANCH (6e0601 Merge branch 'stable-2.14' into stable-2.15)
