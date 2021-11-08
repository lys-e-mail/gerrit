load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (ac4c30 Fix DynamicOptions to invoke listeners registered to BeanPar)
_JGIT_VERS = "5.3.9.202012012026-r"
=======
_JGIT_VERS = "5.1.16.202106041830-r"
>>>>>>> BRANCH (2a3ff3 Merge branch 'stable-2.15' into stable-2.16)

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
<<<<<<< HEAD   (ac4c30 Fix DynamicOptions to invoke listeners registered to BeanPar)
        sha1 = "b6d3af64d2538db1c25ee8cf9f2346fd8663321b",
=======
        sha1 = "1b32273b9b8326a14355374702799b6cd4a94050",
        src_sha1 = "62a010fe8e0de9c4684348f3853da3c817257165",
        unsign = True,
>>>>>>> BRANCH (2a3ff3 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (ac4c30 Fix DynamicOptions to invoke listeners registered to BeanPar)
        sha1 = "1f68350b98cbbd9a5219ad5827b3aa6c46a15dea",
=======
        sha1 = "f375f3c6cfe37096ee984d3a2f380817446ac5f0",
        unsign = True,
>>>>>>> BRANCH (2a3ff3 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (ac4c30 Fix DynamicOptions to invoke listeners registered to BeanPar)
        sha1 = "7a4bf3ac728274129acf8c13c11b66199808eb20",
=======
        sha1 = "20378bb3138a9ab8d2e13036a2f3e4719caad7a2",
>>>>>>> BRANCH (2a3ff3 Merge branch 'stable-2.15' into stable-2.16)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (ac4c30 Fix DynamicOptions to invoke listeners registered to BeanPar)
        sha1 = "dd8a2b2cd0b65ee00c260d1a1e7ed33aed951c69",
=======
        sha1 = "9563231cf411841a78f9bb703782b0f0a716d420",
        unsign = True,
>>>>>>> BRANCH (2a3ff3 Merge branch 'stable-2.15' into stable-2.16)
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
