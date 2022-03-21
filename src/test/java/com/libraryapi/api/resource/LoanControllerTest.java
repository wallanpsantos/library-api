package com.libraryapi.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.mocks.api.dto.LoanDTOMock;
import com.libraryapi.mocks.api.model.entity.BookModelMock;
import com.libraryapi.mocks.api.model.entity.LoanModelMock;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.LoanService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
// TESTE UNITÁRIO
public class LoanControllerTest {

    static final String LOAN_API = "/api/loans";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookServices bookServices;

    @MockBean
    LoanService loanService;

    @Test
    @DisplayName("Deve realizar um emprestimo de livro")
    void createLoanTest() throws Exception {
        // Cenario
        var json = new ObjectMapper().writeValueAsString(LoanDTOMock.createMock());

        BDDMockito.given(bookServices.getBookByIsbn(LoanDTOMock.createMock().getIsbn()))
                .willReturn(Optional.of(BookModelMock.mockBookWithIdToLoanBook()));

        BDDMockito.given(loanService.save(Mockito.any(LoanModel.class))).willReturn(LoanModelMock.getMock());

        // Execução
        var request = MockMvcRequestBuilders.post(LOAN_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Verificação
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(BookModelMock.mockBookWithIdToLoanBook().getId()));


    }
}
