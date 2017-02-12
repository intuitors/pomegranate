package org.pomegranate.demo.web.listener;


import org.pomegranate.demo.data.ContactDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author sylenthira
 */
@Component
public class AppServletContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Servlet Context Initialization...");
        ContactDatabase.setUpData();

        LOGGER.debug("Total Records: {}", ContactDatabase.CONTACT_RECORDS.size());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Context Destroyed");
    }
}
