package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<BookModel> find(BookModel filter, Pageable pageRequest) {
        Example<BookModel> example = Example.of(filter,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        /* .withStringMatcher(ExampleMatcher.StringMatcher.) -> Comparar pelo inicio, pelo fim,
                        exatamente o valor, ou qualquer parte do valor/texto passado */
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );
        return repository.findAll(example, pageRequest);
    }

    @Override
    public Optional<BookModel> getBookByIsbn(String isbn) {
        return repository.findByIsbn(isbn);
    }
}
