package com.atmostadam.cats.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "io.atmostadam.cats")
@ComponentScan(basePackages = "io.atmostadam.cats")
public class CatSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatSpringBootApplication.class, args);
    }
}
