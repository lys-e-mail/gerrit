load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (8b5ee5 Upgrade JGit to 5.4.3.201909031940-r)
_JGIT_VERS = "5.4.3.201909031940-r"
=======
_JGIT_VERS = "5.3.5.201909031855-r"
>>>>>>> BRANCH (e46d5a Merge branch 'stable-2.16' into stable-3.0)

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
<<<<<<< HEAD   (8b5ee5 Upgrade JGit to 5.4.3.201909031940-r)
        sha1 = "10322c4e103485f8b4873cbbf982342f9c3d7989",
=======
        sha1 = "f5e087859c805b7dc9c544714e9970f534791cf6",
>>>>>>> BRANCH (e46d5a Merge branch 'stable-2.16' into stable-3.0)
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (8b5ee5 Upgrade JGit to 5.4.3.201909031940-r)
        sha1 = "59d0c943343f30612e4e2a5a3bf1b95b56e00207",
=======
        sha1 = "25577326355c0ecd951f9987607606de6af089be",
>>>>>>> BRANCH (e46d5a Merge branch 'stable-2.16' into stable-3.0)
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (8b5ee5 Upgrade JGit to 5.4.3.201909031940-r)
        sha1 = "21dc4a10882dc667c83bf82a563a6fc4d7719456",
=======
        sha1 = "7ec97af1ffab2c2466570d7566e6748d1e48d7e8",
>>>>>>> BRANCH (e46d5a Merge branch 'stable-2.16' into stable-3.0)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (8b5ee5 Upgrade JGit to 5.4.3.201909031940-r)
        sha1 = "71659fc1a1729b7c67846dac8cd6a762fa72002a",
=======
        sha1 = "f92c31856a700237be0ecfb4d5044fa0ca4671dc",
>>>>>>> BRANCH (e46d5a Merge branch 'stable-2.16' into stable-3.0)
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
