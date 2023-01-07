package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.spring.boot.CatSpringBootApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = CatSpringBootApplication.class)
@AutoConfigureMockMvc
public class AddCatIT {

    @Test
    void test() {
        assertEquals(true, true);
    }

}