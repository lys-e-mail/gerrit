load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (04c987 Merge changes I651c570b,I0d571918)
_JGIT_VERS = "5.1.1.201809181055-r"
=======
_JGIT_VERS = "4.9.5.201809180939-r"
>>>>>>> BRANCH (d4782a Merge branch 'stable-2.14' into stable-2.15)

_DOC_VERS = _JGIT_VERS  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "http://download.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

_JGIT_REPO = MAVEN_CENTRAL  # Leave here even if set to MAVEN_CENTRAL.

# set this to use a local version.
# "/home/<user>/projects/jgit"
LOCAL_JGIT_REPO = ""

def jgit_repos():
    if LOCAL_JGIT_REPO:
        native.local_repository(
            name = "jgit",
            path = LOCAL_JGIT_REPO,
        )
        jgit_maven_repos_dev()
    else:
        jgit_maven_repos()

def jgit_maven_repos_dev():
    # Transitive dependencies from JGit's WORKSPACE.
    maven_jar(
        name = "hamcrest-library",
        artifact = "org.hamcrest:hamcrest-library:1.3",
        sha1 = "4785a3c21320980282f9f33d0d1264a69040538f",
    )
    maven_jar(
        name = "jzlib",
        artifact = "com.jcraft:jzlib:1.1.1",
        sha1 = "a1551373315ffc2f96130a0e5704f74e151777ba",
    )

def jgit_maven_repos():
    maven_jar(
        name = "jgit-lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (04c987 Merge changes I651c570b,I0d571918)
        sha1 = "64dfe41b3c152bb9b7158b214e28467cb1217153",
        src_sha1 = "ff6ab018897cf4213b905e156ac5930bad2bdff1",
=======
        sha1 = "e49fcc98e3c78fea8aeea075e061b6df1b0a9acb",
        src_sha1 = "36cd2ad673e051fa1549bbe3228fc92fb6d29c1f",
>>>>>>> BRANCH (d4782a Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (04c987 Merge changes I651c570b,I0d571918)
        sha1 = "22fd6827fbb6135efd813271185a91f8615538eb",
=======
        sha1 = "f69b3ccd0e9c84bbadf4a1a7f3eee3f62f81b725",
>>>>>>> BRANCH (d4782a Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (04c987 Merge changes I651c570b,I0d571918)
        sha1 = "bfbbdd6aa1893db14f346913aad3f9898b2fe01d",
=======
        sha1 = "96090439253b1e39037f0476734613e57eb513cf",
>>>>>>> BRANCH (d4782a Merge branch 'stable-2.14' into stable-2.15)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (04c987 Merge changes I651c570b,I0d571918)
        sha1 = "6de6de74053d7c28100fe128255d7382a939fe99",
=======
        sha1 = "384987918ef03fd1462e22c65a1a27c4ecc2350a",
>>>>>>> BRANCH (d4782a Merge branch 'stable-2.14' into stable-2.15)
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
