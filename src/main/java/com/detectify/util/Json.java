package com.detectify.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by ahenriquez on 10/26/16.
 */

public final class Json {
    private static final ObjectMapper OBJECT_MAPPER = Serializer.newObjectMapper();
    private final String string;

    private Json(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

    public <T> T cast(final Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(string, OBJECT_MAPPER.constructType(type));
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot cast: " + string + " into instance of: " + type, e);
        }
    }

    public static Json copyOf(final String string) {
        return new Json(string);
    }

    public static <T> Json of(final T object) {
        try {
            final String string = OBJECT_MAPPER.writeValueAsString(object);
            return new Json(string);
        } catch (IOException e) {
            throw new IllegalArgumentException("cannot convert " + ReflectionToStringBuilder.toString(object)
                    + " to Json", e);
        }
    }
}