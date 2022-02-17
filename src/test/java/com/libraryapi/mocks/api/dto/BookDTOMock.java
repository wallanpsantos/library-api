package com.libraryapi.mocks.api.dto;

import com.libraryapi.api.dto.BookDTO;

public class BookDTOMock {

    public static BookDTO getMock() {
        return BookDTO.builder()
                .author("Autor")
                .title("Meu Livro")
                .isbn("1213212")
                .build();
    }
}
