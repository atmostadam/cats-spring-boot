package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service(CatSpringBeanServiceNames.QUERY_BY_MICROSERVICE_NUMBER)
public class QueryByMicrochipNumberCat implements CatService {
    @Autowired
    private CatSpringBootRepository repository;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            Long microchipNumber = request.getMicrochipNumbers().get(0);
            CatEntity entity = repository.querySingleRowByMicrochipNumber(microchipNumber);
            if(Objects.nonNull(entity)) {
                Cat cat = entity.newCat();
                response = new CatResponse()
                        .setMessage(String.format("Successfully retrieved row with microchip number [%s]", microchipNumber))
                        .addCat(cat)
                        .newResponseEntity(requestId, HttpStatus.OK);
            } else {
                response = new CatResponse()
                        .setMessage(String.format("Unable to find cat with microchip number [%s]", microchipNumber))
                        .newResponseEntity(requestId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
