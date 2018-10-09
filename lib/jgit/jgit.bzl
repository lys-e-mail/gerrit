load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (4eac09 ValidationMessage: Remove unnecessary semicolon)
_JGIT_VERS = "5.1.2.201810061102-r"
=======
_JGIT_VERS = "4.9.6.201810051924-r"
>>>>>>> BRANCH (914a17 Merge branch 'stable-2.14' into stable-2.15)

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
<<<<<<< HEAD   (4eac09 ValidationMessage: Remove unnecessary semicolon)
        sha1 = "467c951f20aef345c584e1d578be691ac7ae6fbc",
        src_sha1 = "37a8b0233413af35886be512ebfcd499a439d455",
=======
        sha1 = "c322ac4e0bc11e512a30751890e6e2812617c17e",
        src_sha1 = "7bc25ac3fba5e4937717240d58ad7f99220ba39a",
>>>>>>> BRANCH (914a17 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (4eac09 ValidationMessage: Remove unnecessary semicolon)
        sha1 = "f8a7f7934b8038fe01f26a0908b648385dbc5ffe",
=======
        sha1 = "317486903d84501c70d7b6384c13e73c9ffa5a49",
>>>>>>> BRANCH (914a17 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (4eac09 ValidationMessage: Remove unnecessary semicolon)
        sha1 = "c51089a2e1f225f4b10e78e9bfc9c077a9337977",
=======
        sha1 = "333b1b5a347297e03974243ca325511f2ab0b088",
>>>>>>> BRANCH (914a17 Merge branch 'stable-2.14' into stable-2.15)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (4eac09 ValidationMessage: Remove unnecessary semicolon)
        sha1 = "afd35253f780ffb64281bcb3abfe24cceef78d2e",
=======
        sha1 = "274ef27f61b912c2a7a326ca6f7dceaa14297d94",
>>>>>>> BRANCH (914a17 Merge branch 'stable-2.14' into stable-2.15)
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
