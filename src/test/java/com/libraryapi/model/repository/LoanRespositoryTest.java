package com.libraryapi.model.repository;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class LoanRespositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve verificar se existe emprestimo não devolvido para o livro")
    void existsBookAndNotReturnedTest() {
        // Cenario
        var book = BookModelMock.getBookMockNotId();
        entityManager.persist(book);

        var loan = LoanModel.builder().book(book).customer("Yoshi Adventure").localDate(LocalDate.now()).build();
        entityManager.persist(loan);

        // Execução
        var exists = loanRepository.existsByBookAndNotReturned(book);

        // Verificação
        assertThat(exists).isTrue();

    }
}
