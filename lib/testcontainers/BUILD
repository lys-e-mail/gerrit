java_library(
    name = "duct-tape",
<<<<<<< HEAD   (4b1ff1 Acceptance tests: Replace embedded ES with docker testcontai)
    testonly = True,
    data = ["//lib:LICENSE-testcontainers"],
    visibility = ["//visibility:public"],
    exports = ["@duct_tape//jar"],
)

java_library(
    name = "visible-assertions",
    testonly = True,
    data = ["//lib:LICENSE-testcontainers"],
    visibility = ["//visibility:public"],
    exports = ["@visible_assertions//jar"],
)

java_library(
    name = "jna",
    testonly = True,
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@jna//jar"],
)

java_library(
    name = "testcontainers",
    testonly = True,
=======
    testonly = 1,
    data = ["//lib:LICENSE-testcontainers"],
    visibility = ["//visibility:public"],
    exports = ["@duct_tape//jar"],
)

java_library(
    name = "visible-assertions",
    testonly = 1,
    data = ["//lib:LICENSE-testcontainers"],
    visibility = ["//visibility:public"],
    exports = ["@visible_assertions//jar"],
)

java_library(
    name = "jna",
    testonly = 1,
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
    exports = ["@jna//jar"],
)

java_library(
    name = "testcontainers",
    testonly = 1,
>>>>>>> BRANCH (d92ba3 Merge branch 'stable-2.14' into stable-2.15)
    data = ["//lib:LICENSE-testcontainers"],
    visibility = ["//visibility:public"],
    exports = ["@testcontainers//jar"],
    runtime_deps = [
        ":duct-tape",
        ":jna",
        ":visible-assertions",
        "//lib/log:ext",
    ],
)
