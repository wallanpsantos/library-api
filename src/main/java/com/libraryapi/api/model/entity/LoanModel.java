package com.libraryapi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanModel {

    private Long id;
    private String customer;
    private BookModel book;
    private LocalDate localDate;
    private Boolean returned;
}
