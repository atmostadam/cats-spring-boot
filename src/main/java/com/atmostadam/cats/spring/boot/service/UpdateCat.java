package com.atmostadam.cats.spring.boot.service;

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

@Service(CatSpringBeanServiceNames.UPDATE_CAT)
public class UpdateCat implements CatService {
    @Autowired
    private CatSpringBootRepository repository;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        if (request.getCats().size() != 1) {
            return new CatResponse()
                    .setMessage("Client has not provided a single cat to Add! Bad Request!")
                    .newResponseEntity(requestId, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<CatResponse> response;
        Cat cat = request.getCats().get(0);
        Long microchipNumber = cat.getMicrochipNumber();
        try {
            repository.updateSingleRow(cat.newCatEntity());
            response = new CatResponse()
                    .setMessage(String.format("Successfully updated row with microchip number [%s]",
                            cat.getMicrochipNumber()))
                    .addCat(cat)
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
