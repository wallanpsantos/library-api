package com.libraryapi.repository;

import com.libraryapi.api.model.entity.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
}
