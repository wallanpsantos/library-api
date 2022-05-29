package com.libraryapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class LibraryApiApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Scheduled(cron = "0/20 * * 1/1 * ?")
    public void startRoutineTest() {
        System.out.println("Hi, i'am routine to test :D");
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApiApplication.class, args);
    }

}
