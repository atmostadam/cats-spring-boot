package com.atmostadam.cats.spring.boot.exception;

import com.atmostadam.cats.api.model.out.CatResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class CatSpringBootExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        return new ResponseEntity<>(handleAllExceptions(ex, request), status);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CatResponse> handleAllExceptions(Exception ex, WebRequest request) {
        String message;
        if(Objects.isNull(request) || Objects.isNull(request.getHeaderNames()) || !request.getHeaderNames().hasNext()) {
            return new CatResponse()
                    .setMessage(String.format("RequestId [null] failed with exception message [%s]",
                            "Completely Missing Headers or Request! " + ex.getMessage()))
                    .setStackTrace(ExceptionUtils.getStackTrace(ex))
                    .newResponseEntity("MISSING - requestId", HttpStatus.BAD_REQUEST);
        }
        MultiValueMap headers = new LinkedMultiValueMap();
        while(request.getHeaderNames().hasNext()) {
            String header = request.getHeaderNames().next();
            headers.put(header, request.getHeader(header));
        }
        return new CatResponse()
                .setMessage(String.format("RequestId [%s] failed with exception message [%s]",
                        headers.get("RequestId"), ex.getMessage()))
                .setStackTrace(ExceptionUtils.getStackTrace(ex))
                .newResponseEntity(headers, HttpStatus.BAD_REQUEST);
    }
}
