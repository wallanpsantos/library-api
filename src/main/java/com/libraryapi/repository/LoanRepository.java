package com.libraryapi.repository;

import com.libraryapi.api.model.entity.LoanModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanModel, String> {
}
