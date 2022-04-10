package com.libraryapi.service;

import com.libraryapi.mocks.api.model.entity.LoanModelMock;
import com.libraryapi.repository.LoanRepository;
import com.libraryapi.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
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

        // Execução
        var result = loanService.save(loan);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(LoanModelMock.get().getId());
        assertThat(result.getBook().getId()).isEqualTo(LoanModelMock.get().getBook().getId());
    }
}
