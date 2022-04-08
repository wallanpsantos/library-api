package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.repository.LoanRepository;
import com.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanModel save(LoanModel loanModel) {
        if (Objects.isNull(loanModel)) {
            throw new IllegalArgumentException("Objeto nulo passado no parametro");
        }
        return loanRepository.save(loanModel);

    }
}