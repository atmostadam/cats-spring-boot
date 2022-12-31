package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service(CatSpringBeanServiceNames.DELETE_CAT)
public class DeleteCat implements CatService {
    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        return new CatResponse()
                .setMessage("TODO")
                .newResponseEntity(requestId, HttpStatus.OK);
    }
}
