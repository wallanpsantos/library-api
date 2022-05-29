package com.libraryapi.service.impl;

import com.libraryapi.api.dto.LoanFilterDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.repository.LoanRepository;
import com.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public LoanModel update(LoanModel loanModel) {
        if (Objects.isNull(loanModel)) {
            throw new IllegalArgumentException("Parametro do tipo não Loan");
        }
        return loanRepository.save(loanModel);
    }

    @Override
    public Page<LoanModel> find(LoanFilterDTO filterDTO, Pageable page) {
        return loanRepository.findByIsbnOrCustomer(filterDTO.getIsbn(), filterDTO.getCustomer(), page);
    }

    @Override
    public Page<LoanModel> getLoansByBook(BookModel book, Pageable page) {
        return loanRepository.findByBook(book, page);
    }
}
