package com.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.api.dto.LoanFilterDTO;
import com.libraryapi.api.dto.LoanReturnedDTO;
import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.mocks.api.dto.LoanDTOMock;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.mocks.api.model.entity.LoanModelMock;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.LoanService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest // Para REST
@AutoConfigureMockMvc // Para REST
// TESTE UNITÁRIO
@DisplayName("Teste unitário end point de emprestimos ")
class LoanControllerTest {

    static final String LOAN_API = "/api/loans";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServices bookServices;

    @MockBean
    private LoanService loanService;

    @Test
    @DisplayName("Deve realizar um emprestimo de livro")
    void createLoanTest() throws Exception {
        // Cenario
        var json = new ObjectMapper().writeValueAsString(LoanDTOMock.createMock());

        BDDMockito.given(bookServices.getBookByIsbn(LoanDTOMock.createMock().getIsbn()))
                .willReturn(Optional.of(BookModelMock.mockBookWithIdToLoanBook()));

        BDDMockito.given(loanService.save(any(LoanModel.class))).willReturn(LoanModelMock.get());

        // Execução
        var request = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string(BookModelMock.mockBookWithIdToLoanBook().getId().toString()));
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar fazer um emprestimo de livro inexistente.")
    void invalidIsbnCreateLoanTest() throws Exception {
        // Cenario
        var json = new ObjectMapper().writeValueAsString(LoanDTOMock.createMock());

        BDDMockito.given(bookServices.getBookByIsbn(LoanDTOMock.createMock().getIsbn())).willReturn(Optional.empty());
        // Execução
        var request = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Book not found for passed isbn"));
    }


    @Test
    @DisplayName("Deve retornar erro ao tentar fazer um emprestimo de livro já emprestado.")
    void loanedBookErrorOnCreateLoanTest() throws Exception {
        // Cenario
        var json = new ObjectMapper().writeValueAsString(LoanDTOMock.createMock());

        BDDMockito.given(bookServices.getBookByIsbn(LoanDTOMock.createMock().getIsbn()))
                .willReturn(Optional.of(BookModelMock.mockBookWithIdToLoanBook()));

        BDDMockito.given(loanService.save(any(LoanModel.class))).willThrow(new BusinessException("Book already loaned"));
        // Execução
        var request = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errors", hasSize(1)))
                .andExpect(jsonPath("errors[0]").value("Book already loaned"));
    }

    @Test
    @DisplayName("Deve retornar um livro")
    void returnedTrueBookTest() throws Exception {
        // Cenario
        var returnedDTO = new LoanReturnedDTO(true);
        var loan = LoanModelMock.get();

        var json = new ObjectMapper().writeValueAsString(returnedDTO);

        BDDMockito.given(loanService.getById(anyLong())).willReturn(Optional.of(loan));

        // Execução e Verificação
        mockMvc.perform(
                MockMvcRequestBuilders.patch(LOAN_API.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isOk());

        verify(loanService, times(1)).update(loan);
    }

    @Test
    @DisplayName("Deve retornar 404 quando tentar devolver um livro inexistente.")
    void returnedInexistentBookTest() throws Exception {
        // Cenario
        var returnedDTO = new LoanReturnedDTO(true);

        var json = new ObjectMapper().writeValueAsString(returnedDTO);

        BDDMockito.given(loanService.getById(anyLong())).willReturn(Optional.empty());

        // Execução e Verificação
        mockMvc.perform(
                MockMvcRequestBuilders.patch(LOAN_API.concat("/1"))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve filtrar livros.")
    void findLoansTest() throws Exception {
        // Cenario
        var loan = LoanModelMock.get();

        BDDMockito.given(loanService.find(any(LoanFilterDTO.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(loan), PageRequest.of(0, 100), 1));

        var queryString = String.format("?isbn=%s&customer=%s&page=0&size=10",
                loan.getBook().getIsbn(), loan.getCustomer());

        // Execução e Verificação
        mockMvc.perform(
                        MockMvcRequestBuilders.get(LOAN_API.concat(queryString))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("content", Matchers.hasSize(1)))
                .andExpect(jsonPath("totalElements").value(1))
                .andExpect(jsonPath("pageable.pageSize").value(10));
    }
}
