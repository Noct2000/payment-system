package com.example.business.api.controller;

import feign.FeignException;
import feign.RequestTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class BusinessApiControllerAdvice {
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(FeignException exception) {
        String exceptionMessage = new String(exception.responseBody().get().array());
        RequestTemplate requestTemplate = exception.request().requestTemplate();
        log.warn(
                "FeignException with message '{}', requestTemplate = {}",
                exceptionMessage,
                requestTemplate
        );
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }
}
