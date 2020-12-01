load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (87e16c Merge branch 'stable-2.16' into stable-3.0)
_JGIT_VERS = "5.3.8.202011260953-r"
=======
_JGIT_VERS = "5.1.14.202011251942-r"
>>>>>>> BRANCH (ac1f09 Upgrade JGit to 5.1.14.202011251942-r)

_DOC_VERS = _JGIT_VERS  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "https://archive.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

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
<<<<<<< HEAD   (87e16c Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "f34c7c9e0ffaf8ba9e5af00e299e51f70931a833",
=======
        sha1 = "f962f10e9aac2c476eac02e6d37cf8ee9a101958",
        src_sha1 = "afa2ff384db5b4a3dc75b0ef2f127d5ef474d635",
        unsign = True,
>>>>>>> BRANCH (ac1f09 Upgrade JGit to 5.1.14.202011251942-r)
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (87e16c Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "f9517712c741660cd199311a4eb27584dd8d03f6",
=======
        sha1 = "b7c6e973495c75f2de8f837fac213c6b1d9a9860",
        unsign = True,
>>>>>>> BRANCH (ac1f09 Upgrade JGit to 5.1.14.202011251942-r)
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (87e16c Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "9adac724af047dfaa1e9061eeb34fbb14ebaefa9",
=======
        sha1 = "4331d91515347e416d914a90299f6d6070599efc",
>>>>>>> BRANCH (ac1f09 Upgrade JGit to 5.1.14.202011251942-r)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (87e16c Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "1c2dc9a93b30e3a6fb1fadb589f01f29343ec7d8",
=======
        sha1 = "5fd2a105454dd8b4834a37667ec12111d691167a",
        unsign = True,
>>>>>>> BRANCH (ac1f09 Upgrade JGit to 5.1.14.202011251942-r)
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
