load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (1b7889 Update git submodules)
_JGIT_VERS = "5.3.1.201904271842-r"
=======
_JGIT_VERS = "5.1.10.201908230655-r"
>>>>>>> BRANCH (dea581 Update git submodules)

_DOC_VERS = _JGIT_VERS  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "https://download.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

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
<<<<<<< HEAD   (1b7889 Update git submodules)
        sha1 = "dba85014483315fa426259bc1b8ccda9373a624b",
=======
        sha1 = "f31edd6f2fb29f71e99e032371620e133182b279",
        src_sha1 = "59007bae013106211ba6168e2c977baa2ec30045",
        unsign = True,
>>>>>>> BRANCH (dea581 Update git submodules)
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (1b7889 Update git submodules)
        sha1 = "3287341fca859340a00b51cb5dd3b78b8e532b39",
=======
        sha1 = "ae18143349233cb08968ed09457d2cbdef191e90",
        unsign = True,
>>>>>>> BRANCH (dea581 Update git submodules)
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (1b7889 Update git submodules)
        sha1 = "3585027e83fb44a5de2c10ae9ddbf976593bf080",
=======
        sha1 = "e2c57117cfec84ace6777365c345e200e41e8b3b",
>>>>>>> BRANCH (dea581 Update git submodules)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (1b7889 Update git submodules)
        sha1 = "3d9ba7e610d6ab5d08dcb1e4ba448b592a34de77",
=======
        sha1 = "dab29b14a9e61998667af0bcc3a9e5a67f58b115",
        unsign = True,
>>>>>>> BRANCH (dea581 Update git submodules)
    )

def jgit_dep(name):
    mapping = {
        "@jgit-archive//jar": "@jgit//org.eclipse.jgit.archive:jgit-archive",
        "@jgit-junit//jar": "@jgit//org.eclipse.jgit.junit:junit",
        "@jgit-lib//jar": "@jgit//org.eclipse.jgit:jgit",
        "@jgit-servlet//jar": "@jgit//org.eclipse.jgit.http.server:jgit-servlet",
    }

    if LOCAL_JGIT_REPO:
        return mapping[name]
    else:
        return name
