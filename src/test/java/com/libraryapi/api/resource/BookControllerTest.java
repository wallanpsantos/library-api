package com.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.mocks.api.dto.BookDTOMock;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.service.BookServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
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

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
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

        String json = new ObjectMapper().writeValueAsString(BookDTOMock.getMock());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Meu Livro"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Autor"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value("1213212"));

    }

    @Test
    @DisplayName("Deve lançar erro quando nao tiver dados suficientes para criação do livro.")
    void createInvalidBookTest() {

    }
}
