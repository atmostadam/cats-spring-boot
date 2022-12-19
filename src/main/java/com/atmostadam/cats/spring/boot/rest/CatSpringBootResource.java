package com.atmostadam.cats.spring.boot.rest;

import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.rest.CatResource;
import com.atmostadam.cats.spring.boot.service.CatSpringBootService;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.atmostadam.cats.api.util.CatApiUtils.prettyPrint;

public class CatSpringBootResource implements CatResource {
    public static final Logger logger = LoggerFactory.getLogger(CatSpringBootResource.class);

    @Autowired
    private CatSpringBootService service;

    @Override
    public ResponseEntity<CatResponse> onboardCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> fosterCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> adoptCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }

    @Override
    public ResponseEntity<CatResponse> transferCat(@Valid CatRequest cat) {
        throw new NotImplementedException();
    }
}
