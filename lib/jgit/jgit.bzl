load("//tools/bzl:maven_jar.bzl", "GERRIT", "MAVEN_CENTRAL", "MAVEN_LOCAL", "maven_jar")

<<<<<<< HEAD   (e81ce7 Revert "Simplify [ReceivePack]MessageSender logic")
_JGIT_VERS = "5.0.1.201806211838-r"
=======
_JGIT_VERS = "4.9.3.201807311005-r"
>>>>>>> BRANCH (665426 Merge branch 'stable-2.14' into stable-2.15)

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
    else:
        jgit_maven_repos()

def jgit_maven_repos():
    maven_jar(
        name = "jgit-lib",
        artifact = "org.eclipse.jgit:org.eclipse.jgit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e81ce7 Revert "Simplify [ReceivePack]MessageSender logic")
        sha1 = "dbba66a425d2153ccd749d0ba9c075b0ba424655",
        src_sha1 = "c85725a96e20d940fe20e1be4ddf50133c322f65",
=======
        sha1 = "b063719602ce9aaa058421e5beafb26b4950532b",
        src_sha1 = "c666721021b61465d3e140b8eef37b475c29eeb8",
>>>>>>> BRANCH (665426 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-servlet",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.http.server:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e81ce7 Revert "Simplify [ReceivePack]MessageSender logic")
        sha1 = "5d9cd43e880d49f14501ac48d59b55905f4ec5bf",
=======
        sha1 = "0b7408658db0067cdaebeb9c8dda6cadf639b84a",
>>>>>>> BRANCH (665426 Merge branch 'stable-2.14' into stable-2.15)
        unsign = True,
    )
    maven_jar(
        name = "jgit-archive",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.archive:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e81ce7 Revert "Simplify [ReceivePack]MessageSender logic")
        sha1 = "1d94e2bfa505dd719f62cfb036295022543af17e",
=======
        sha1 = "e86ef418c398a38dda59abfbdb21e014dc94fb18",
>>>>>>> BRANCH (665426 Merge branch 'stable-2.14' into stable-2.15)
    )
    maven_jar(
        name = "jgit-junit",
        artifact = "org.eclipse.jgit:org.eclipse.jgit.junit:" + _JGIT_VERS,
        repository = _JGIT_REPO,
<<<<<<< HEAD   (e81ce7 Revert "Simplify [ReceivePack]MessageSender logic")
        sha1 = "f848735061fab81f2863f68cca8d533ff403c765",
=======
        sha1 = "0a061070690a57a855fa5963b71f1f9995dbf8cb",
>>>>>>> BRANCH (665426 Merge branch 'stable-2.14' into stable-2.15)
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
