package com.libraryapi.service;

import com.libraryapi.api.model.entity.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookServices {

    BookModel save(BookModel bookModel);

    Optional<BookModel> getById(Long id);

    void delete(BookModel book);

    BookModel update(BookModel book);

    Page<BookModel> find(BookModel filter, Pageable pageRequest);

    Optional<BookModel> getBookByIsbn(String isbn);
}
