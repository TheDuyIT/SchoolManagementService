package com.apollogix.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.apollogix"})
public class SchoolManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

}
