package com.libraryapi.api.exception;

import com.libraryapi.exception.BusinessException;

import java.util.List;

public class ApiErrors {
    List<String> errors;

    public ApiErrors(IllegalArgumentException ex) {
        this.errors = List.of(ex.getMessage());
    }

    public ApiErrors(BusinessException ex) {
        this.errors = List.of(ex.getMessage());
    }

    public List<String> getErrors() {
        return errors;
    }
}
