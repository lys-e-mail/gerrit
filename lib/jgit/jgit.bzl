load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (632b5b Merge "Convert NoteDb migrator output into chars" into stabl)
_JGIT_VERS = "5.1.11.201909031202-r"
=======
_JGIT_VERS = "4.11.9.201909030838-r"
>>>>>>> BRANCH (89bfdb Upgrade JGit to 4.11.9.201909030838-r)

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
<<<<<<< HEAD   (632b5b Merge "Convert NoteDb migrator output into chars" into stabl)
        sha1 = "5aa0e29d7b4db4e6c17e3ddee9bdc8d578f02ef0",
        src_sha1 = "48ae1f24793a18c94d7b2e335ccdee6f16f8dd09",
=======
        sha1 = "3bc74ffed6186bf2fc37404216e5ef16f904d0b0",
        src_sha1 = "ddfdec70e78d145e7d99c8d72286cb714b0239ae",
>>>>>>> BRANCH (89bfdb Upgrade JGit to 4.11.9.201909030838-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (632b5b Merge "Convert NoteDb migrator output into chars" into stabl)
        sha1 = "2650749548a85adf53ffa7c334834edf3411d7c7",
=======
        sha1 = "a870e53f414992a548264758bdb17c74f6b79e19",
>>>>>>> BRANCH (89bfdb Upgrade JGit to 4.11.9.201909030838-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (632b5b Merge "Convert NoteDb migrator output into chars" into stabl)
        sha1 = "0eb22173603c141047c790e28f9d8a1df39b8067",
=======
        sha1 = "65bcf563c3155f9555735992f6493f3fe35b13a2",
>>>>>>> BRANCH (89bfdb Upgrade JGit to 4.11.9.201909030838-r)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (632b5b Merge "Convert NoteDb migrator output into chars" into stabl)
        sha1 = "35f4b77f8e8339da192120ee0b037944b94b4194",
=======
        sha1 = "c7a8883d5431149d1d3b9282eda3d79efffd319e",
>>>>>>> BRANCH (89bfdb Upgrade JGit to 4.11.9.201909030838-r)
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
