package com.bni.test.backendfitness.exception;

import com.bni.test.backendfitness.helpers.WebResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public WebResponse<Object> globalException(GlobalException ex, HttpServletRequest request){
        WebResponse<Object> webResponse = new WebResponse<>();
        webResponse.setErrors(ex.getMessage());

        return webResponse;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> apiException(ResponseStatusException exception){
        return ResponseEntity.status(exception.getStatusCode())
                .body(WebResponse.<String>builder()
                        .errors(exception.getReason())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public WebResponse<Object> methodArgumentNotValid(MethodArgumentNotValidException e){
        List<String> details = new ArrayList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        WebResponse<Object> webResponse = new WebResponse<>();
        webResponse.setErrors(details.spliterator().toString());

        return webResponse;
    }

}
