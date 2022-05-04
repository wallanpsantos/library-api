package com.libraryapi.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class LoanFilterDTO {

    private final String isbn;
    private final String customer;
}
