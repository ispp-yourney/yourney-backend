package com.yourney.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationUtils {
    
    public static Map<String, List<String>> validateDto(BindingResult result) {
        Map<String, List<String>> errors = new HashMap<>();
        
        for (FieldError error : result.getFieldErrors()) {
            if (errors.containsKey(error.getField())) {
                errors.get(error.getField()).add(error.getDefaultMessage());
            } else {
                List<String> aux = new ArrayList<>();
                aux.add(error.getDefaultMessage());
                errors.put(error.getField(), aux);
            }
        }

        return errors;
    }
    
}
