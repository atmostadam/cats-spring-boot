package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.exception.CatException;
import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.in.CatMicrochipRequest;
import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.util.CatApiUtils;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CatSpringBootService {
    @Autowired
    private CatSpringBootRepository repository;

    public ResponseEntity<CatResponse> queryByMicrochipNumber(CatMicrochipRequest microchipRequest) {
        ResponseEntity<CatResponse> response;
        try {
            CatEntity entity = repository.querySingleRowByMicrochipNumber(microchipRequest
                    .getMicrochip().getMicrochipNumber());
            if(Objects.nonNull(entity)) {
                Cat cat = CatApiUtils.switchCat(entity);
                response = CatApiUtils.successResponse(microchipRequest.getTransactionId(), String.format(
                                "Successfully retrieved row with microchip number [%s]", microchipRequest
                                        .getMicrochip().getMicrochipNumber()), List.of(cat));
            } else {
                response = new ResponseEntity<>(CatResponse.builder()
                        .transactionId(microchipRequest.getTransactionId())
                        .message(String.format("Unable to find cat with microchip number [%s]",
                                microchipRequest.getMicrochip().getMicrochipNumber()))
                        .build(), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response = CatApiUtils.http500Response(microchipRequest.getTransactionId(), e, List.of());
        }
        return response;
    }

    public ResponseEntity<CatResponse> insertSingleRow(CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            Cat cat = validateSingleRow(request);
            repository.insertSingleRow(CatApiUtils.switchCat(cat));
            response = CatApiUtils.successResponse(request.getTransactionId(),
                    "Successfully inserted row with microchip number " + cat.getMicrochip().getMicrochipNumber(),
                    List.of(cat));
        } catch (Exception e) {
            response = CatApiUtils.http500Response(request.getTransactionId(), e, request.getCats());
        }
        return response;
    }

    public ResponseEntity<CatResponse> updateSingleRow(CatRequest request) {
        ResponseEntity<CatResponse> response;
        try {
            Cat cat = validateSingleRow(request);
            repository.updateSingleRow(CatApiUtils.switchCat(cat));
            response = CatApiUtils.successResponse(request.getTransactionId(),
                    "Successfully updated row with microchip number " + cat.getMicrochip().getMicrochipNumber(),
                    List.of(cat));
        } catch (Exception e) {
            response = CatApiUtils.http500Response(request.getTransactionId(), e, request.getCats());
        }
        return response;
    }

    public ResponseEntity<CatResponse> deleteSingleRow(CatMicrochipRequest microchipRequest) {
        ResponseEntity<CatResponse> response;
        try {
            CatEntity entity =
                    repository.querySingleRowByMicrochipNumber(microchipRequest.getMicrochip().getMicrochipNumber());
            repository.deleteSingleRow(entity);
            response = CatApiUtils.successResponse(microchipRequest.getTransactionId(),
                    "Successfully deleted row with microchip number " + microchipRequest
                            .getMicrochip().getMicrochipNumber(),
                    List.of());
        } catch (Exception e) {
            response = CatApiUtils.http500Response(microchipRequest.getTransactionId(), e, List.of());
        }
        return response;
    }

    private Cat validateSingleRow(CatRequest request) throws CatException {
        if(request.getCats().size() != 1) {
            throw new CatException("ERROR: Only 1 row is supported on this call. Someone is trying to use " +
                    request.getCats().size());
        }
        return request.getCats().get(0);
    }
}
