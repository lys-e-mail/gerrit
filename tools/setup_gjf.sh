#!/bin/bash
#
# Copyright (C) 2017 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -eu

# Keep this version in sync with dev-contributing.txt.
VERSION=${1:-1.7}

TAG_PREFIX=google-java-format-

case "$VERSION" in
1.7)
    SHA1="b6d34a51e579b08db7c624505bdf9af4397f1702"
    TAG_PREFIX="google-java-format-"
    ;;
1.22.0)
    SHA1="693d8fd04656886a2287cfe1d7a118c4697c3a57"
    TAG_PREFIX=v
    ;;
*)
    echo "unknown google-java-format version: $VERSION"
    exit 1
    ;;
esac

root="$(git rev-parse --show-toplevel)"
if [[ -z "$root" ]]; then
  echo "google-java-format setup requires a git working tree"
  exit 1
fi

dir="$root/tools/format"
mkdir -p "$dir"

name="google-java-format-$VERSION-all-deps.jar"
url="https://github.com/google/google-java-format/releases/download/$TAG_PREFIX$VERSION/$name"
"$root/tools/download_file.py" -o "$dir/$name" -u "$url" -v "$SHA1"

launcher="$dir/google-java-format-$VERSION"
cat > "$launcher" <<EOF
#!/bin/bash
#
# Copyright (C) 2017 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

function abs_script_dir_path {
    SOURCE=\${BASH_SOURCE[0]}
    while [ -h "\$SOURCE" ]; do
      DIR=\$( cd -P \$( dirname "\$SOURCE") && pwd )
      SOURCE=\$(readlink "\$SOURCE")
      [[ \$SOURCE != /* ]] && SOURCE="\$DIR/\$SOURCE"
    done
    DIR=\$( cd -P \$( dirname "\$SOURCE" ) && pwd )
    echo \$DIR
}

set -e

dir="\$(abs_script_dir_path "\$0")"
exec java -jar "\$dir/$name" "\$@"
EOF

chmod +x "$launcher"

cat <<EOF
Installed launcher script at $launcher
To set up an alias, add the following to your ~/.bashrc or equivalent:
  alias google-java-format='$launcher'
EOF
