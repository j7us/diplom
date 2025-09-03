package org.example.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.ConflictException;
import org.example.exception.NoAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class JsonControllerAdvice {

    @ExceptionHandler(NoAccessException.class)
    ResponseEntity noaccess(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(ConflictException.class)
    ResponseEntity conflict(ConflictException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(409).build();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity defaultException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(400).build();
    }
}
