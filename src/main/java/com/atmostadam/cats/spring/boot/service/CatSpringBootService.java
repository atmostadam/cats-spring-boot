package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.exception.CatException;
import com.atmostadam.cats.api.model.Cat;
import com.atmostadam.cats.api.model.Microchip;
import com.atmostadam.cats.api.model.out.CatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatSpringBootService {

    public CatResponse queryByMicrochipNumber(Microchip microchip) throws CatException {
        return CatResponse.builder()
                .cats(List.of(Cat.builder().microchip(microchip).build()))
                .message("Simulated")
                .build();
    }
}
