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

    @Query(value = " select L from LoanModel as L join L.book as B where B.isbn = :isbn or L.customer = :customer")
    Page<LoanModel> findByIsbnOrCustomer(@Param("isbn") String isbn,
                                         @Param("customer") String customer,
                                         Pageable pageable);

    @Query( value = " select l from LoanModel as l join l.book as b where b.isbn = :isbn or l.customer =:customer ")
    Page<LoanModel> findByBookIsbnOrCustomer(
            @Param("isbn") String isbn,
            @Param("customer") String customer,
            Pageable pageable
    );


}
