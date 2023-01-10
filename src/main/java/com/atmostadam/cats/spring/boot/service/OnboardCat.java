package com.atmostadam.cats.spring.boot.service;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.api.service.CatSpringBeanServiceNames;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import com.atmostadam.cats.spring.boot.orchestrate.CatSpringBootOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service(CatSpringBeanServiceNames.ONBOARD_CAT)
class OnboardCat implements CatService {
    private static final Logger logger = LoggerFactory.getLogger(OnboardCat.class);

    @Autowired
    private AddCat addCat;

    @Autowired
    private CatSpringBootOrchestrator orchestrator;

    @Override
    public ResponseEntity<CatResponse> invoke(String requestId, CatRequest request) {
        // TODO: Add more workflow steps
        return orchestrator.invokeAsync(addCat, requestId, request);
    }
}
