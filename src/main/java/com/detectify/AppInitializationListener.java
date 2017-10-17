package com.detectify;

import com.detectify.service.RequestConsumer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Moupiya
 */
public final class AppInitializationListener implements ServletContextListener {
    private static ExecutorService executor;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        executor = Executors.newFixedThreadPool(1);
        executor.submit(new RequestConsumer());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        RequestConsumer.getConsumer().close();
        executor.shutdown();
    }
}