package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
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

    @Override
    public void delete(BookModel book) {
        if (Objects.isNull(book)) {
            throw new BusinessException("Erro ao deletar book");
        }
    }

    @Override
    public BookModel update(BookModel book) {
        return null;
    }
}
