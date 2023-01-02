package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service(CatSpringBeanServiceNames.POST_ADOPT_A_PET_CAT)
public class PostAdoptAPetCat implements CatService {
    @Autowired
    private CatSpringBootRepository repository;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        return new CatResponse()
                .setMessage("TODO")
                .newResponseEntity(requestId, HttpStatus.OK);
    }
}
