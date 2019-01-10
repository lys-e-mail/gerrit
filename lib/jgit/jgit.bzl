load("//tools/bzl:maven_jar.bzl", "ECLIPSE", "maven_jar")

<<<<<<< HEAD   (df74d0 Merge branch 'stable-2.15' into stable-2.16)
_JGIT_VERS = "5.1.3.201810200350-r"
=======
_JGIT_VERS = "4.9.8.201812241815-r"
>>>>>>> BRANCH (112f83 Set version to 2.15.8)

_DOC_VERS = _JGIT_VERS  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "http://download.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

_JGIT_REPO = ECLIPSE  # Leave here even if set to MAVEN_CENTRAL.

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
<<<<<<< HEAD   (df74d0 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "f270dbd1d792d5ad06074abe018a18644c90b60e",
        src_sha1 = "00e24ee2b721040edbb8520d705607a7f7bafd64",
=======
        sha1 = "dedb5d05a952551dc465611ebde3819d86bb22fc",
        src_sha1 = "dbbc82bb6b1f7733a3d03a5af67eb5b8cf30e2d4",
>>>>>>> BRANCH (112f83 Set version to 2.15.8)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (df74d0 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "360405244c28b537f0eafdc0b9d9f3753503d981",
=======
        sha1 = "76fc4dab1d3b0c05768d7d5424e003e4dbfa5222",
>>>>>>> BRANCH (112f83 Set version to 2.15.8)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (df74d0 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "08e10921fcc75ead2736dd5bf099ba8e2ed8a3fb",
=======
        sha1 = "dfebb3889afcd4f335e9becf4bb58a06bb84b72f",
>>>>>>> BRANCH (112f83 Set version to 2.15.8)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (df74d0 Merge branch 'stable-2.15' into stable-2.16)
        sha1 = "1dc8f86bba3c461cb90c9dc3e91bf343889ca684",
=======
        sha1 = "3e34e327979c8c263765be63abc89dda1b4b4d94",
>>>>>>> BRANCH (112f83 Set version to 2.15.8)
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
