package org.hmhb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point into the spring-boot application.
 */
@SpringBootApplication
public class Application {

    /**
     * Starts the spring-boot application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
