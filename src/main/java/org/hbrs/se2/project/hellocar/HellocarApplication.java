package org.hbrs.se2.project.hellocar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HellocarApplication extends SpringBootServletInitializer {
//public class HellocarApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellocarApplication.class, args);
    }

}
