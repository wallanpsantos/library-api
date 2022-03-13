package com.libraryapi.repository;

import com.libraryapi.api.model.entity.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookModel, Long> {

    boolean existsByIsbn(String isbn);

    @Override
    Optional<BookModel> findById(Long aLong);
}
