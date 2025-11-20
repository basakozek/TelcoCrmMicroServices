package com.etiya.authservice.service.rules;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthBusinessRules {

    public void checkIfAuthenticated(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("E posta veya şifre hatalı");
        }
    }
}
