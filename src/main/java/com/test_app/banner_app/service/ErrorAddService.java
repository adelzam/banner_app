package com.test_app.banner_app.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorAddService {
    public Map<String, String> writeErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                fieldError -> fieldError.getField() + " Error:", FieldError::getDefaultMessage)
        );
        return errorMap;
    }
}