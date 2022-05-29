package com.libraryapi.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanModel {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String customer;

    @Column(name = "customer_email")
    private String email;

    @JoinColumn(name = "id_book")
    @ManyToOne
    private BookModel book;

    @Column
    private LocalDate loanDate;

    @Column
    private Boolean returned;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        LoanModel loanModel = (LoanModel) o;
        return id != null && Objects.equals(id, loanModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
