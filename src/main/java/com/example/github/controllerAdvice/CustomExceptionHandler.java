package com.example.github.controllerAdvice;

import com.example.github.exception.GithubUserNotFoundException;
import com.example.github.model.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GithubUserNotFoundException.class)
    public ResponseEntity<Object> handleGithubUserNotFoundException(GithubUserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleNotAcceptableException(HttpMediaTypeNotAcceptableException ex) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(),
                "The requested media type 'application/xml' is not acceptable. Please use 'application/json'.");

        ObjectMapper objectMapper = new ObjectMapper();
        String formattedJsonErrorResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(errorResponse);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(formattedJsonErrorResponse);
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An unexpected error occurred.";

        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);

        return new ResponseEntity<>(errorResponse, status);
    }
}