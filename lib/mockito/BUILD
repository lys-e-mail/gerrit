load("@rules_java//java:defs.bzl", "java_library")

package(
    default_testonly = True,
    default_visibility = ["//visibility:private"],
)

java_library(
    name = "mockito",
<<<<<<< HEAD   (75b159 CreateChange: Do not fail with 500 ISE if called by anonymou)
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//visibility:public"],
=======
    data = ["//lib:LICENSE-mockito"],
    # Only exposed for plugin tests; core tests should use Easymock
    visibility = ["//java/com/google/gerrit/acceptance:__pkg__"],
>>>>>>> BRANCH (93f7fc Fix the license for mockito)
    exports = ["@mockito//jar"],
    runtime_deps = [
        ":byte-buddy",
        ":byte-buddy-agent",
        ":objenesis",
    ],
)

java_library(
    name = "byte-buddy",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@bytebuddy//jar"],
)

java_library(
    name = "byte-buddy-agent",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@bytebuddy-agent//jar"],
)

java_library(
    name = "objenesis",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@objenesis//jar"],
)
