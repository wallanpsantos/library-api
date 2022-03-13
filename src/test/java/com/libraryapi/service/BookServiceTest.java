package com.libraryapi.service;

import com.libraryapi.exception.BusinessException;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        var book = BookModelMock.getBookMockNotId();
        Mockito.when(bookRepository.save(book)).thenReturn(BookModelMock.getBookMockWithId());
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(Boolean.FALSE);

        // Execução
        var savedBook = bookServices.save(book);

        // Validação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getAuthor()).isEqualTo("Yoshi");
        assertThat(savedBook.getTitle()).isEqualTo("Yoshi Adventure");
        assertThat(savedBook.getIsbn()).isEqualTo("360");
    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com ISBN duplicado")
    void shouldNotSaveABookWithDuplicatedISBN() {
        // Cenario
        var book = BookModelMock.getBookMockNotId();
        Mockito.when(bookRepository.existsByIsbn(Mockito.anyString())).thenReturn(Boolean.TRUE);

        // Execução
        var exception = catchThrowable(() -> bookServices.save(book));

        // Verificação
        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("ISBN já cadastrado");

        Mockito.verify(bookRepository, Mockito.never()).save(book);
    }

    @Test
    @DisplayName("Deve obter um livro por ID")
    void getByIdTest() {
        // Cenario
        Long id = 10L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.of(BookModelMock.getMockBook()));

        //Execução
        var foundBook = bookServices.getById(id);

        //Verificações
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getId()).isEqualTo(BookModelMock.getMockBook().getId());
        assertThat(foundBook.get().getAuthor()).isEqualTo(BookModelMock.getMockBook().getAuthor());
        assertThat(foundBook.get().getTitle()).isEqualTo(BookModelMock.getMockBook().getTitle());
        assertThat(foundBook.get().getIsbn()).isEqualTo(BookModelMock.getMockBook().getIsbn());

    }


    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por Id quando ele não existe na base")
    void bookNotFoundtByIdTest() {
        // Cenario
        Long id = 10L;
        Mockito.when(bookRepository.findById(id)).thenReturn(Optional.empty());

        //Execução
        var book = bookServices.getById(id);

        //Verificações
        assertTrue(book.isEmpty());
    }
}
