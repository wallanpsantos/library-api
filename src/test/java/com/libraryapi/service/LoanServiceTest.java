package com.libraryapi.service;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.mocks.api.dto.LoanFilterDTOMock;
import com.libraryapi.mocks.api.model.entity.LoanModelMock;
import com.libraryapi.repository.LoanRepository;
import com.libraryapi.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// TESTE UNITARIO
class LoanServiceTest {

    LoanService loanService;

    @MockBean // Muito utilizado para Repository, irá mockar pelo Spring (simular)
    LoanRepository loanRepository;

    @BeforeEach
    void setUp() {
        this.loanService = new LoanServiceImpl(loanRepository);
    }

    @Test
    @DisplayName("Deve salvar um emprestimo")
    void saveLoanTest() {
        // Canario
        var loan = LoanModelMock.getNotId();
        when(loanRepository.save(loan)).thenReturn(LoanModelMock.get());
        when(loanRepository.existsByBookAndNotReturned(loan.getBook())).thenReturn(false);

        // Execução
        var result = loanService.save(loan);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(LoanModelMock.get().getId());
        assertThat(result.getBook().getId()).isEqualTo(LoanModelMock.get().getBook().getId());
    }

    @Test
    @DisplayName("Deve lançar erro de negócio ao tentar salvar um livro já emprestado")
    void loanedBookSaveTest() {

        // Canario e Execução
        var loan = LoanModelMock.getNotId();
        when(loanRepository.existsByBookAndNotReturned(loan.getBook())).thenReturn(true);

        var exception = catchThrowable(() -> loanService.save(loan));

        // Verificação
        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("Book already loaned");

        verify(loanRepository, never()).save(LoanModelMock.get());
    }

    @Test
    @DisplayName("Deve obter as informações de um empréstimo pelo ID")
    void getLoanDetailsTest() {
        // Canario
        var loan = LoanModelMock.get();

        when(loanRepository.findById(loan.getId())).thenReturn(Optional.of(loan));

        // Execução
        var result = loanService.getById(loan.getId());

        // Verificação
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(loan.getId());
        assertThat(result.get().getCustomer()).isEqualTo(loan.getCustomer());

        verify(loanRepository, times(1)).findById(loan.getId());
    }

    @Test
    @DisplayName("Deve atualizar um emprestimo")
    void updateLoanTest() {
        // Canario
        var loan = LoanModelMock.get();

        when(loanRepository.save(any(LoanModel.class))).thenReturn(loan);

        // Execução
        var updating = loanService.update(loan);

        // Verificação
        assertThat(updating.getReturned()).isTrue();
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    @DisplayName("Deve filtrar empréstimos pelas propriedades")
    void findLoansTest() {
        // cenario
        var pageRequest = PageRequest.of(0, 10);
        var list = List.of(LoanModelMock.get());

        Page<LoanModel> page = new PageImpl<>(list, pageRequest, list.size());

        when(loanRepository.findByBookIsbnOrCustomer(anyString(), anyString(), any(PageRequest.class))).thenReturn(page);

        // Execução
        Page<LoanModel> result = loanService.find(LoanFilterDTOMock.get(), pageRequest);

        // Verificação
        assertThat(result.getTotalElements()).isEqualTo(1);

    }
}

















