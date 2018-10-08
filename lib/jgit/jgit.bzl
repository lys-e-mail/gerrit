load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (6c0282 Merge "Add support for "max_object_size_limit" effective val)
_JGIT_VERS = "4.9.6.201810051924-r"
=======
_JGIT_VERS = "4.7.5.201810051826-r"
>>>>>>> BRANCH (527e01 Update JGit to 4.7.5.201810051826-r)

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
<<<<<<< HEAD   (6c0282 Merge "Add support for "max_object_size_limit" effective val)
        sha1 = "c322ac4e0bc11e512a30751890e6e2812617c17e",
        src_sha1 = "7bc25ac3fba5e4937717240d58ad7f99220ba39a",
=======
        sha1 = "60474c582755fb992c9167cd516851de7c0b8a1c",
        src_sha1 = "2a91de9ca6f844c3f5c355fe800f9de572488e12",
>>>>>>> BRANCH (527e01 Update JGit to 4.7.5.201810051826-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (6c0282 Merge "Add support for "max_object_size_limit" effective val)
        sha1 = "317486903d84501c70d7b6384c13e73c9ffa5a49",
=======
        sha1 = "2f167221e507ce4614004361e0cdaca559deb204",
>>>>>>> BRANCH (527e01 Update JGit to 4.7.5.201810051826-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (6c0282 Merge "Add support for "max_object_size_limit" effective val)
        sha1 = "333b1b5a347297e03974243ca325511f2ab0b088",
=======
        sha1 = "714612fdf6e22f05abaeb1935f01506c7f4f42b6",
>>>>>>> BRANCH (527e01 Update JGit to 4.7.5.201810051826-r)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (6c0282 Merge "Add support for "max_object_size_limit" effective val)
        sha1 = "274ef27f61b912c2a7a326ca6f7dceaa14297d94",
=======
        sha1 = "915256429c59e91a4627d954e7ca477d9c2da112",
>>>>>>> BRANCH (527e01 Update JGit to 4.7.5.201810051826-r)
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
