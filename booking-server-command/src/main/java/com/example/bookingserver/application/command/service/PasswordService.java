package com.example.bookingserver.application.command.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    String encode(String password);
}
