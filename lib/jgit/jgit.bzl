load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (282062 Merge branch 'stable-2.16' into stable-3.0)
_JGIT_VERS = "5.2.1.201812262042-r"
=======
_JGIT_VERS = "5.1.7.201904200442-r"
>>>>>>> BRANCH (4dab1b Merge branch 'stable-2.15' into stable-2.16)

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
<<<<<<< HEAD   (282062 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "34914e63e1463e40ba40e2e28b0392993ea3b938",
        src_sha1 = "b1c9e2ae01dd31ab4957de54756ec11acc99bb30",
=======
        sha1 = "ef1f744a1117e3d8916f3770486502a56d241b16",
        src_sha1 = "7bf5b5298f8936e959a92f62b5151d58feb9c00a",
>>>>>>> BRANCH (4dab1b Merge branch 'stable-2.15' into stable-2.16)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (282062 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "18c8938c4d8966abed84fc9de6c09aaea8cc8d87",
=======
        sha1 = "8306e221fb50985247138adb8ffb0f4cd74c5e9a",
>>>>>>> BRANCH (4dab1b Merge branch 'stable-2.15' into stable-2.16)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (282062 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "08c945bc664e4efe0d0e9a878f96505076da2ca9",
=======
        sha1 = "fefb13ab8331cc8b13a15b653b1e057de7d3e514",
>>>>>>> BRANCH (4dab1b Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (282062 Merge branch 'stable-2.16' into stable-3.0)
        sha1 = "5a5fb36517cb05ca51cbb1f00a520142dc83f793",
=======
        sha1 = "f73f1eacc38f8329d9453f1d0353e82404379a89",
>>>>>>> BRANCH (4dab1b Merge branch 'stable-2.15' into stable-2.16)
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
