package com.detectify;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by moupiya on 10/16/17.
 */

public class AppInit extends ResourceConfig {

    public AppInit() {
        packages(true, "com.detectify.controllers");
        register(JacksonFeature.class);
    }
}
