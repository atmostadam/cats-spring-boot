package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.exception.CatException;
import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.spring.boot.configuration.CatSpringBootTestConfiguration;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import com.atmostadam.cats.test.data.CatMicrochipRequestTestData;
import com.atmostadam.cats.test.data.CatResponseTestData;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.test.asserts.CatAsserts.*;
import static com.atmostadam.cats.test.data.CatEntityTestData.staticCatEntity;
import static com.atmostadam.cats.test.data.CatMicrochipRequestTestData.staticCatMicrochipRequest;
import static com.atmostadam.cats.test.data.CatResponseTestData.staticCatResponse;
import static com.atmostadam.cats.test.data.CatResponseTestData.staticCatResponse200;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(CatSpringBootTestConfiguration.class)
public class CatSpringBootServiceTest {
    @InjectMocks
    CatSpringBootService service;

    @Mock
    CatSpringBootRepository repository;

    @Test
    void queryByMicrochipNumber() {
        when(repository.querySingleRowByMicrochipNumber(isA(Long.class))).thenReturn(staticCatEntity());

        ResponseEntity<CatResponse> actual = service.queryByMicrochipNumber(staticCatMicrochipRequest());

        assertEquals(actual, staticCatResponse200(
                "Successfully retrieved row with microchip number [999999999999999]"));
    }

    @Test
    void queryByMicrochipNumber404() {
        CatMicrochipRequest request = staticCatMicrochipRequest();

        CatResponse expectedCatResponse = CatResponse.builder()
                .transactionId(request.getTransactionId())
                .message("Unable to find cat with microchip number [999999999999999]")
                .build();
        ResponseEntity<CatResponse> expectedResponse = new ResponseEntity<>(expectedCatResponse, HttpStatus.NOT_FOUND);

        when(repository.querySingleRowByMicrochipNumber(isA(Long.class)))
                .thenReturn(staticCatEntity());

        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(request);

        assertThat(response.getStatusCode(), Matchers.equalTo(expectedResponse.getStatusCode()));
        assertThat(response.getBody().getTransactionId(), Matchers.equalTo(expectedResponse.getBody().getTransactionId()));
        assertThat(response.getBody().getMessage(), Matchers.equalTo(expectedResponse.getBody().getMessage()));
        assertThat(response.getBody().getStackTrace(), Matchers.equalTo(expectedResponse.getBody().getStackTrace()));
        assertThat(response.getBody().getCats(), Matchers.equalTo(expectedResponse.getBody().getCats()));
    }

    @Test
    void queryByMicrochipNumber500() {
        RuntimeException exception = new RuntimeException("Http 500 Response");

        ResponseEntity<CatResponse> expectedResponse = new ResponseEntity<>(staticCatResponse(exception), HttpStatus.INTERNAL_SERVER_ERROR);

        when(repository.querySingleRowByMicrochipNumber(isA(Long.class))).thenThrow(exception);

        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(staticCatMicrochipRequest());

        assertThat(response.getStatusCode(), Matchers.equalTo(expectedResponse.getStatusCode()));
        assertThat(response.getBody().getTransactionId(), Matchers.equalTo(expectedResponse.getBody().getTransactionId()));
        assertThat(response.getBody().getMessage(), Matchers.equalTo(expectedResponse.getBody().getMessage()));
        assertThat(response.getBody().getStackTrace(), Matchers.equalTo(expectedResponse.getBody().getStackTrace()));
        assertThat(response.getBody().getCats(), Matchers.equalTo(expectedResponse.getBody().getCats()));
    }

    @Test
    void insertSingleRow() {

    }

    @Test
    void updateSingleRow() {

    }

    @Test
    void deleteSingleRow() {

    }
}
