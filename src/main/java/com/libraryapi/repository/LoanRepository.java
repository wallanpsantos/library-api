package com.libraryapi.repository;

import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.api.model.entity.LoanModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanRepository extends JpaRepository<LoanModel, Long> {

    @Query(value = " select case when ( count(l.id) > 0 ) then true else false end " +
            " from LoanModel l where l.book = :book and ( l.returned is null or l.returned is false ) ")
    boolean existsByBookAndNotReturned(@Param("book") BookModel book);

    Page<LoanModel> findByBookIsbnOrCustomer(String isbn, String customer, Pageable pageable);
}
