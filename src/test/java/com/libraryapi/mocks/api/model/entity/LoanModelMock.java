package com.libraryapi.mocks.api.model.entity;

import com.libraryapi.api.model.entity.LoanModel;

import java.time.LocalDate;

public class LoanModelMock {

    public static LoanModel getMock() {
        return LoanModel.builder()
                .id(1L)
                .customer("Eker,T. Harv")
                .book(BookModelMock.mockBookWithIdToLoanBook())
                .localDate(LocalDate.now())
                .returned(true)
                .build();
    }
}
