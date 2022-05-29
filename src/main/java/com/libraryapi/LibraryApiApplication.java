package com.libraryapi;

import com.libraryapi.service.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class LibraryApiApplication {

    @Value("${loan.overdue.message}")
    private String message;
    @Autowired
    private EmailService emailService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            var emails = List.of("wallanpereira09@gmail.com");
            emailService.sendEmail(message, emails);
            System.out.println("Executado envio de e-mail :D");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }

}
