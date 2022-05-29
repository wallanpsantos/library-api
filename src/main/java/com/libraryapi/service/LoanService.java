package com.libraryapi.service;

import com.libraryapi.api.dto.LoanFilterDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.api.model.entity.LoanModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LoanService {

    LoanModel save(LoanModel loanModel);

    Optional<LoanModel> getById(Long id);

    LoanModel update(LoanModel loan);

    Page<LoanModel> find(LoanFilterDTO filterDTO, Pageable page);

    Page<LoanModel> getLoansByBook(BookModel book, Pageable page);

    List<LoanModel> getOverdueLoans();
}
