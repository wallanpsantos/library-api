package com.libraryapi.api.service;

import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BookServices bookServices;

    @MockBean // Muito utilizado para Repository, irá mockar pelo Spring (simular)
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        this.bookServices = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void saveBookTest() {
        // Cenario
        var book = BookModelMock.getSaveBookMockNotId();
        Mockito.when(bookRepository.save(book)).thenReturn(BookModelMock.getSaveBookMockWithId());

        // Execução
        var savedBook = bookServices.save(book);

        // Validação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getAuthor()).isEqualTo("Yoshi");
        assertThat(savedBook.getTitle()).isEqualTo("Yoshi Adventure");
        assertThat(savedBook.getIsbn()).isEqualTo("360");
    }
}
