load("@npm//@bazel/rollup:index.bzl", "rollup_bundle")
load("@npm//@bazel/terser:index.bzl", "terser_minified")
<<<<<<< HEAD   (cbc59c Merge "Remove unused licenses")
load("//tools/bzl:genrule2.bzl", "genrule2")
=======
load("//lib/js:npm.bzl", "NPM_SHA1S", "NPM_VERSIONS")
load("//tools/bzl:genrule2.bzl", "genrule2")

NPMJS = "NPMJS"

GERRIT = "GERRIT:"

def _npm_tarball(name):
    return "%s@%s.npm_binary.tgz" % (name, NPM_VERSIONS[name])

def _npm_binary_impl(ctx):
    """rule to download a NPM archive."""
    name = ctx.name
    version = NPM_VERSIONS[name]
    sha1 = NPM_SHA1S[name]

    dir = "%s-%s" % (name, version)
    filename = "%s.tgz" % dir
    base = "%s@%s.npm_binary.tgz" % (name, version)
    dest = ctx.path(base)
    repository = ctx.attr.repository
    if repository == GERRIT:
        url = "https://gerrit-maven.storage.googleapis.com/npm-packages/%s" % filename
    elif repository == NPMJS:
        url = "https://registry.npmjs.org/%s/-/%s" % (name, filename)
    else:
        fail("repository %s not in {%s,%s}" % (repository, GERRIT, NPMJS))

    python = ctx.which("python")
    script = ctx.path(ctx.attr._download_script)

    args = [python, script, "-o", dest, "-u", url, "-v", sha1]
    out = ctx.execute(args)
    if out.return_code:
        fail("failed %s: %s" % (args, out.stderr))
    ctx.file("BUILD", "package(default_visibility=['//visibility:public'])\nfilegroup(name='tarball', srcs=['%s'])" % base, False)

npm_binary = repository_rule(
    attrs = {
        "repository": attr.string(default = NPMJS),
        # Label resolves within repo of the .bzl file.
        "_download_script": attr.label(default = Label("//tools:download_file.py")),
    },
    local = True,
    implementation = _npm_binary_impl,
)
>>>>>>> BRANCH (8fd931 Fix link for change.enableAssignee configuration option)

ComponentInfo = provider()

def _js_component(ctx):
    dir = ctx.outputs.zip.path + ".dir"
    name = ctx.outputs.zip.basename
    if name.endswith(".zip"):
        name = name[:-4]
    dest = "%s/%s" % (dir, name)
    cmd = " && ".join([
        "TZ=UTC",
        "export TZ",
        "mkdir -p %s" % dest,
        "cp %s %s/" % (" ".join([s.path for s in ctx.files.srcs]), dest),
        "cd %s" % dir,
        "find . -exec touch -t 198001010000 '{}' ';'",
        "zip -Xqr ../%s *" % ctx.outputs.zip.basename,
    ])

    ctx.actions.run_shell(
        inputs = ctx.files.srcs,
        outputs = [ctx.outputs.zip],
        command = cmd,
        mnemonic = "GenJsComponentZip",
    )

    licenses = []
    if ctx.file.license:
        licenses.append(ctx.file.license)

    return [
        ComponentInfo(
            transitive_licenses = depset(licenses),
            transitive_versions = depset(),
            transitive_zipfiles = list([ctx.outputs.zip]),
        ),
    ]

js_component = rule(
    _js_component,
    attrs = {
        "srcs": attr.label_list(allow_files = [".js"]),
        "license": attr.label(allow_single_file = True),
    },
    outputs = {
        "zip": "%{name}.zip",
    },
)

def polygerrit_plugin(name, app, plugin_name = None):
    """Produces plugin file set with minified javascript.

    This rule minifies a plugin javascript file, potentially renames it, and produces a file set.
    Output of this rule is a FileSet with "${plugin_name}.js".

    Args:
      name: String, rule name.
      app: String, the main or root source file. This must be single JavaScript file.
      plugin_name: String, plugin name. ${name} is used if not provided.
    """
    if not plugin_name:
        plugin_name = name

    terser_minified(
        name = plugin_name + ".min",
        sourcemap = False,
        src = app,
    )

    native.genrule(
        name = name + "_rename_js",
        srcs = [plugin_name + ".min"],
        outs = [plugin_name + ".js"],
        cmd = "cp $< $@",
        output_to_bindir = True,
    )

    native.filegroup(
        name = name,
        srcs = [plugin_name + ".js"],
    )

def gerrit_js_bundle(name, srcs, entry_point):
    """Produces a Gerrit JavaScript bundle archive.

    This rule bundles and minifies the javascript files of a frontend plugin and
    produces a file archive.
    Output of this rule is an archive with "${name}.jar" with specific layout for
    Gerrit frontentd plugins. That archive should be provided to gerrit_plugin
    rule as resource_jars attribute.

    Args:
      name: Rule name.
      srcs: Plugin sources.
      entry_point: Plugin entry_point.
    """
    rollup_bundle(
        name = name + "-bundle",
        srcs = srcs,
        entry_point = entry_point,
        format = "iife",
        rollup_bin = "//tools/node_tools:rollup-bin",
        sourcemap = "hidden",
        deps = [
            "@tools_npm//rollup-plugin-node-resolve",
        ],
    )

    terser_minified(
        name = name + ".min",
        sourcemap = False,
        src = name + "-bundle.js",
    )

    native.genrule(
        name = name + "_rename_js",
        srcs = [name + ".min"],
        outs = [name + ".js"],
        cmd = "cp $< $@",
        output_to_bindir = True,
    )

    genrule2(
        name = name,
        srcs = [name + ".js"],
        outs = [name + ".jar"],
        cmd = " && ".join([
            "mkdir $$TMP/static",
            "cp $(SRCS) $$TMP/static",
            "cd $$TMP",
            "zip -Drq $$ROOT/$@ -g .",
        ]),
    )
