package com.libraryapi.api.resource;

import com.libraryapi.api.dto.LoanDTO;
import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
public class LoanController {

    private LoanService loanService;
    private BookServices bookServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        var book = bookServices.getBookByIsbn(dto.getIsbn()).get();
        var entity = LoanModel.builder()
                .customer(dto.getCustomer())
                .book(book)
                .localDate(LocalDate.now())
                .build();

        entity = loanService.save(entity);

        return entity.getId();
    }

}
