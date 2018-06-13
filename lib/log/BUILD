java_library(
    name = "api",
    data = ["//lib:LICENSE-slf4j"],
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
    visibility = [
        "//lib/jgit/org.eclipse.jgit:__pkg__",
        "//plugins:__pkg__",
    ],
    exports = ["@log-api//jar"],
=======
    visibility = ["//visibility:public"],
    exports = ["@log-api//jar"],
)

java_library(
    name = "nop",
    data = ["//lib:LICENSE-slf4j"],
    visibility = ["//visibility:public"],
    exports = ["@log-nop//jar"],
    runtime_deps = [":api"],
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
)

java_library(
    name = "ext",
    data = ["//lib:LICENSE-slf4j"],
    visibility = ["//visibility:public"],
    exports = ["@log-ext//jar"],
)

java_library(
    name = "impl-log4j",
    data = ["//lib:LICENSE-slf4j"],
    visibility = ["//visibility:public"],
    exports = ["@impl-log4j//jar"],
    runtime_deps = [":log4j"],
)

java_library(
    name = "jcl-over-slf4j",
    data = ["//lib:LICENSE-slf4j"],
    visibility = ["//visibility:public"],
    exports = ["@jcl-over-slf4j//jar"],
)

java_library(
    name = "log4j",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@log4j//jar"],
)

java_library(
    name = "jsonevent-layout",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@jsonevent-layout//jar"],
    runtime_deps = [
        ":json-smart",
        "//lib/commons:lang",
    ],
)

java_library(
    name = "json-smart",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@json-smart//jar"],
)
