package com.google.gerrit.common;

/**
 * An interface used to mark Java entities that have equivalent proto representations.
 *
 * <p>Ideally, this would be an annotation. However, java generics do not support {@code
 * annotatedBy} equivalent for {@code extends}, which is the main programmatic usage of this
 * interface.
 */
public interface ConvertibleToProto {}
