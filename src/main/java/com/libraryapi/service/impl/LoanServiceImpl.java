package com.libraryapi.service.impl;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.LoanRepository;
import com.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public LoanModel save(LoanModel loanModel) {

        if (Objects.isNull(loanModel)) {
            throw new IllegalArgumentException("Parametro do tipo não Loan");
        }

        if (loanRepository.existsByBookAndNotReturned(loanModel.getBook())) {
            throw new BusinessException("Book already loaned");
        }

        return loanRepository.save(loanModel);
    }

    @Override
    public Optional<LoanModel> getById(Long id) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Parametro do tipo não long");
        }
        return loanRepository.findById(id);
    }

    @Override
    public LoanModel update(LoanModel loan) {
        return loanRepository.save(loan);
    }
}
