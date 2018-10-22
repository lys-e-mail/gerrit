load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (28d037 Upgrade JGit to 4.9.7.201810191756-r)
_JGIT_VERS = "4.9.7.201810191756-r"
=======
_JGIT_VERS = "4.7.6.201810191618-r"
>>>>>>> BRANCH (929025 Set version to 2.14.16)

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
<<<<<<< HEAD   (28d037 Upgrade JGit to 4.9.7.201810191756-r)
        sha1 = "fdb6c03608e701970338c0a659cffc6772642708",
        src_sha1 = "00923a3e9302d659fa7887cc8a019e1fa11b5dd2",
=======
        sha1 = "e65c01d8f3a30a413b613f1fa5f770bfa8b62c81",
        src_sha1 = "c837b9e774573afcb8a80ee318c2fbf1a3f29dd2",
>>>>>>> BRANCH (929025 Set version to 2.14.16)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (28d037 Upgrade JGit to 4.9.7.201810191756-r)
        sha1 = "8385c02bee53a8e189817bae2ea2529631ccc7a8",
=======
        sha1 = "e55ba5476474939aaf74c76f3aa9f377391ee043",
>>>>>>> BRANCH (929025 Set version to 2.14.16)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (28d037 Upgrade JGit to 4.9.7.201810191756-r)
        sha1 = "b8224b08c5c403ee635b8fa8378c27fbe6329620",
=======
        sha1 = "6d873357c44f217f59f52a70cd91fa30bd80e79a",
>>>>>>> BRANCH (929025 Set version to 2.14.16)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (28d037 Upgrade JGit to 4.9.7.201810191756-r)
        sha1 = "4084467ad58438bc819daf99de7244cfaa5a6fa1",
=======
        sha1 = "50932594f877a56f88d2a5ab4b19198a4dfd495a",
>>>>>>> BRANCH (929025 Set version to 2.14.16)
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
