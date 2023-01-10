package com.atmostadam.cats.spring.boot.orchestrate;

import com.atmostadam.cats.api.model.in.CatRequest;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.api.service.CatService;
import com.atmostadam.cats.spring.boot.jpa.CatSpringBootRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class CatSpringBootOrchestrator {
    private static final Logger logger = LoggerFactory.getLogger(CatSpringBootRepository.class);

    @Autowired
    private ThreadPoolTaskExecutor executorService;

    public ResponseEntity<CatResponse> invokeAsync(CatService service, String requestId, CatRequest request) {
        logger.info("Start Invoke Async [{}] with request id [{}] and cat [{}]", service.getClass().getSimpleName(),
                requestId, request.getMicrochipNumbers().get(0));
        Future<ResponseEntity<CatResponse>> future = executorService.submit(() ->
                service.invoke(requestId, request));
        ResponseEntity<CatResponse> response;
        try {
            response = future.get();
        } catch (Exception e) {
            response = new CatResponse()
                    .setMessage(String.format("Failed %s [%s] with request id [%s] and error message [%s]",
                            e.getClass().getSimpleName(), service.getClass().getSimpleName(),
                            requestId, e.getMessage()))
                    .setStackTrace(ExceptionUtils.getStackTrace(e))
                    .newResponseEntity(requestId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Completed Invoke Async [{}] with request id [{}] and cat [{}]",
                service.getClass().getSimpleName(), requestId, request.getMicrochipNumbers().get(0));
        return response;
    }
}
