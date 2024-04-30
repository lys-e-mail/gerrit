package com.google.gerrit.entities.converter;

import com.google.errorprone.annotations.Immutable;
import com.google.gerrit.common.ConvertibleToProto;
import com.google.protobuf.MessageLite;

/**
 * An extension to {@link ProtoConverter} that enforces the Entity class and the Proto class to stay
 * in sync. The enforcement is done by {@link SafeProtoConverterTest}.
 *
 * <p>Implementing classes must be: 1. Enums with a single enum. 2. Located under {@code
 * com.google.gerrit.entities}.
 *
 * <p>In addition, the Java entities must implement {@link ConvertibleToProto}.
 */
@Immutable
public interface SafeProtoConverter<P extends MessageLite, C extends ConvertibleToProto>
    extends ProtoConverter<P, C> {

  Class<P> getProtoClass();

  Class<C> getEntityClass();
}
