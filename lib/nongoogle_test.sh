#!/bin/sh

# This test ensures that new dependencies in nongoogle.bzl go through LC review.

set -eux

bzl=$(pwd)/tools/nongoogle.bzl

TMP=$(mktemp -d || mktemp -d -t /tmp/tmp.XXXXXX)

grep 'name = "[^"]*"' ${bzl} | sed 's|^[^"]*"||g;s|".*$||g' | sort > $TMP/names

cat << EOF > $TMP/want
backward-codecs
cglib-3_2
commons-io
dropwizard-core
eddsa
flogger
flogger-log4j-backend
flogger-system-backend
guava
guice-assistedinject
guice-library
guice-servlet
<<<<<<< HEAD   (b83a15 Change visibility: prefer test instead of check)
=======
httpasyncclient
httpcore-nio
impl-log4j
>>>>>>> BRANCH (19a6c8 Set version to 3.4.5-SNAPSHOT)
j2objc
<<<<<<< HEAD   (b83a15 Change visibility: prefer test instead of check)
=======
jackson-annotations
jackson-core
jcl-over-slf4j
>>>>>>> BRANCH (19a6c8 Set version to 3.4.5-SNAPSHOT)
jimfs
jruby
log-api
log-ext
log4j
lucene-analyzers-common
lucene-core
lucene-misc
lucene-queryparser
mina-core
nekohtml
objenesis
openid-consumer
soy
sshd-mina
sshd-osgi
sshd-sftp
truth
truth-java8-extension
truth-liteproto-extension
truth-proto-extension
tukaani-xz
xerces
EOF

diff -u $TMP/names $TMP/want
