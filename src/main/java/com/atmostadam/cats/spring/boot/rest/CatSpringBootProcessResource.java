package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.rest.CatProcessResource;
import com.atmostadam.cats.spring.boot.service.CatSpringBootService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

public class CatSpringBootProcessResource implements CatProcessResource {
    public static final Logger logger = LoggerFactory.getLogger(CatSpringBootResource.class);

    @Autowired
    private CatSpringBootService service;

    @Override
    public ResponseEntity<CatResponse> treatCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> microchipCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> postPetfinderCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> postAdoptAPetCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }
}
