package com.libraryapi.repository;

import com.libraryapi.api.model.entity.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel, Long> {

    boolean existsByIsbn(String isbn);

    @Override
    Optional<BookModel> findById(Long id);

    Optional<BookModel> findByIsbn(String isbn);
}
