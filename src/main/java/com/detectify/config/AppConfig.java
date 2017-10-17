package com.detectify.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by dabroal on 10/16/17.
 */
public class AppConfig {
    private static Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);
    private static HashMap<String, String> appConfigProperties = new HashMap();

    public static synchronized void init() {
        if (!isInited()) {
            try {
                URL e = AppConfig.class.getResource("/props/app_config.properties");
                if (e == null) {
                    throw new RuntimeException("Failed to find /props/app_config.properties file on classpath");
                } else {
                    registerProperties(e.getPath(), e.openStream());
                }
            } catch (RuntimeException rte) {
                throw rte;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static boolean isInited() {
        return !appConfigProperties.isEmpty();
    }

    private static void registerProperties(String filePath, InputStream in) throws IOException {
        LOGGER.info("Registering properties from: " + filePath);
        Properties temp = new Properties();
        temp.load(in);

        Enumeration keys = temp.keys();

        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            String value = temp.getProperty(key);
            appConfigProperties.put(key, value);
        }

    }

    public static Map<String, String> getAllProperties() {
        if (!isInited()) {
            init();
        }

        return appConfigProperties;
    }

    public AppConfig() {
        init();
    }

}
