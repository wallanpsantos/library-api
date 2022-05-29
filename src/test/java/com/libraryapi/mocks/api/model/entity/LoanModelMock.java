package com.libraryapi.mocks.api.model.entity;

import com.libraryapi.api.model.entity.LoanModel;

import java.time.LocalDate;

public class LoanModelMock {

    public static LoanModel get() {
        return LoanModel.builder()
                .id(1L)
                .customer("Eker,T. Harv")
                .email("test.email@mail.com")
                .book(BookModelMock.mockBookWithIdToLoanBook())
                .localDate(LocalDate.now())
                .returned(true)
                .build();
    }

    public static LoanModel getNotId() {
        return LoanModel.builder()
                .customer("Eker,T. Harv")
                .book(BookModelMock.mockBookWithIdToLoanBook())
                .localDate(LocalDate.now())
                .returned(true)
                .build();
    }

    public static LoanModel getNotIdLoanAndBook() {
        return LoanModel.builder()
                .customer("Yoshi Adventure")
                .book(BookModelMock.getBookMockNotId())
                .localDate(LocalDate.now())
                .returned(true)
                .build();
    }

    public static LoanModel getReturnedFalse() {
        return LoanModel.builder()
                .id(1L)
                .customer("Eker,T. Harv")
                .book(BookModelMock.mockBookWithIdToLoanBook())
                .localDate(LocalDate.now())
                .returned(false)
                .build();
    }
}
