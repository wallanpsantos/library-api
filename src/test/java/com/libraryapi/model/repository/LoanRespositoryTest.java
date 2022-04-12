package com.libraryapi.model.repository;

import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.mocks.api.model.entity.LoanModelMock;
import com.libraryapi.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRespositoryTest {

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

        var loan = LoanModelMock.getNotIdLoanAndBook();
        entityManager.persist(loan);

        // Execução


        // Verificação

    }
}
