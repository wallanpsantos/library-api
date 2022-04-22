package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.BookServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookServices {

    private final BookRepository bookRepository;

    @Override
    public BookModel save(BookModel bookModel) {
        if (bookRepository.existsByIsbn(bookModel.getIsbn())) {
            throw new BusinessException("ISBN já cadastrado");
        }
        return bookRepository.save(bookModel);
    }

    @Override
    public Optional<BookModel> getById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public void delete(BookModel book) {
        if (Objects.isNull(book) || Objects.isNull(book.getId())) {
            throw new IllegalArgumentException("Erro ao deletar book");
        }
        this.bookRepository.delete(book);
    }

    @Override
    public BookModel update(BookModel book) {
        if (Objects.isNull(book) || Objects.isNull(book.getId())) {
            throw new IllegalArgumentException("Erro ao atualizar book");
        }
        return this.bookRepository.save(book);
    }

    @Override
    public Page<BookModel> find(BookModel filterDTO, Pageable pageRequest) {
        Example<BookModel> example = Example.of(filterDTO,
                ExampleMatcher
                        .matching()
                        .withIgnoreCase()
                        .withIgnoreNullValues()
                        /* .withStringMatcher(ExampleMatcher.StringMatcher.) -> Comparar pelo inicio, pelo fim,
                        exatamente o valor, ou qualquer parte do valor/texto passado */
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );
        return bookRepository.findAll(example, pageRequest);
    }

    @Override
    public Optional<BookModel> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}
