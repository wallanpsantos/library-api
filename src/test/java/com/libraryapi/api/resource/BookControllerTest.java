package com.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.mocks.api.dto.BookDTOMock;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.service.BookServices;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest // Para REST
@AutoConfigureMockMvc // Para REST
// TESTE UNITARIO
class BookControllerTest {

    static String BOOK_API = "/api/books";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookServices bookServices;

    @Test
    @DisplayName("Deve criar um livro com sucesso.")
    void createBookTest() throws Exception {

        BDDMockito.given(bookServices.save(any(BookModel.class))).willReturn(BookModelMock.getMock());

        var json = new ObjectMapper().writeValueAsString(BookDTOMock.getMock());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value(BookDTOMock.getMock().getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value(BookDTOMock.getMock().getAuthor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(BookDTOMock.getMock().getIsbn()));

    }

    @Test
    @DisplayName("Deve lançar erro quando nao tiver dados suficientes para criação do livro.")
    void createInvalidBookTest() throws Exception {

        var json = new ObjectMapper().writeValueAsString(new BookDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", hasSize(1)));
    }

    @Test
    @DisplayName("Deve lançar erro quando tentar cadastrar um livro com ISBN já utilizado.")
    void createBookWithDuplicatedIsbn() throws Exception {

        var messageError = "ISBN já cadastrado";

        BDDMockito.given(bookServices.save(Mockito.any(BookModel.class))).willThrow(new BusinessException(messageError));

        var json = new ObjectMapper().writeValueAsString(BookDTOMock.getMock());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value(messageError));

    }

}
