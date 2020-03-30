package com.thunisoft.orchid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OrchidApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchidApplication.class, args);
    }

}
