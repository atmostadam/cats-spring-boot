package com.atmostadam.cats.spring.boot.model.adoptacat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptAPetResponse {
    private String status;

    private LimitedPetDetails pet;
}
