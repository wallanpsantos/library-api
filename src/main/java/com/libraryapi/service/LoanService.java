package com.libraryapi.service;

import com.libraryapi.api.model.entity.LoanModel;

import java.util.Optional;

public interface LoanService {

    LoanModel save(LoanModel loanModel);

    Optional<LoanModel> getById(Long id);

    LoanModel update(LoanModel loan);
}
