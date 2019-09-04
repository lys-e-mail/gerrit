<<<<<<< HEAD   (91666f Update git submodules)
=======
load("@rules_java//java:defs.bzl", "java_binary", "java_library")

java_binary(
    name = "asciidoc",
    main_class = "AsciiDoctor",
    visibility = ["//visibility:public"],
    runtime_deps = [":asciidoc_lib"],
)

java_library(
    name = "asciidoc_lib",
    srcs = ["java/AsciiDoctor.java"],
    visibility = ["//visibility:public"],
    deps = [
        ":asciidoctor",
        "//lib:args4j",
        "//lib:guava",
        "//lib/log:api",
        "//lib/log:nop",
    ],
)

java_binary(
    name = "doc_indexer",
    main_class = "DocIndexer",
    visibility = ["//visibility:public"],
    runtime_deps = [":doc_indexer_lib"],
)

java_library(
    name = "doc_indexer_lib",
    srcs = ["java/DocIndexer.java"],
    visibility = ["//visibility:public"],
    deps = [
        ":asciidoc_lib",
        "//gerrit-server:constants",
        "//lib:args4j",
        "//lib:guava",
        "//lib/lucene:lucene-analyzers-common",
        "//lib/lucene:lucene-core-and-backward-codecs",
    ],
)

>>>>>>> BRANCH (3ab6d2 Merge branch 'stable-2.14' into stable-2.15)
java_library(
    name = "asciidoctor",
    data = ["//lib:LICENSE-asciidoctor"],
    visibility = ["//java/com/google/gerrit/asciidoctor:__pkg__"],
    exports = ["@asciidoctor//jar"],
    runtime_deps = [":jruby"],
)

java_library(
    name = "jruby",
    data = ["//lib:LICENSE-DO_NOT_DISTRIBUTE"],
    exports = ["@jruby//jar"],
)
