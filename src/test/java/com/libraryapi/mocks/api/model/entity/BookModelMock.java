package com.libraryapi.mocks.api.model.entity;

import com.libraryapi.api.model.entity.BookModel;

public class BookModelMock {

    public static BookModel getMock() {
        return BookModel.builder()
                .id(10L)
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();
    }
}
