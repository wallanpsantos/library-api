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
        return this.repository.findById(id);
    }

    @Override
    public void delete(BookModel book) {
        if (Objects.isNull(book) || Objects.isNull(book.getId())) {
            throw new IllegalArgumentException("Erro ao deletar book");
        }
        this.repository.delete(book);
    }

    @Override
    public BookModel update(BookModel book) {
        if (Objects.isNull(book) || Objects.isNull(book.getId())) {
            throw new IllegalArgumentException("Erro ao atualizar book");
        }
        return this.repository.save(book);
    }
}
