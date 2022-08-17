package com.libraryapi.service;

import com.libraryapi.exception.BusinessException;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.repository.BookRepository;
import com.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// TESTE UNITARIO
class BookServiceTest {

    private BookServices bookServices;

    @MockBean // Muito utilizado para Repository, irá mockar pelo Spring (simular)
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        this.bookServices = new BookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void saveBookTest() {
        // Cenario
        var book = BookModelMock.getBookMockNotId();
        when(bookRepository.save(book)).thenReturn(BookModelMock.getBookMockWithId());
        when(bookRepository.existsByIsbn(anyString())).thenReturn(Boolean.FALSE);

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
        when(bookRepository.existsByIsbn(anyString())).thenReturn(Boolean.TRUE);

        // Execução
        var exception = catchThrowable(() -> bookServices.save(book));

        // Verificação
        assertThat(exception).isInstanceOf(BusinessException.class).hasMessage("ISBN já cadastrado");

        verify(bookRepository, never()).save(book);
    }

    @Test
    @DisplayName("Deve obter um livro por ID")
    void getByIdTest() {
        // Cenario
        Long id = 10L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(BookModelMock.getMockBook()));

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
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        //Execução
        var book = bookServices.getById(id);

        //Verificações
        assertTrue(book.isEmpty());
    }

    @Test
    @DisplayName("Deve deletar um livro")
    void deleteBookTest() {
        // Cenario
        var book = BookModelMock.getBookMockWithId();

        // Execução
        Assertions.assertDoesNotThrow(() -> bookServices.delete(book));

        // Verificação
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve ocorre erro ao tentar deletar um livro inexistente.")
    void deleteInvalidBookTest() {
        // Cenario
        var book = BookModelMock.getBookMockNotId();

        // Execução
        Assertions.assertThrows(IllegalArgumentException.class, () -> bookServices.delete(book));

        // Verificação
        verify(bookRepository, never()).delete(book);
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    void updateBookTest() {
        // Cenario
        var bookToUpdate = BookModelMock.getMockBook();

        when(bookRepository.save(bookToUpdate)).thenReturn(BookModelMock.mockZeldaUpdateBook());

        // Execução
        var updateBook = bookServices.update(bookToUpdate);

        // Verificação
        assertThat(updateBook.getId()).isEqualTo(BookModelMock.mockZeldaUpdateBook().getId());
        assertThat(updateBook.getTitle()).isEqualTo(BookModelMock.mockZeldaUpdateBook().getTitle());
        assertThat(updateBook.getAuthor()).isEqualTo(BookModelMock.mockZeldaUpdateBook().getAuthor());
        assertThat(updateBook.getIsbn()).isEqualTo(BookModelMock.mockZeldaUpdateBook().getIsbn());
    }

    @Test
    @DisplayName("Deve filtrar livros pelas propriedades")
    void findBooksTest() {
        // Cenario
        var pageRequest = PageRequest.of(0, 10);
        var books = List.of(BookModelMock.getBookMockWithId());
        var page = new PageImpl<>(books, pageRequest, 1);

        when(bookRepository.findAll(any(Example.class), any(PageRequest.class))).thenReturn(page);

        // Execução
        var result = bookServices.find(BookModelMock.getBookMockWithId(), pageRequest);

        // Verificação
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(books);
        assertThat(result.getPageable().getPageNumber()).isZero();
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("Deve opter um livro pelo isbn")
    void getBookByIsbn() {
        // Cenario
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(BookModelMock.getMockBook()));

        // Execução
        var result = bookServices.getBookByIsbn(BookModelMock.getMockBook().getIsbn());

        // Verificação
        assertThat(result).isPresent();
    }
}
