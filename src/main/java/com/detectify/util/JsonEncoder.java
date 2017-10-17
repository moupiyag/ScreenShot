package com.detectify.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;


/**
 * Created by Moupiya on 3/28/17.
 */
public class JsonEncoder implements org.apache.kafka.common.serialization.Serializer<Object> {
    private static final ObjectMapper OBJECT_MAPPER = Serializer.newObjectMapper();

    public void configure(final Map<String, ?> configs, final boolean isKey) {
    }

    public byte[] serialize(final String topic, final Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    public void close() {
    }
}
