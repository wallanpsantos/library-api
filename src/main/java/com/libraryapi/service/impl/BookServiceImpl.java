package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookServices {

    private BookRepository repository;

    @Override
    public BookModel save(BookModel bookModel) {
        if (repository.existsByIsbn(bookModel.getIsbn())) {
            throw new BusinessException("ISBN já cadastrado");
        }
        return repository.save(bookModel);
    }

    @Override
    public Optional<BookModel> getById(Long id) {
        return Optional.empty();
    }
}
