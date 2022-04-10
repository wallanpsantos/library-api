package com.libraryapi.repository;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.api.model.entity.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanModel, String> {

    boolean existsByBook(BookModel book);
}
