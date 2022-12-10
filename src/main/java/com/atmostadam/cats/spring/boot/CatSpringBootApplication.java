package com.atmostadam.cats.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "com.atmostadam.cats")
@ComponentScan(basePackages = "com.atmostadam.cats")
public class CatSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatSpringBootApplication.class, args);
    }
}
