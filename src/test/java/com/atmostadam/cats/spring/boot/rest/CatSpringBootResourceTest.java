package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.atmostadam.cats.test.data.CatResponseTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.atmostadam.cats.test.data.CatMicrochipRequestTestData.staticCatMicrochipRequest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
public class CatSpringBootResourceTest {

    public static final ObjectMapper om = new ObjectMapper();

    @InjectMocks
    CatSpringBootResource restResource;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = standaloneSetup(restResource).build();
    }

    @Test
    void queryByMicrochipNumber() throws Exception {
        CatMicrochipRequest request = staticCatMicrochipRequest();

        MvcResult mvcResult = mockMvc.perform(
                        get("/cats/1.0/cat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        CatResponse actual = om.readValue(mvcResult.getResponse().getContentAsString(), CatResponse.class);
        assertThat(mvcResult.getResponse().getStatus(), equalTo(200));
        assertThat(actual.getCats().get(0).getMicrochip().getMicrochipNumber(),
                Matchers.equalTo(CatResponseTestData.catResponse().getCats().get(0).getMicrochip().getMicrochipNumber()));
    }
}
