package com.example.intermediaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IntermediariesApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntermediariesApplication.class, args);
    }

}
