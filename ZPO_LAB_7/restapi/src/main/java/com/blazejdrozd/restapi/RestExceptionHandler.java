package com.blazejdrozd.restapi;

import com.blazejdrozd.restapi.exceptions.StudentRepoAlreadyExistsException;
import com.blazejdrozd.restapi.exceptions.StudentRepoNotFoundException;
import com.blazejdrozd.restapi.exceptions.StudentRepoWrongDataProvided;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {
    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message, HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(StudentRepoNotFoundException.class)
    public ResponseEntity<Object> handleNotFound(StudentRepoNotFoundException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(StudentRepoAlreadyExistsException.class)
    public ResponseEntity<Object> handleAlreadyExists(StudentRepoAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(StudentRepoWrongDataProvided.class)
    public ResponseEntity<Object> handleBadRequest(StudentRepoWrongDataProvided ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
