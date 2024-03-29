package com.libraryapi.service;

import com.libraryapi.api.model.entity.LoanModel;
import com.libraryapi.enums.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    /* cron = " Second Minute Hour Day Month Year " */
    private static final String CRON_OVERDUE_LOANS = "0 0 0 1/1 * ?";

    private final LoanService loanService;
    private final EmailService emailService;

    @Scheduled(cron = CRON_OVERDUE_LOANS)
    public void sendMailToOverdueLoans() {
        var emails = loanService.getOverdueLoans().stream()
                .map(LoanModel::getEmail)
                .collect(Collectors.toList());
        emailService.sendEmail(MessagesEnum.EMAIL_ENVIADO.message, emails);

    }
}
