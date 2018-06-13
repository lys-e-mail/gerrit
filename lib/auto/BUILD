java_plugin(
    name = "auto-annotation-plugin",
    processor_class = "com.google.auto.value.processor.AutoAnnotationProcessor",
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
    deps = [
        "@auto-value-annotations//jar",
        "@auto-value//jar",
    ],
=======
    deps = ["@auto-value//jar"],
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
)

java_plugin(
    name = "auto-value-plugin",
    processor_class = "com.google.auto.value.processor.AutoValueProcessor",
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
    deps = [
        "@auto-value-annotations//jar",
        "@auto-value//jar",
    ],
=======
    deps = ["@auto-value//jar"],
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
)

java_library(
    name = "auto-value",
    data = ["//lib:LICENSE-Apache2.0"],
    exported_plugins = [
        ":auto-annotation-plugin",
        ":auto-value-plugin",
    ],
    visibility = ["//visibility:public"],
    exports = ["@auto-value//jar"],
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
)

java_library(
    name = "auto-value-annotations",
    data = ["//lib:LICENSE-Apache2.0"],
    exported_plugins = [
        ":auto-annotation-plugin",
        ":auto-value-plugin",
    ],
    visibility = ["//visibility:public"],
    exports = ["@auto-value-annotations//jar"],
=======
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
)
