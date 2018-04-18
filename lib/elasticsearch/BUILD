package(default_visibility = ["//visibility:public"])

java_library(
    name = "elasticsearch",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@elasticsearch//jar"],
    runtime_deps = [
        ":compress-lzf",
        ":hppc",
<<<<<<< HEAD   (3d3c21 Merge "AccountCreator: generate P256 ECDSA keys.")
        ":jna",
        ":joda-time",
=======
>>>>>>> BRANCH (421930 Merge branch 'stable-2.14' into stable-2.15)
        ":jsr166e",
        ":netty",
        ":t-digest",
        "//lib/jackson:jackson-core",
        "//lib/jackson:jackson-dataformat-cbor",
        "//lib/jackson:jackson-dataformat-smile",
<<<<<<< HEAD   (3d3c21 Merge "AccountCreator: generate P256 ECDSA keys.")
        "//lib/lucene:lucene-codecs",
=======
>>>>>>> BRANCH (421930 Merge branch 'stable-2.14' into stable-2.15)
        "//lib/lucene:lucene-highlighter",
        "//lib/lucene:lucene-join",
        "//lib/lucene:lucene-memory",
        "//lib/lucene:lucene-queries",
        "//lib/lucene:lucene-spatial",
        "//lib/lucene:lucene-suggest",
    ],
)

java_library(
    name = "joda-time",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@joda_time//jar"],
    runtime_deps = ["joda-convert"],
)

java_library(
    name = "joda-convert",
    data = ["//lib:LICENSE-Apache2.0"],
    exports = ["@joda_convert//jar"],
)

java_library(
    name = "compress-lzf",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//lib/elasticsearch:__pkg__"],
    exports = ["@compress_lzf//jar"],
)

java_library(
    name = "hppc",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//lib/elasticsearch:__pkg__"],
    exports = ["@hppc//jar"],
)

java_library(
    name = "jsr166e",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//lib/elasticsearch:__pkg__"],
    exports = ["@jsr166e//jar"],
)

java_library(
    name = "netty",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//lib/elasticsearch:__pkg__"],
    exports = ["@netty//jar"],
)

java_library(
    name = "t-digest",
    data = ["//lib:LICENSE-Apache2.0"],
    visibility = ["//lib/elasticsearch:__pkg__"],
    exports = ["@t_digest//jar"],
)
