package com.ngshop.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@ResponseStatus
@SuppressWarnings({"unchecked","rawtypes"})
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getMessage(),details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    /**Exception Handing for CustomException*/
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessage> customException(CustomException exception,
                                                        WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(exception.getLocalizedMessage());
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                exception.getMessage(),details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<String> details = new ArrayList<>();
//        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
//            details.add(error.getDefaultMessage());
//        }
//        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
//                "Validation Failed",details);
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(message);
//    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessage> invalidCredentialsException(InvalidCredentialsException exception,
                                                                    WebRequest request) {
        List<String> details = new ArrayList<>();
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                exception.getMessage(),details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);

    }

}
