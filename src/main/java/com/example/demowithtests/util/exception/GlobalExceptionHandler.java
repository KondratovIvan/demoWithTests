package com.example.demowithtests.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.mail.AuthenticationFailedException;
import jakarta.mail.SendFailedException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(true));
        /*ErrorDetails errorDetails =
                new ErrorDetails(new Date(),
                        "Employee not found with id =" + request.getDescription(true),//getParameter("id"),
                        request.getDescription(false));*/
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<MyGlobalExceptionHandler> handleDeleteException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This user was deleted"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SendFailedException.class)
    public ResponseEntity<?> methodArgumentNotValidException(SendFailedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Mail sending failed. " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> methodAuthenticationFailedException(AuthenticationFailedException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Mail authentication failed. " + ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProhibitedCountryException.class)
    public ResponseEntity<?> prohibitedCountryException() {
        return new ResponseEntity<>(new MyGlobalExceptionHandler("This country was prohibited"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceWasSoftDeletedException.class)
    public ResponseEntity<?> resourceWasSoftDeletedException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                "Resource is already deleted softly" ,
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    private static class MyGlobalExceptionHandler {
        private String message;
    }
}
