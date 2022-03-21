package com.libraryapi.api.resource;

import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.exception.ApiErrors;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@AllArgsConstructor
public class BookController {

    private BookServices bookServices;
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
        var entity = modelMapper.map(bookDTO, BookModel.class);

        entity = bookServices.save(entity);

        return modelMapper.map(entity, BookDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO getBookDetails(@PathVariable Long id) {

        var book = bookServices.getById(id);

        return book.map(bookModel -> modelMapper.map(bookModel, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        var book = bookServices.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        bookServices.delete(book);
    }

    @PutMapping("/{id}")
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

        var list = result.stream().map(bookModel -> modelMapper.map(bookModel, BookDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(list, pageRequest, result.getTotalPages());
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerIllegalArgumentException(IllegalArgumentException ex) {
        return new ApiErrors(ex);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerBusinessException(BusinessException ex) {
        return new ApiErrors(ex);
    }
}
