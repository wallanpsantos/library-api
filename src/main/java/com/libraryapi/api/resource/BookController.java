package com.libraryapi.api.resource;

import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.dto.LoanDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.service.BookServices;
import com.libraryapi.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Api("Endpoint to Books")
@Slf4j
public class BookController {

    private final BookServices bookServices;
    private final ModelMapper modelMapper;
    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a book")
    @ApiResponses({
            @ApiResponse(code = 401, message = "Unauthorized request, verify credentials.")
    })
    public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
        log.info("Create a book with Author: {} and Tittle book: {}", bookDTO.getAuthor(), bookDTO.getTitle());

        var entity = modelMapper.map(bookDTO, BookModel.class);

        entity = bookServices.save(entity);

        return modelMapper.map(entity, BookDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search details of a book by ID")
    public BookDTO getBookDetails(@PathVariable Long id) {

        var book = bookServices.getById(id);

        return book.map(bookModel -> modelMapper.map(bookModel, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a book by ID")
    public void deleteBook(@PathVariable Long id) {
        var book = bookServices.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookServices.delete(book);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a past book Author and Title")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {

        return bookServices.getById(id).map(book -> {
            book.setAuthor(bookDTO.getAuthor());
            book.setTitle(bookDTO.getTitle());
            book = bookServices.update(book);

            return modelMapper.map(book, BookDTO.class);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Page<BookDTO> find(BookDTO bookDTO, Pageable pageRequest) {
        var filter = modelMapper.map(bookDTO, BookModel.class);

        var result = bookServices.find(filter, pageRequest);

        var list = result.getContent()
                .stream()
                .map(bookModel -> modelMapper.map(bookModel, BookDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalPages());
    }

    @GetMapping("{id}/loans")
    public Page<LoanDTO> loansByBook(@PathVariable Long id, Pageable page) {

        var book = bookServices.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var result = loanService.getLoansByBook(book, page);

        var list = result.getContent()
                .stream()
                .map(loanModel -> {
                    var loanBook = loanModel.getBook();
                    var bookDTO = modelMapper.map(loanBook, BookDTO.class);
                    var loanDTO = modelMapper.map(loanModel, LoanDTO.class);
                    loanDTO.setBookDTO(bookDTO);
                    return loanDTO;
                })
                .collect(Collectors.toList());

        return new PageImpl<>(list, page, result.getTotalElements());
    }

}
