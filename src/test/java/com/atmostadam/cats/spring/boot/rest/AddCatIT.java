package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.spring.boot.CatSpringBootApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.atmostadam.cats.api.util.CatApiUtils.convertToJsonNode;
import static com.atmostadam.cats.api.util.CatDefaultValues.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = CatSpringBootApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:integration-application.properties")
public class AddCatIT {
    private static final ObjectMapper om = new ObjectMapper();

    private static final String RESOURCE = "/cats/1.0/cat";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void httpStatus200() throws Exception {
        long microchipNumber = 431654444132657L;

        CatResponse expected = new CatResponse()
                .setMessage("Successfully inserted row with microchip number [" + microchipNumber +"]")
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        CatRequest request = new CatRequest()
                .addMicrochipNumber(microchipNumber)
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        MvcResult actual = mockMvc.perform(post(RESOURCE)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.OK.value(), actual.getResponse().getStatus());
        assertEquals(List.of(TEST_REQUEST_ID), actual.getResponse().getHeaders("requestId"));
        assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());

        CatResponse response = om.readValue(actual.getResponse().getContentAsString(), CatResponse.class);
        assertEquals(expected.getMessage(), response.getMessage());
        assertEquals(convertToJsonNode(expected.getCats().get(0)), convertToJsonNode(response.getCats().get(0)));
        assertEquals(convertToJsonNode(expected), convertToJsonNode(response));
    }

    @Test
    void httpStatus500DuplicateRow() throws Exception {
        long microchipNumber = 431654445132657L;

        CatResponse expected = new CatResponse()
                .setMessage("[DataIntegrityViolationException] while inserting row with microchip number ["
                        + microchipNumber +"] [microchipNumber already exists]")
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        CatRequest request = new CatRequest()
                .addMicrochipNumber(microchipNumber)
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        mockMvc.perform(post(RESOURCE)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        MvcResult actual = mockMvc.perform(post(RESOURCE)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getResponse().getStatus());
        assertEquals(List.of(TEST_REQUEST_ID), actual.getResponse().getHeaders("requestId"));
        assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());

        CatResponse response = om.readValue(actual.getResponse().getContentAsString(), CatResponse.class);
        assertEquals(expected.getMessage(), response.getMessage());
        assertTrue(response.getStackTrace().contains("org.springframework.dao.DataIntegrityViolationException"));
        assertEquals(convertToJsonNode(expected.getCats().get(0)), convertToJsonNode(response.getCats().get(0)));
    }

    @Test
    void httpStatus400ZeroCats() throws Exception {
        long microchipNumber = 431654446132657L;

        CatResponse expected = new CatResponse()
                .setMessage("Client has not provided a cat to Add! Bad Request!");

        CatRequest request = new CatRequest()
                .addMicrochipNumber(microchipNumber);

        MvcResult actual = mockMvc.perform(post(RESOURCE)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getResponse().getStatus());
        assertEquals(List.of(TEST_REQUEST_ID), actual.getResponse().getHeaders("requestId"));
        assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());

        CatResponse response = om.readValue(actual.getResponse().getContentAsString(), CatResponse.class);
        assertEquals(expected.getMessage(), response.getMessage());
        assertEquals(expected.getStackTrace(), response.getStackTrace());
        assertEquals(expected.getCats(), response.getCats());
    }

    @Test
    void httpStatus400MultipleCats() throws Exception {
        long microchipNumber = 431654446132657L;
        long microchipNumber2 = 431654447132657L;

        CatResponse expected = new CatResponse()
                .setMessage("Client has provided multiple cats to Add! Bad Request!")
                .addCat(catTestData().setMicrochipNumber(microchipNumber))
                .addCat(catTestData().setMicrochipNumber(microchipNumber2));

        CatRequest request = new CatRequest()
                .addMicrochipNumber(microchipNumber)
                .addMicrochipNumber(microchipNumber2)
                .addCat(catTestData().setMicrochipNumber(microchipNumber))
                .addCat(catTestData().setMicrochipNumber(microchipNumber2));

        MvcResult actual = mockMvc.perform(post(RESOURCE)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getResponse().getStatus());
        assertEquals(List.of(TEST_REQUEST_ID), actual.getResponse().getHeaders("requestId"));
        assertEquals(MediaType.APPLICATION_JSON.toString(), actual.getResponse().getContentType());

        CatResponse response = om.readValue(actual.getResponse().getContentAsString(), CatResponse.class);
        assertEquals(expected.getMessage(), response.getMessage());
        assertEquals(expected.getStackTrace(), response.getStackTrace());
        assertEquals(convertToJsonNode(expected.getCats().get(0)), convertToJsonNode(response.getCats().get(0)));
        assertEquals(convertToJsonNode(expected.getCats().get(1)), convertToJsonNode(response.getCats().get(1)));
    }
}