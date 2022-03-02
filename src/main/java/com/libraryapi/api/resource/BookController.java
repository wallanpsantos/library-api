package com.libraryapi.api.resource;

import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.exception.ApiErrors;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.exception.BusinessException;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private BookServices bookServices;
    private ModelMapper modelmapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO bookDTO) {
        var entity = modelmapper.map(bookDTO, BookModel.class);

        entity = bookServices.save(entity);

        return modelmapper.map(entity, BookDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookDTO getBookDetails(@PathVariable Long id) {

        var book = bookServices.getById(id);

        return book.map(bookModel -> modelmapper.map(bookModel, BookDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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
