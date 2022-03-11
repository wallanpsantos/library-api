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

    public static BookDTO mockZeldaUpdateBook() {
        return BookDTO.builder()
                .author("Zelda")
                .title("The Legend of Zelda")
                .isbn("N-SWITCH")
                .build();
    }
}
