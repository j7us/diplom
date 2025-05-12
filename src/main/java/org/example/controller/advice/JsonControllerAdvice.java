package org.example.controller.advice;

import org.example.exception.ConflictException;
import org.example.exception.NoAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JsonControllerAdvice {

    @ExceptionHandler(NoAccessException.class)
    ResponseEntity noaccess(Exception e) {
        return ResponseEntity.status(403).build();
    }

    @ExceptionHandler(ConflictException.class)
    ResponseEntity conflict(ConflictException e) {
        return ResponseEntity.status(409).build();
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity defaultException(Exception e) {
        return ResponseEntity.status(400).build();
    }
}
