package org.pomegranate.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @author Sylenthira on 2/11/2017.
 */
@SpringBootApplication
public class JerseyServerApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(JerseyServerApp.class);

    public static void main(String[] args) {
        SpringApplication.run(JerseyServerApp.class, args);
    }




    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            LOGGER.info("Inspecting the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOGGER.info("Bean: {}", beanName);
            }

        };
    }
}
