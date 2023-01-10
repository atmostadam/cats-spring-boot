package com.atmostadam.cats.spring.boot.model.adoptacat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptAPetRequest {
    private String action;

    private LimitedPetDetails pet;
}
