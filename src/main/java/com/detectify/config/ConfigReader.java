package com.detectify.config;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by dabroal on 10/16/17.
 */
public class ConfigReader {
    public final static String KAFKA_BOOTSTRAP_SERVER = "localhost:9092";
    public final static String KAFKA_GROUP_ID = "kafka.group.id";
    public final static String KAFKA_ENABLE_AUTO_COMMIT = "kafka.enable.auto.commit";
    public final static String KAFKA_SCREENSHOT_TOPIC = "kafka.screenshot.topic";
    public final static String KAFKA_PRODUCER_RETRIES = "kafka.producer.retries";
    public final static String KAFKA_PRODUCER_BATCH_SIZE = "kafka.producer.batch.size";
    public final static String KAFKA_PRODUCER_LINGER = "kafka.producer.linger.ms";
    public final static String KAFKA_PRODUCER_BUFFER_MEMORY = "kafka.producer.buffer.memory";
    @Getter
    private static final ConfigReader instance = new ConfigReader();

    private Map<String, String> map;

    private ConfigReader() {
        map = AppConfig.getAllProperties();
    }

    public Boolean getBoolean(final String param) {
        return Boolean.valueOf(getString(param));
    }

    public Integer getInteger(final String param, Integer defaultValue) {
        String intString = getString(param);

        return StringUtils.isBlank(intString) ? defaultValue : Integer.parseInt(intString);
    }

    public Integer getInteger(final String param) {
        return getInteger(param, 0);
    }

    public String getString(final String param) {
        if (map.containsKey(param)) {
            return map.get(param);
        }

        return StringUtils.EMPTY;
    }
}
