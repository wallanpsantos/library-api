package com.libraryapi.api.resource;

import com.libraryapi.api.dto.BookDTO;
import com.libraryapi.api.model.entity.BookModel;
import com.libraryapi.service.BookServices;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private BookServices bookServices;
    private ModelMapper modelmapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        var entity = modelmapper.map(bookDTO, BookModel.class);

        entity = bookServices.save(entity);

        return modelmapper.map(entity, BookDTO.class);
    }
}