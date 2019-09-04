# Copyright (C) 2016 The Android Open Source Project
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

<<<<<<< HEAD   (29f62d Merge branch 'stable-2.14' into stable-2.15)
=======
load("@rules_java//java:defs.bzl", "java_library")
load("//tools/bzl:genrule2.bzl", "genrule2")

>>>>>>> BRANCH (5c07cb Merge branch 'stable-2.13' into stable-2.14)
def prolog_cafe_library(
        name,
        srcs,
        deps = [],
        **kwargs):
    native.genrule(
        name = name + "__pl2j",
        cmd = "$(location //lib/prolog:compiler-bin) " +
              "$$(dirname $@) $@ " +
              "$(SRCS)",
        srcs = srcs,
        tools = ["//lib/prolog:compiler-bin"],
        outs = [name + ".srcjar"],
    )
    java_library(
        name = name,
        srcs = [":" + name + "__pl2j"],
        deps = ["//lib/prolog:runtime-neverlink"] + deps,
        **kwargs
    )
