package com.thunisoft.orchid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@EntityScan(basePackages = "com.thunisoft.orchid.bean.pojo")
@EnableCaching
@SpringBootApplication
public class OrchidApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchidApplication.class, args);
    }

}
