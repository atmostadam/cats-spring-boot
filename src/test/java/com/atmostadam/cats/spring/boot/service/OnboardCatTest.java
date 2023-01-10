package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.api.util.CatApiUtils.convertToJsonNode;
import static com.atmostadam.cats.api.util.CatDefaultValues.TEST_REQUEST_ID;
import static com.atmostadam.cats.api.util.CatDefaultValues.catTestData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
public class OnboardCatTest {
    @Autowired
    OnboardCat service;

    @Mock
    CatSpringBootRepository repository;

    @Test
    void httpStatus200() {
        long microchipNumber = 431655446132657L;

        CatRequest request = new CatRequest()
                .addMicrochipNumber(microchipNumber)
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        CatResponse expected = new CatResponse()
                .setMessage("Successfully inserted row with microchip number [" + microchipNumber + "]")
                .addCat(catTestData().setMicrochipNumber(microchipNumber));

        doNothing().when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, request);

        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());

        CatResponse response = actual.getBody();
        assertEquals(expected.getMessage(), response.getMessage());
        assertEquals(expected.getStackTrace(), response.getStackTrace());
        assertEquals(convertToJsonNode(expected.getCats().get(0)), convertToJsonNode(response.getCats().get(0)));
    }
}
