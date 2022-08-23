package com.libraryapi.mocks.api.dto;

import com.libraryapi.api.dto.LoanFilterDTO;

public class LoanFilterDTOMock {

    public static LoanFilterDTO get() {
        return LoanFilterDTO.builder()
                .customer("Eker,T. Harv")
                .isbn("8575422391")
                .build();
    }
}
