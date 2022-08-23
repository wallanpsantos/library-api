package com.libraryapi.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    @DisplayName("Should send an email to the list of emails")
    void sendEmailShouldSendAnEmailToTheListOfEmails() {
        var emails = List.of("email1@email.com", "email2@email.com");
        var message = "message";

        emailService.sendEmail(message, emails);

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}