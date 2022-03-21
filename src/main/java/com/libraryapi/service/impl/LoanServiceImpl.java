package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {

    @Override
    public LoanModel save(LoanModel loanModel) {
        return loanModel;
    }
}
