load("//tools/bzl:maven_jar.bzl", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (32470f Elasticsearch: Update elasticsearch-rest-client to 7.0.0)
_JGIT_VERS = "4.9.9.201903122025-r"
=======
_JGIT_VERS = "4.7.9.201904161809-r"
>>>>>>> BRANCH (53eb53 Upgrade JGit to 4.7.9.201904161809-r)

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
<<<<<<< HEAD   (32470f Elasticsearch: Update elasticsearch-rest-client to 7.0.0)
        sha1 = "52193ccd43992cfa262d72db9da1b7512e0858da",
        src_sha1 = "fb5f9b0630d8016061a05666d9adee8b1f1f4b16",
=======
        sha1 = "14fb9628876e69d1921776c84c7343ddabe7db31",
        src_sha1 = "6717cab511548f01f07db2442d104ba901402d49",
>>>>>>> BRANCH (53eb53 Upgrade JGit to 4.7.9.201904161809-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (32470f Elasticsearch: Update elasticsearch-rest-client to 7.0.0)
        sha1 = "37e45177c96a2a936e783dd3b850edf1676fa1fa",
=======
        sha1 = "4b9006c68e257e4397a34a6022c6729c657129d8",
>>>>>>> BRANCH (53eb53 Upgrade JGit to 4.7.9.201904161809-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (32470f Elasticsearch: Update elasticsearch-rest-client to 7.0.0)
        sha1 = "57be131434f94b5e3446ba00a11a99cab806eaaa",
=======
        sha1 = "41fb617b0d51afb2f6c1345e8ef57f3caece790a",
>>>>>>> BRANCH (53eb53 Upgrade JGit to 4.7.9.201904161809-r)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (32470f Elasticsearch: Update elasticsearch-rest-client to 7.0.0)
        sha1 = "80e5eb69c35bafec0e74285ca2058e5212d43266",
=======
        sha1 = "10ad697deb13a90b957e462589fb92a5cf371909",
>>>>>>> BRANCH (53eb53 Upgrade JGit to 4.7.9.201904161809-r)
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
