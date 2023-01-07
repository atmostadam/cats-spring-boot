package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.entity.CatEntity;
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

@Service(CatSpringBeanServiceNames.DELETE_CAT)
public class DeleteCat implements CatService {
    @Autowired
    private CatSpringBootRepository repository;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            CatEntity entity =
                    repository.querySingleRowByMicrochipNumber(request.getMicrochipNumbers().get(0));
            repository.deleteSingleRow(entity);
            response = new CatResponse()
                    .setMessage(String.format("Successfully deleted row with microchip number [%s]",
                            request.getMicrochipNumbers().get(0)))
                    .newResponseEntity(requestId, HttpStatus.OK);
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(e.getMessage())
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
