package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.rest.CatResource;
import com.atmostadam.cats.spring.boot.service.CatSpringBootService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static com.atmostadam.cats.api.util.CatApiUtils.sneakyPrettyPrint;

public class CatSpringBootResource implements CatResource {
    public static final Logger logger = LoggerFactory.getLogger(CatSpringBootResource.class);

    @Autowired
    private CatSpringBootService service;

    @Override
    public ResponseEntity<CatResponse> queryByMicrochipNumber(CatMicrochipRequest microchipRequest) {
        logger.debug("HTTP METHOD: [{}], URI: [{}], REQUEST: [{}]", "GET", "/1.0/cat",
                sneakyPrettyPrint(microchipRequest));

        ResponseEntity<CatResponse> response;
        try {
             CatResponse catResponse = service.queryByMicrochipNumber(microchipRequest.getMicrochip());
             if(Objects.nonNull(catResponse) && Objects.isNull(catResponse.getStackTrace())) {
                 response = new ResponseEntity<>(catResponse, HttpStatus.OK);
             } else {
                 response = new ResponseEntity<>(catResponse, HttpStatus.BAD_REQUEST);
             }
         } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            CatResponse catResponse = new CatResponse();
            catResponse.setMessage(e.getMessage());
            catResponse.setStackTrace(ExceptionUtils.getStackTrace(e));
            response = new ResponseEntity<>(new CatResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
         logger.debug("RESPONSE: [{}]", sneakyPrettyPrint(response.getBody()));
         return response;
    }

    @Override
    public ResponseEntity<CatResponse> addCat(CatRequest request) {
        logger.debug("HTTP METHOD: [{}], URI: [{}], REQUEST: [{}]", "POST", "/1.0/cat",
                sneakyPrettyPrint(request));
        return new ResponseEntity<>(new CatResponse(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<CatResponse> updateCat(CatRequest request) {
        logger.debug("HTTP METHOD: [{}], URI: [{}], REQUEST: [{}]", "PATCH", "/1.0/cat",
                sneakyPrettyPrint(request));
        return new ResponseEntity<>(new CatResponse(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<CatResponse> deleteCat(CatMicrochipRequest microchipRequest) {
        logger.debug("HTTP METHOD: [{}], URI: [{}], REQUEST: [{}]", "DELETE", "/1.0/cat",
                sneakyPrettyPrint(microchipRequest));
        return new ResponseEntity<>(new CatResponse(), HttpStatus.NOT_IMPLEMENTED);
    }

}
