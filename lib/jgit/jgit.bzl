load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (fb2f47 Upgrade JGit to 4.9.5.201809180939-r)
_JGIT_VERS = "4.9.5.201809180939-r"
=======
_JGIT_VERS = "4.7.4.201809180905-r"
>>>>>>> BRANCH (08f5fd Upgrade elasticsearch-rest-client to 6.4.1)

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

def jgit_maven_repos():
    maven_jar(
        name = "jgit-lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (fb2f47 Upgrade JGit to 4.9.5.201809180939-r)
        sha1 = "e49fcc98e3c78fea8aeea075e061b6df1b0a9acb",
        src_sha1 = "36cd2ad673e051fa1549bbe3228fc92fb6d29c1f",
=======
        sha1 = "969b065546911db86ec41a7726d12292f905b875",
        src_sha1 = "c19ef80da53442b46005a5cf78396362c2d9e3e5",
>>>>>>> BRANCH (08f5fd Upgrade elasticsearch-rest-client to 6.4.1)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (fb2f47 Upgrade JGit to 4.9.5.201809180939-r)
        sha1 = "f69b3ccd0e9c84bbadf4a1a7f3eee3f62f81b725",
=======
        sha1 = "7973c6c6e6f023ab0dce33ff9caf8c57e5216a29",
>>>>>>> BRANCH (08f5fd Upgrade elasticsearch-rest-client to 6.4.1)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (fb2f47 Upgrade JGit to 4.9.5.201809180939-r)
        sha1 = "96090439253b1e39037f0476734613e57eb513cf",
=======
        sha1 = "21117e9050f36366a22378db1e569a3f36044c48",
>>>>>>> BRANCH (08f5fd Upgrade elasticsearch-rest-client to 6.4.1)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (fb2f47 Upgrade JGit to 4.9.5.201809180939-r)
        sha1 = "384987918ef03fd1462e22c65a1a27c4ecc2350a",
=======
        sha1 = "2af2926424cfd90b3113e675c8e4d0bb3e3d8024",
>>>>>>> BRANCH (08f5fd Upgrade elasticsearch-rest-client to 6.4.1)
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
