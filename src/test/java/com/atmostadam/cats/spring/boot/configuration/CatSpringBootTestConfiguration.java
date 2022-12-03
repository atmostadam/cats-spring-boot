package com.atmostadam.cats.spring.boot.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.atmostadam.cats.spring.boot")
@TestConfiguration
public class CatSpringBootTestConfiguration {
}
