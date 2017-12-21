load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_LOCAL", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (7f92f9 Update JGit to 4.9.2.201712150930-r.175-gd8a24ac1c)
_JGIT_VERS = "4.9.2.201712150930-r.175-gd8a24ac1c"
=======
_JGIT_VERS = "4.9.2.201712150930-r.4-g085d1f959"
>>>>>>> BRANCH (6979cc NoteDbMigrator: Set checkExisting(false) on PackInserter)

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
<<<<<<< HEAD   (7f92f9 Update JGit to 4.9.2.201712150930-r.175-gd8a24ac1c)
        sha1 = "4286555f5851fbfcf0ff89ec884f7f806b0c7e37",
        src_sha1 = "5e38b7e7936ebbd778914dc4f9d76d245a5a4518",
=======
        sha1 = "1b9090699d03f57b2f3e682dd4a87ddf6bf460ac",
        src_sha1 = "46ed37a4a385e7dc3fb65ccf165e5f854698186f",
>>>>>>> BRANCH (6979cc NoteDbMigrator: Set checkExisting(false) on PackInserter)
        unsign = True,
    )
    maven_jar(
        name = "jgit_servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (7f92f9 Update JGit to 4.9.2.201712150930-r.175-gd8a24ac1c)
        sha1 = "da0d2c7a048cc213274cd06a5baf277c85ea152e",
=======
        sha1 = "27c490cc62a6efd4d4bc10789e8e5945fd01458b",
>>>>>>> BRANCH (6979cc NoteDbMigrator: Set checkExisting(false) on PackInserter)
        unsign = True,
    )
    maven_jar(
        name = "jgit_archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (7f92f9 Update JGit to 4.9.2.201712150930-r.175-gd8a24ac1c)
        sha1 = "1cd91bedf8b591626d341c2d896181ddba5f9aa9",
=======
        sha1 = "bff16e9ae593808fd8b77f8917fb44c73d2eb2f3",
>>>>>>> BRANCH (6979cc NoteDbMigrator: Set checkExisting(false) on PackInserter)
    )
    maven_jar(
        name = "jgit_junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (7f92f9 Update JGit to 4.9.2.201712150930-r.175-gd8a24ac1c)
        sha1 = "5b7cc1aa0ba062ad587b6daa64743b704b997f74",
=======
        sha1 = "87f5f48bf2efa11726e2751f316f49c8f2429ee8",
>>>>>>> BRANCH (6979cc NoteDbMigrator: Set checkExisting(false) on PackInserter)
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
