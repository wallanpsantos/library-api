package com.libraryapi.api.exception;

import com.libraryapi.exception.BusinessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

public class ApiErrors {
    List<String> errors;

    public ApiErrors(IllegalArgumentException ex) {
        this.errors = List.of(ex.getMessage());
    }

    public ApiErrors(BusinessException ex) {
        this.errors = List.of(ex.getMessage());
    }

    public ApiErrors(ResponseStatusException ex) {
        this.errors = List.of(Objects.requireNonNull(ex.getReason()));
    }

    public List<String> getErrors() {
        return errors;
    }
}
