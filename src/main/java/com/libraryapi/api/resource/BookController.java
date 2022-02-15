package com.libraryapi.api.resource;

import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    BookServices bookServices;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookModel create(@RequestBody BookDTO bookDTO) {
        var entity = bookServices.save(BookModel.builder()
                .author(bookDTO.getAuthor())
                .title(bookDTO.getTitle())
                .isbn(bookDTO.getIsbn())
                .build());

        return BookModel.builder()
                .id(entity.getId())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .build();
    }
}
