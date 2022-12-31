package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.configuration.CatConfiguration;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.rest.CatResource;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.atmostadam.cats.api.util.CatApiUtils.convertToJsonNode;
import static com.atmostadam.cats.spring.boot.test.CatTestValues.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
class CatResourceTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    CatResource resource;

    @Mock
    CatService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(resource).build();
    }

    @ParameterizedTest
    @ValueSource(strings = {// "/cats/1.0/cat/register/onboard",
            // "/cats/1.0/cat/register/transfer",
            // "/cats/1.0/cat/register/transfer/foster",
            // "/cats/1.0/cat/register/adopt",
            "/cats/1.0/cat" })
            // "/cats/1.0/cat/process/medical",
            // "/cats/1.0/cat/process/microchip",
            // "/cats/1.0/cat/process/petfinder",
            //  "/cats/1.0/cat/process/adoptapet"})
    void postCatResource(String resource) throws Exception {
        when(service.invoke(isA(String.class), isA(CatRequest.class)))
                .thenReturn(new ResponseEntity<>(catResponseTestData(), HttpStatus.OK));

        MvcResult actual = mockMvc.perform(post(resource)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(catRequestTestData())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(actual.getResponse().getStatus(), Matchers.equalTo(HttpStatus.OK.value()));
        assertThat(actual.getResponse().getHeaders("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(actual.getResponse().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON.toString()));

        assertEquals(catResponseTestData(),
                convertToJsonNode(om.readValue(actual.getResponse().getContentAsString(), CatResponse.class)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/cats/1.0/cat"})
    void getCatResource(String resource) throws Exception {
        when(service.invoke(isA(String.class), isA(CatRequest.class)))
                .thenReturn(new ResponseEntity<>(catResponseTestData(), HttpStatus.OK));

        MvcResult actual = mockMvc.perform(get(resource)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new CatRequest().addMicrochipNumber(TEST_MICROCHIP_NUMBER))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(actual.getResponse().getStatus(), Matchers.equalTo(HttpStatus.OK.value()));
        assertThat(actual.getResponse().getHeaders("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(actual.getResponse().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON.toString()));

        assertEquals(catResponseTestData(),
                convertToJsonNode(om.readValue(actual.getResponse().getContentAsString(), CatResponse.class)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/cats/1.0/cat"})
    void patchCatResource(String resource) throws Exception {
        when(service.invoke(isA(String.class), isA(CatRequest.class)))
                .thenReturn(new ResponseEntity<>(catResponseTestData(), HttpStatus.OK));

        MvcResult actual = mockMvc.perform(patch(resource)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(catRequestTestData())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(actual.getResponse().getStatus(), Matchers.equalTo(HttpStatus.OK.value()));
        assertThat(actual.getResponse().getHeaders("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(actual.getResponse().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON.toString()));

        assertEquals(catResponseTestData(),
                convertToJsonNode(om.readValue(actual.getResponse().getContentAsString(), CatResponse.class)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"/cats/1.0/cat"})
    void deleteCatResource(String resource) throws Exception {
        when(service.invoke(isA(String.class), isA(CatRequest.class)))
                .thenReturn(new ResponseEntity<>(catResponseTestData(), HttpStatus.OK));

        MvcResult actual = mockMvc.perform(delete(resource)
                        .header("requestId", TEST_REQUEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(new CatRequest().addMicrochipNumber(TEST_MICROCHIP_NUMBER))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        assertThat(actual.getResponse().getStatus(), Matchers.equalTo(HttpStatus.OK.value()));
        assertThat(actual.getResponse().getHeaders("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(actual.getResponse().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON.toString()));

        assertEquals(catResponseTestData(),
                convertToJsonNode(om.readValue(actual.getResponse().getContentAsString(), CatResponse.class)));
    }
}
