load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_LOCAL", "MAVEN_CENTRAL", "maven_jar")

<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
_JGIT_VERS = "4.9.1.201712030800-r.66-gf8eff40ca"
=======
_JGIT_VERS = "4.9.1.201712030800-r"
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)

<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
_DOC_VERS = "4.9.1.201712030800-r"  # Set to _JGIT_VERS unless using a snapshot
=======
_DOC_VERS = _JGIT_VERS  # Set to _JGIT_VERS unless using a snapshot
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)

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
  else:
    jgit_maven_repos()

def jgit_maven_repos():
    maven_jar(
        name = "jgit_lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
        sha1 = "0b974aa9c6c929c39c506ab2705d42f3d7da84c7",
        src_sha1 = "8884bef0415e092563b60b2167adbb09ac19d131",
=======
        sha1 = "e8ab33771a89682e80596854a6739ab8889ecdeb",
        src_sha1 = "f2cdb9f8f87a37b5611c0750db2bc570736533da",
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit_servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
        sha1 = "19c6bcdf5e0ba1907f6eeb18ae02d6ae04f630e3",
=======
        sha1 = "6ecb5bd14f45fe07995074613c9255a3447623b2",
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)
        unsign = True,
    )
    maven_jar(
        name = "jgit_archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
        sha1 = "0063dde3c017e05ee4e84ae16c97cb8817b91782",
=======
        sha1 = "87002ceb9f127e1220f854b12a53b9639404b926",
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)
    )
    maven_jar(
        name = "jgit_junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (f3bea1 Upgrade JGit to 4.9.1.201712030800-r.66-gf8eff40ca)
        sha1 = "a9cb1e58df9bd876a2e81130f61a9bac0f182520",
=======
        sha1 = "89a9789155dc44200a15adc3544069b6cd7196a4",
>>>>>>> BRANCH (68a8f3 Update JGit to 4.9.1.201712030800-r)
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
