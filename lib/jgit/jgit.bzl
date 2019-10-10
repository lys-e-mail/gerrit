load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (0ac9b0 Merge branch 'stable-2.16' into stable-3.0)
_JGIT_VERS = "5.3.5.201909031855-r"
=======
_JGIT_VERS = "5.1.12.201910011832-r"
>>>>>>> BRANCH (8273f5 Merge branch 'stable-2.15' into stable-2.16)

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
<<<<<<< HEAD   (0ac9b0 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "f5e087859c805b7dc9c544714e9970f534791cf6",
=======
        sha1 = "62c60aa985aa8dcfa6ad7308d130c319a1d01073",
        src_sha1 = "6260138710f82d9084ab55e97aba8981e63c6ac2",
        unsign = True,
>>>>>>> BRANCH (8273f5 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (0ac9b0 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "25577326355c0ecd951f9987607606de6af089be",
=======
        sha1 = "7e32173b3d1958627950f1b675e5a3f1f35e9b53",
        unsign = True,
>>>>>>> BRANCH (8273f5 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (0ac9b0 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "7ec97af1ffab2c2466570d7566e6748d1e48d7e8",
=======
        sha1 = "c86ea9a5771bc966a3b0e4bba4c7113e5423c0f4",
>>>>>>> BRANCH (8273f5 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (0ac9b0 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "f92c31856a700237be0ecfb4d5044fa0ca4671dc",
=======
        sha1 = "2d957b5b515f9510792659277b5df049b6e795d8",
        unsign = True,
>>>>>>> BRANCH (8273f5 Merge branch 'stable-2.15' into stable-2.16)
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
