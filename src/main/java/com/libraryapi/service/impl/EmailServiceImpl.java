package com.libraryapi.service.impl;

import com.libraryapi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${mail.default-remetent}")
    private final String remetent;

    @Value("${loan.overdue.message}")
    private final String message;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String message, List<String> emails) {

        var mails = emails.toArray(new String[0]);

        var setupMailMessage = new SimpleMailMessage();
        setupMailMessage.setFrom(remetent);
        setupMailMessage.setSubject("Livro com empréstimo atrasado!");
        setupMailMessage.setText(message);
        setupMailMessage.setTo(mails);

        javaMailSender.send(setupMailMessage);

    }
}
