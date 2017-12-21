load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_LOCAL", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (eeef2f Merge "ReviewersUtil: Only load ID from account index")
_JGIT_VERS = "4.9.2.201712150930-r.171-gfdbaa25db"
=======
_JGIT_VERS = "4.9.2.201712150930-r.3-g43ef5dabf"
>>>>>>> BRANCH (3296b9 Merge "PolyGerrit: Add background-repeat to gr-main-header" )

_DOC_VERS = "4.9.2.201712150930-r"  # Set to _JGIT_VERS unless using a snapshot

JGIT_DOC_URL = "http://download.eclipse.org/jgit/site/" + _DOC_VERS + "/apidocs"

_JGIT_REPO = GERRIT  # Leave here even if set to MAVEN_CENTRAL.

# set this to use a local version.
# "/home/<user>/projects/jgit"
LOCAL_JGIT_REPO = ""

def jgit_repos():
  if LOCAL_JGIT_REPO:
    native.local_repository(
        name = "jgit",
        path = LOCAL_JGIT_REPO,
    )
  else:
    jgit_maven_repos()

def jgit_maven_repos():
    maven_jar(
        name = "jgit_lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (eeef2f Merge "ReviewersUtil: Only load ID from account index")
        sha1 = "29b822410b29286a09df728f8379e5cb8b1a486e",
        src_sha1 = "5106b81910a057470cfd2584d9cb3502bcbebbc2",
=======
        sha1 = "3f6a1002069be91d99e1b356193aac5bbe5b3da3",
        src_sha1 = "4fbbcd1e2f474917dd0ddbfef2580f474daf4dbd",
>>>>>>> BRANCH (3296b9 Merge "PolyGerrit: Add background-repeat to gr-main-header" )
        unsign = True,
    )
    maven_jar(
        name = "jgit_servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (eeef2f Merge "ReviewersUtil: Only load ID from account index")
        sha1 = "01f6718f6b629e28caad38e00190811b38574e74",
=======
        sha1 = "78425749a618dd82da8dcf19ef9fd14e4318315b",
>>>>>>> BRANCH (3296b9 Merge "PolyGerrit: Add background-repeat to gr-main-header" )
        unsign = True,
    )
    maven_jar(
        name = "jgit_archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (eeef2f Merge "ReviewersUtil: Only load ID from account index")
        sha1 = "9c9e9332e7dc724dbe1837e21feccd98bc25e6b4",
=======
        sha1 = "884933af30be5c64187838e43764e0e19309f850",
>>>>>>> BRANCH (3296b9 Merge "PolyGerrit: Add background-repeat to gr-main-header" )
    )
    maven_jar(
        name = "jgit_junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (eeef2f Merge "ReviewersUtil: Only load ID from account index")
        sha1 = "4154c70b78b62035dad446332b24f7816b7a2a1b",
=======
        sha1 = "d7c24fec0a23842a03a6eea592a07fbd1448e783",
>>>>>>> BRANCH (3296b9 Merge "PolyGerrit: Add background-repeat to gr-main-header" )
        unsign = True,
    )

def jgit_dep(name):
  mapping = {
      "@jgit_junit//jar": "@jgit//org.eclipse.jgit.junit:junit",
      "@jgit_lib//jar:src": "@jgit//org.eclipse.jgit:libjgit-src.jar",
      "@jgit_lib//jar": "@jgit//org.eclipse.jgit:jgit",
      "@jgit_servlet//jar":"@jgit//org.eclipse.jgit.http.server:jgit-servlet",
      "@jgit_archive//jar": "@jgit//org.eclipse.jgit.archive:jgit-archive",
  }

  if LOCAL_JGIT_REPO:
    return mapping[name]
  else:
    return name
