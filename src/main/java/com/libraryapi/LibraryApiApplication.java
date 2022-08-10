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
    private String message; // Variavel para testar o envio de email com mensagem

    @Autowired
    private EmailService emailService;

    @Value("${spring.application.instance_id}")
    private String instanceRandom;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /*
     * Metodo para testar o envio de email
     * */
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            var emails = List.of("wallanpsantos.dev@yahoo.com");
            emailService.sendEmail("Empréstimo atrasado. Favor entrar em contato com a biblioteca para quitar seu empréstimo!", emails);
            System.out.println("Executado envio de e-mail :D");
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }

}
