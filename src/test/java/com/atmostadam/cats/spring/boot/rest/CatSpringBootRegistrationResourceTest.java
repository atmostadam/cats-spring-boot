package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.atmostadam.cats.spring.boot.service.CatSpringBootService;
import com.atmostadam.cats.test.data.CatResponseTestData;
import com.atmostadam.cats.test.util.CatTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.atmostadam.cats.test.data.CatMicrochipRequestTestData.staticCatMicrochipRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
public class CatSpringBootRegistrationResourceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @InjectMocks
    CatSpringBootRegistrationResource restResource;

    private MockMvc mockMvc;

    @Mock
    CatSpringBootService service;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(restResource).build();
    }

    @Test
    void queryByMicrochipNumber() throws Exception {
        CatMicrochipRequest request = staticCatMicrochipRequest();
        CatResponse response = CatResponse.builder()
                .cats(List.of(Cat.builder().microchip(request.getMicrochip()).build()))
                .message("Simulated")
                .build();

        when(service.queryByMicrochipNumber(isA(CatMicrochipRequest.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        MvcResult mvcResult = mockMvc.perform(
                        get("/cats/register/1.0/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        CatResponse actualResponse = CatTestUtils.extractResponse(mvcResult);

        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        assertThat(actualResponse.getCats().get(0).getMicrochip().getMicrochipNumber(),
                Matchers.equalTo(CatResponseTestData.catResponse().getCats().get(0).getMicrochip().getMicrochipNumber()));
    }
}
