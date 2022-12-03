package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.exception.CatException;
import com.atmostadam.cats.api.model.Microchip;
import com.atmostadam.cats.api.model.out.CatResponse;
import org.springframework.stereotype.Service;

@Service
public class CatSpringBootService {

    public CatResponse queryByMicrochipNumber(Microchip microchip) throws CatException {
        CatResponse response = new CatResponse();

        return response;
    }

}
