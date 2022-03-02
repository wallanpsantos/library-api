package com.libraryapi.model.repository;

import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest // Para teste de integração com banco de dados utiliza o H2 em memoria para o teste
class BookRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    void returnTrueWhenIsbnExists() {
        //  Cenario
        String isbn = "360";
        testEntityManager.persist(BookModelMock.getBookMockNotId());

        //  Execução
        boolean exists = bookRepository.existsByIsbn(isbn);

        //  Verificação
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    void returnFalseWhenIsbnDoesNotExists() {
        //  Cenario
        String isbn = "360";

        //  Execução
        boolean exists = bookRepository.existsByIsbn(isbn);

        //  Verificação
        assertThat(exists).isFalse();
    }


}
