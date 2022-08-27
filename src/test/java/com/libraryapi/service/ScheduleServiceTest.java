package com.libraryapi.service;

import com.libraryapi.api.model.entity.LoanModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private LoanService loanService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    @DisplayName("Should send an email to all overdue loans")
    void sendMailToOverdueLoansShouldSendEmailToAllOverdueLoans() {
        var loan = LoanModel.builder()
                .customer("Fulano")
                .email("fulano@gmail.com")
                .loanDate(LocalDate.now().minusDays(7))
                .returned(false)
                .build();

        when(loanService.getOverdueLoans()).thenReturn(List.of(loan));

        scheduleService.sendMailToOverdueLoans();

        verify(emailService).sendEmail("Email enviado!", List.of("fulano@gmail.com"));
    }
}