package com.libraryapi.api.resource;

import com.libraryapi.api.dto.LoanDTO;
import com.libraryapi.api.dto.LoanFilterDTO;
import com.libraryapi.api.dto.LoanReturnedDTO;
import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.LoanService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
public class LoanController {

    private LoanService loanService;
    private BookServices bookServices;

    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto) {
        var book = bookServices.getBookByIsbn(dto.getIsbn())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn"));

        var entity = LoanModel.builder()
                .customer(dto.getCustomer())
                .book(book)
                .localDate(LocalDate.now())
                .build();

        entity = loanService.save(entity);

        return entity.getId();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void returnBook(@PathVariable Long id, @RequestBody LoanReturnedDTO loanReturnedDTO) {
        var loan = loanService.getById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found for passed id"));

        loan.setReturned(loanReturnedDTO.getReturned());
        loanService.update(loan);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<LoanDTO> find(LoanFilterDTO filterDTO, Pageable page) {

        var result = loanService.find(filterDTO, page);

        var loans = result.getContent().stream().map(entity -> {

//            var book = entity.getBook();
//            var bookDTO = modelMapper.map(book, BookDTO.class);
//            var loanDTO = modelMapper.map(entity, LoanDTO.class);
//            loanDTO.setBook(bookDTO);
//            return loanDTO;

            return modelMapper.map(entity, LoanDTO.class);
        }).collect(Collectors.toList());

        return new PageImpl<>(loans, page, result.getTotalElements());
    }
}
