def _classpath_collector(ctx):
    all = []
    for d in ctx.attr.deps:
        if hasattr(d, "java"):
<<<<<<< HEAD   (86aa0b Bazel: Replace deprecated ctx.new_file() with ctx.actions.de)
            all += d.java.transitive_runtime_deps
            if hasattr(d.java.compilation_info, "runtime_classpath"):
                all += d.java.compilation_info.runtime_classpath
=======
            all.append(d.java.transitive_runtime_deps)
            all.append(d.java.compilation_info.runtime_classpath)
>>>>>>> BRANCH (0c42b3 Merge branch 'stable-2.14' into stable-2.15)
        elif hasattr(d, "files"):
            all.append(d.files)

    as_strs = [c.path for c in depset(transitive = all).to_list()]
    ctx.actions.write(
        output = ctx.outputs.runtime,
        content = "\n".join(sorted(as_strs)),
    )

classpath_collector = rule(
    attrs = {
        "deps": attr.label_list(),
    },
    outputs = {
        "runtime": "%{name}.runtime_classpath",
    },
    implementation = _classpath_collector,
)
