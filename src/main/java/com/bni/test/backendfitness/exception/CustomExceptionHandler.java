package com.bni.test.backendfitness.exception;

import com.bni.test.backendfitness.helpers.WebResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public WebResponse<Object> globalException(GlobalException ex, HttpServletRequest request){
        WebResponse<Object> webResponse = new WebResponse<>();
        webResponse.setErrors(ex.getMessage());

        return webResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValid(MethodArgumentNotValidException e){
        List<String> details = new ArrayList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(WebResponse.setValidateRequest(details, "error field") );
    }

}
