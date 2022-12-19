package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.rest.CatRegistrationResource;
import com.atmostadam.cats.spring.boot.service.CatSpringBootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static com.atmostadam.cats.api.util.CatApiUtils.*;

public class CatSpringBootRegistrationResource implements CatRegistrationResource {
    public static final Logger logger = LoggerFactory.getLogger(CatSpringBootResource.class);

    @Autowired
    private CatSpringBootService service;

    @Override
    public ResponseEntity<CatResponse> queryByMicrochipNumber(CatMicrochipRequest microchipRequest) {
        logger.debug(logRequestMessage("GET", "/1.0/cat", microchipRequest));
        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(microchipRequest);
        logger.debug(logResponseMessage(response.getBody()));
        return response;
    }

    @Override
    public ResponseEntity<CatResponse> addCat(CatRequest request) {
        logger.debug(logRequestMessage("POST", "/1.0/cat", request));
        ResponseEntity<CatResponse> response = service.insertSingleRow(request);
        logger.debug(logResponseMessage(response.getBody()));
        return response;
    }

    @Override
    public ResponseEntity<CatResponse> updateCat(CatRequest request) {
        logger.debug(logRequestMessage("PUT", "/1.0/cat", request));
        ResponseEntity<CatResponse> response = service.updateSingleRow(request);
        logger.debug(logResponseMessage(response.getBody()));
        return response;
    }

    @Override
    public ResponseEntity<CatResponse> deleteCat(CatMicrochipRequest microchipRequest) {
        logger.debug(logRequestMessage("DELETE", "/1.0/cat", microchipRequest));
        ResponseEntity<CatResponse> response = service.deleteSingleRow(microchipRequest);
        logger.debug(logResponseMessage(response.getBody()));
        return response;
    }
}
