package com.libraryapi.model.repository;

import com.libraryapi.api.model.entity.BookModel;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest // Para teste de integração com banco de dados utiliza o H2 em memoria para o teste
class BookRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com isbn informado")
    void returnTrueWhenIsbnExists() {
        //  Cenario
        String isbn = "360";
        entityManager.persist(BookModelMock.getBookMockNotId());

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

    @Test
    @DisplayName("Deve obter um livro por ID")
    void findById() {
        //  Cenario
        var resultEntity = entityManager.persist(BookModelMock.getBookMockNotId());

        //  Execução
        var exists = bookRepository.findById(resultEntity.getId());

        //  Verificação
        assertTrue(exists.isPresent());
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void saveBookTest() {
        // Cenario e Execução
        var savedBook = bookRepository.save(BookModelMock.getBookMockNotId());

        // Verificação
        assertThat(savedBook.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve deletar um livro")
    void deleteBookTest() {
        // Cenario
        var resultPersist = entityManager.persist(BookModelMock.getBookMockNotId());
        var foundBook = entityManager.find(BookModel.class, resultPersist.getId());

        // Execução
        bookRepository.delete(foundBook);

        // Verificação
        var deletedBook = entityManager.find(BookModel.class, resultPersist.getId());

        assertThat(deletedBook).isNull();
    }

}
