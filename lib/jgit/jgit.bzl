load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (e00abd Merge branch 'stable-2.14' into stable-2.15)
_JGIT_VERS = "4.9.2.201712150930-r.15-g5fe8e31d4"
=======
_JGIT_VERS = "4.7.2.201807261330-r"
>>>>>>> BRANCH (c4311f Set version to 2.14.11)

_DOC_VERS = "4.9.2.201712150930-r"  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "http://download.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

_JGIT_REPO = GERRIT  # Leave here even if set to MAVEN_CENTRAL.

# set this to use a local version.
# "/home/<user>/projects/jgit"
LOCAL_JGIT_REPO = ""

def jgit_repos():
    if LOCAL_JGIT_REPO:
        native.local_repository(
            name = "jgit",
            path = LOCAL_JGIT_REPO,
        )
    else:
        jgit_maven_repos()

def jgit_maven_repos():
    maven_jar(
        name = "jgit-lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e00abd Merge branch 'stable-2.14' into stable-2.15)
        sha1 = "dd93e272fb38c4a0e2b9e1cc39424e1e8d542352",
        src_sha1 = "78d34eb21320b8262a1b52bd5c91e66380d0302a",
=======
        sha1 = "6c08ef848fa5f7d5d49776fa25ec24d738ee457d",
        src_sha1 = "ee14417c135693ddc1eebcf23b8cb661c9b8387d",
>>>>>>> BRANCH (c4311f Set version to 2.14.11)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e00abd Merge branch 'stable-2.14' into stable-2.15)
        sha1 = "5ef78cf1da610c643a9fd03763b4cac2889567af",
=======
        sha1 = "2bde0520c1831eedff5d8e0347e254edc8bf9fa1",
>>>>>>> BRANCH (c4311f Set version to 2.14.11)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e00abd Merge branch 'stable-2.14' into stable-2.15)
        sha1 = "238e20f82cb7b5ad99b60445d36f6c2c13ed8e4e",
=======
        sha1 = "a0ad9edcd5dc5ba2cf54dfaaa542e520e771d2b8",
>>>>>>> BRANCH (c4311f Set version to 2.14.11)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e00abd Merge branch 'stable-2.14' into stable-2.15)
        sha1 = "e16e9635020e8a4a62d93cd05869442690f54209",
=======
        sha1 = "ef8a52ff0a7afcecdb6338b00b03b9a0e0f53dae",
>>>>>>> BRANCH (c4311f Set version to 2.14.11)
        unsign = True,
    )

def jgit_dep(name):
    mapping = {
        "@jgit-junit//jar": "@jgit//org.eclipse.jgit.junit:junit",
        "@jgit-lib//jar:src": "@jgit//org.eclipse.jgit:libjgit-src.jar",
        "@jgit-lib//jar": "@jgit//org.eclipse.jgit:jgit",
        "@jgit-servlet//jar": "@jgit//org.eclipse.jgit.http.server:jgit-servlet",
        "@jgit-archive//jar": "@jgit//org.eclipse.jgit.archive:jgit-archive",
    }

    if LOCAL_JGIT_REPO:
        return mapping[name]
    else:
        return name
