package com.libraryapi.service;

import com.libraryapi.api.model.entity.LoanModel;

public interface LoanService {

    LoanModel save(LoanModel loanModel);
    
}
