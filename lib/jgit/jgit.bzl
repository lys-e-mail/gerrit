load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (c2114a ProjectTagsScreen: Base visibility on the create refs/tags/*)
_JGIT_VERS = "4.9.4.201809090327-r"
=======
_JGIT_VERS = "4.7.3.201809090215-r"
>>>>>>> BRANCH (e46fd3 Upgrade JGit to 4.7.3.201809090215-r)

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
<<<<<<< HEAD   (c2114a ProjectTagsScreen: Base visibility on the create refs/tags/*)
        sha1 = "a04cb44514344619bb9cef3db9323298cfda78fd",
        src_sha1 = "e8cf23009f5ef776fcbb6d18cddf455cf0e956f6",
=======
        sha1 = "81b7a1a7484ce0519298e388cab97082e2d20c97",
        src_sha1 = "5e14588b33defc5da2f0179c7c7b42343d8836e0",
>>>>>>> BRANCH (e46fd3 Upgrade JGit to 4.7.3.201809090215-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (c2114a ProjectTagsScreen: Base visibility on the create refs/tags/*)
        sha1 = "a7e14f47af07b74d72841498e4d24dbafc2c0026",
=======
        sha1 = "8113a2bd4b426e12eda75a0f79438a58775feaab",
>>>>>>> BRANCH (e46fd3 Upgrade JGit to 4.7.3.201809090215-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (c2114a ProjectTagsScreen: Base visibility on the create refs/tags/*)
        sha1 = "006dbb35cc0be258929f5a72814e4d47ba61e084",
=======
        sha1 = "14fe9c4f2bbfb78e66e3524a2ee6a86336ed957d",
>>>>>>> BRANCH (e46fd3 Upgrade JGit to 4.7.3.201809090215-r)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (c2114a ProjectTagsScreen: Base visibility on the create refs/tags/*)
        sha1 = "855cc13b23772151894966d88b1def7544955b0a",
=======
        sha1 = "d17e6e24dc7b8cbbf8a95235f51cc47bb8669519",
>>>>>>> BRANCH (e46fd3 Upgrade JGit to 4.7.3.201809090215-r)
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
