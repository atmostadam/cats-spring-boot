package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service(CatSpringBeanServiceNames.UPDATE_CAT)
public class UpdateCat implements CatService {
    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        return null;
    }
}
