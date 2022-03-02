package com.libraryapi.service;

import com.libraryapi.api.model.entity.BookModel;

import java.util.Optional;

public interface BookServices {
    BookModel save(BookModel any);
        Optional<BookModel> getById(Long id);

}
