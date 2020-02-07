load(
    "@bazel_tools//tools/jdk:default_java_toolchain.bzl",
    "JDK9_JVM_OPTS",
    "default_java_toolchain",
)
load("@rules_java//java:defs.bzl", "java_package_configuration")

exports_files(["nongoogle.bzl"])

default_java_toolchain(
    name = "error_prone_warnings_toolchain",
    bootclasspath = ["@bazel_tools//tools/jdk:platformclasspath.jar"],
    jvm_opts = JDK9_JVM_OPTS,
    package_configuration = [
        ":error_prone",
    ],
    visibility = ["//visibility:public"],
)

# Error Prone errors enabled by default; see ../.bazelrc for how this is
# enabled. This warnings list is originally based on:
# https://github.com/bazelbuild/BUILD_file_generator/blob/master/tools/bazel_defs/java.bzl
# However, feel free to add any additional errors. Thus far they have all been pretty useful.
# TODO(davido): Enable ImmutableAnnotationChecker again when these issues are fixed:
# https://github.com/google/error-prone/issues/1348
# https://github.com/bazelbuild/bazel/issues/9378
java_package_configuration(
    name = "error_prone",
    javacopts = [
        "-XepDisableWarningsInGeneratedCode",
<<<<<<< HEAD   (eaef51 Set version to 3.1.4-SNAPSHOT)
        "-Xep:AmbiguousMethodReference:ERROR",
        "-Xep:AutoValueFinalMethods:ERROR",
        "-Xep:BadAnnotationImplementation:ERROR",
        "-Xep:BadComparable:ERROR",
=======
        "-Xep:AmbiguousMethodReference:WARN",
        "-Xep:BadAnnotationImplementation:WARN",
        "-Xep:BadComparable:WARN",
>>>>>>> BRANCH (a58ffc Merge branch 'stable-2.16' into stable-3.0)
        "-Xep:BoxedPrimitiveConstructor:ERROR",
        "-Xep:CannotMockFinalClass:ERROR",
        "-Xep:ClassCanBeStatic:ERROR",
        "-Xep:ClassNewInstance:ERROR",
        "-Xep:DateFormatConstant:ERROR",
        "-Xep:DefaultCharset:ERROR",
        "-Xep:DoubleCheckedLocking:ERROR",
        "-Xep:ElementsCountedInLoop:ERROR",
        "-Xep:EqualsHashCode:ERROR",
        "-Xep:EqualsIncompatibleType:ERROR",
        "-Xep:ExpectedExceptionChecker:ERROR",
<<<<<<< HEAD   (eaef51 Set version to 3.1.4-SNAPSHOT)
        "-Xep:Finally:ERROR",
        "-Xep:FloatingPointLiteralPrecision:ERROR",
        "-Xep:FragmentInjection:ERROR",
        "-Xep:FragmentNotInstantiable:ERROR",
        "-Xep:FunctionalInterfaceClash:ERROR",
        "-Xep:FutureReturnValueIgnored:ERROR",
        "-Xep:GetClassOnEnum:ERROR",
        "-Xep:ImmutableAnnotationChecker:OFF",
        "-Xep:ImmutableEnumChecker:ERROR",
        "-Xep:IncompatibleModifiers:ERROR",
        "-Xep:InjectOnConstructorOfAbstractClass:ERROR",
        "-Xep:InputStreamSlowMultibyteRead:ERROR",
        "-Xep:IterableAndIterator:ERROR",
        "-Xep:JUnit3FloatingPointComparisonWithoutDelta:ERROR",
        "-Xep:JUnitAmbiguousTestClass:ERROR",
        "-Xep:LiteralClassName:ERROR",
=======
        "-Xep:Finally:WARN",
        "-Xep:FloatingPointLiteralPrecision:WARN",
        "-Xep:FragmentInjection:WARN",
        "-Xep:FragmentNotInstantiable:WARN",
        "-Xep:FunctionalInterfaceClash:WARN",
        "-Xep:FutureReturnValueIgnored:WARN",
        "-Xep:GetClassOnEnum:WARN",
        "-Xep:ImmutableAnnotationChecker:WARN",
        "-Xep:ImmutableEnumChecker:WARN",
        "-Xep:IncompatibleModifiers:WARN",
        "-Xep:InjectOnConstructorOfAbstractClass:WARN",
        "-Xep:InputStreamSlowMultibyteRead:WARN",
        "-Xep:IterableAndIterator:WARN",
        "-Xep:JUnit3FloatingPointComparisonWithoutDelta:WARN",
        "-Xep:JUnitAmbiguousTestClass:WARN",
        "-Xep:LiteralClassName:WARN",
>>>>>>> BRANCH (a58ffc Merge branch 'stable-2.16' into stable-3.0)
        "-Xep:MissingCasesInEnumSwitch:ERROR",
        "-Xep:MissingFail:ERROR",
        "-Xep:MissingOverride:ERROR",
        "-Xep:MutableConstantField:ERROR",
<<<<<<< HEAD   (eaef51 Set version to 3.1.4-SNAPSHOT)
        "-Xep:NarrowingCompoundAssignment:ERROR",
        "-Xep:NonAtomicVolatileUpdate:ERROR",
        "-Xep:NonOverridingEquals:ERROR",
        "-Xep:NullableConstructor:ERROR",
        "-Xep:NullablePrimitive:ERROR",
        "-Xep:NullableVoid:ERROR",
        "-Xep:OperatorPrecedence:ERROR",
        "-Xep:OverridesGuiceInjectableMethod:ERROR",
        "-Xep:PreconditionsInvalidPlaceholder:ERROR",
        "-Xep:ProtoFieldPreconditionsCheckNotNull:ERROR",
        "-Xep:ProtocolBufferOrdinal:ERROR",
        "-Xep:ReferenceEquality:ERROR",
        "-Xep:RequiredModifiers:ERROR",
        "-Xep:ShortCircuitBoolean:ERROR",
        "-Xep:SimpleDateFormatConstant:ERROR",
        "-Xep:StaticGuardedByInstance:ERROR",
        "-Xep:StringEquality:ERROR",
        "-Xep:SynchronizeOnNonFinalField:ERROR",
        "-Xep:TruthConstantAsserts:ERROR",
        "-Xep:TypeParameterShadowing:ERROR",
        "-Xep:TypeParameterUnusedInFormals:ERROR",
        "-Xep:URLEqualsHashCode:ERROR",
=======
        "-Xep:NarrowingCompoundAssignment:WARN",
        "-Xep:NonAtomicVolatileUpdate:WARN",
        "-Xep:NonOverridingEquals:WARN",
        "-Xep:NullableConstructor:WARN",
        "-Xep:NullablePrimitive:WARN",
        "-Xep:NullableVoid:WARN",
        "-Xep:OperatorPrecedence:WARN",
        "-Xep:OverridesGuiceInjectableMethod:WARN",
        "-Xep:PreconditionsInvalidPlaceholder:WARN",
        "-Xep:ProtoFieldPreconditionsCheckNotNull:WARN",
        "-Xep:ProtocolBufferOrdinal:WARN",
        "-Xep:ReferenceEquality:WARN",
        "-Xep:RequiredModifiers:WARN",
        "-Xep:ShortCircuitBoolean:WARN",
        "-Xep:SimpleDateFormatConstant:WARN",
        "-Xep:StaticGuardedByInstance:WARN",
        "-Xep:StringEquality:WARN",
        "-Xep:SynchronizeOnNonFinalField:WARN",
        "-Xep:TruthConstantAsserts:WARN",
        "-Xep:TypeParameterShadowing:WARN",
        "-Xep:TypeParameterUnusedInFormals:WARN",
        "-Xep:URLEqualsHashCode:WARN",
        "-Xep:UnsynchronizedOverridesSynchronized:WARN",
>>>>>>> BRANCH (a58ffc Merge branch 'stable-2.16' into stable-3.0)
        "-Xep:UnusedException:ERROR",
<<<<<<< HEAD   (eaef51 Set version to 3.1.4-SNAPSHOT)
        "-Xep:UnsynchronizedOverridesSynchronized:ERROR",
        "-Xep:WaitNotInLoop:ERROR",
        "-Xep:WildcardImport:ERROR",
=======
        "-Xep:WaitNotInLoop:WARN",
        "-Xep:WildcardImport:WARN",
>>>>>>> BRANCH (a58ffc Merge branch 'stable-2.16' into stable-3.0)
    ],
    packages = ["error_prone_packages"],
)

package_group(
    name = "error_prone_packages",
    packages = [
        "//java/...",
        "//javatests/...",
        "//plugins/codemirror-editor/...",
        "//plugins/commit-message-length-validator/...",
        "//plugins/delete-project/...",
        "//plugins/download-commands/...",
        "//plugins/gitiles/...",
        "//plugins/hooks/...",
        "//plugins/plugin-manager/...",
        "//plugins/replication/...",
        "//plugins/reviewnotes/...",
        "//plugins/singleusergroup/...",
        "//plugins/webhooks/...",
    ],
)
