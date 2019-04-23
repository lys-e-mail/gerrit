load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (bcef27 Merge branch 'stable-2.15' into stable-2.16)
_JGIT_VERS = "5.1.6.201903130242-r"
=======
_JGIT_VERS = "4.9.10.201904181027-r"
>>>>>>> BRANCH (b713d3 Set version to 2.15.13)

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
<<<<<<< HEAD   (bcef27 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "c33db190745a8a40524d3076f033ba2ee4616a64",
        src_sha1 = "f0e33171d3d413c5a94f07794aef7b71a35772f6",
=======
        sha1 = "ef0efa372dcbeceaad86fe7a9e97303971cfea85",
        src_sha1 = "d323476bc3551edb07aa6bea623c9aa78e1260ea",
>>>>>>> BRANCH (b713d3 Set version to 2.15.13)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (bcef27 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "86564c102e81a76f67630d4fde862d137e24c63a",
=======
        sha1 = "a03c6602661eb178ad0b98b8e702cecf231c5716",
>>>>>>> BRANCH (b713d3 Set version to 2.15.13)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (bcef27 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "f4ebfa91f4c988f37648b3087830a83973a975be",
=======
        sha1 = "5745d6edb236dd6eb598339000559375b1c94067",
>>>>>>> BRANCH (b713d3 Set version to 2.15.13)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (bcef27 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "b2e512e920afbac405a678efe92e3b4bacb9a1df",
=======
        sha1 = "93f8a494e6730034946c5e19ce90bdefd636af83",
>>>>>>> BRANCH (b713d3 Set version to 2.15.13)
        unsign = True,
    )

def jgit_dep(name):
    mapping = {
        "@jgit-archive//jar": "@jgit//org.eclipse.jgit.archive:jgit-archive",
        "@jgit-junit//jar": "@jgit//org.eclipse.jgit.junit:junit",
        "@jgit-lib//jar": "@jgit//org.eclipse.jgit:jgit",
        "@jgit-lib//jar:src": "@jgit//org.eclipse.jgit:libjgit-src.jar",
        "@jgit-servlet//jar": "@jgit//org.eclipse.jgit.http.server:jgit-servlet",
    }

    if LOCAL_JGIT_REPO:
        return mapping[name]
    else:
        return name
