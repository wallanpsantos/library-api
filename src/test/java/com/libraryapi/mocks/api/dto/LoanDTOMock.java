package com.libraryapi.mocks.api.dto;

import com.libraryapi.api.dto.LoanDTO;

public class LoanDTOMock {

    public static LoanDTO createMock() {
        return LoanDTO.builder()
                .isbn("8575422391")
                .customer("Eker,T. Harv")
                .build();
    }
}
