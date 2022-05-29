package com.libraryapi.service;

import java.util.List;

public interface EmailService {
    void sendEmail(List<String> emails);
}
