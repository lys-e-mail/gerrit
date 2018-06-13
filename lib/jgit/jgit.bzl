load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_LOCAL", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
_JGIT_VERS = "4.11.0.201803080745-r.93-gcbb2e65db"
=======
_JGIT_VERS = "4.9.2.201712150930-r.15-g5fe8e31d4"
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)

_DOC_VERS = "4.11.0.201803080745-r"  # Set to _JGIT_VERS unless using a snapshot

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
        name = "jgit-lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
        sha1 = "265a39c017ecfeed7e992b6aaa336e515bf6e157",
        src_sha1 = "e9d801e17afe71cdd5ade84ab41ff0110c3f28fd",
=======
        sha1 = "dd93e272fb38c4a0e2b9e1cc39424e1e8d542352",
        src_sha1 = "78d34eb21320b8262a1b52bd5c91e66380d0302a",
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
        sha1 = "0d68f62286b5db759fdbeb122c789db1f833a06a",
=======
        sha1 = "5ef78cf1da610c643a9fd03763b4cac2889567af",
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
        sha1 = "4cc3ed2c42ee63593fd1b16215fcf13eeefb833e",
=======
        sha1 = "238e20f82cb7b5ad99b60445d36f6c2c13ed8e4e",
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e1359a Bazel: Harmonize names of external repositories)
        sha1 = "6f1bcc9ac22b31b5a6e1e68c08283850108b900c",
=======
        sha1 = "e16e9635020e8a4a62d93cd05869442690f54209",
>>>>>>> BRANCH (5271b3 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )

def jgit_dep(name):
  mapping = {
      "@jgit-junit//jar": "@jgit//org.eclipse.jgit.junit:junit",
      "@jgit-lib//jar:src": "@jgit//org.eclipse.jgit:libjgit-src.jar",
      "@jgit-lib//jar": "@jgit//org.eclipse.jgit:jgit",
      "@jgit-servlet//jar":"@jgit//org.eclipse.jgit.http.server:jgit-servlet",
      "@jgit-archive//jar": "@jgit//org.eclipse.jgit.archive:jgit-archive",
  }

  if LOCAL_JGIT_REPO:
    return mapping[name]
  else:
    return name
