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
j2objc
jimfs
jruby
<<<<<<< HEAD   (648147 Fix color in account dropdown)
lucene-analyzers-common
lucene-core
lucene-misc
lucene-queryparser
=======
log4j
>>>>>>> BRANCH (52f279 Update jgit to 60b81c5a9280e44fa48d533a61f915382b2b9ce2)
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
