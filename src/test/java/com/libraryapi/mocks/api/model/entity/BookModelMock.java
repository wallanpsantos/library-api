package com.libraryapi.mocks.api.model.entity;

import com.libraryapi.api.model.entity.BookModel;

public class BookModelMock {

    public static BookModel getMockBook() {
        return BookModel.builder()
                .id(10L)
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();
    }

    public static BookModel getBookMockWithId() {
        return BookModel.builder()
                .id(10L)
                .author("Yoshi")
                .title("Yoshi Adventure")
                .isbn("360")
                .build();
    }

    public static BookModel getBookMockNotId() {
        return BookModel.builder()
                .author("Mario")
                .title("Yoshi Adventure")
                .isbn("360")
                .build();
    }

    public static BookModel mockZeldaUpdateBook() {
        return BookModel.builder()
                .id(10L)
                .author("Zelda")
                .title("The Legend of Zelda")
                .isbn("N-SWITCH")
                .build();
    }

    public static BookModel mockBookWithIdToLoanBook() {
        return BookModel.builder()
                .id(1L)
                .author("Eker,T. Harv")
                .title("Os Segredos da Mente Milionária")
                .isbn("8575422391")
                .build();
    }

}
