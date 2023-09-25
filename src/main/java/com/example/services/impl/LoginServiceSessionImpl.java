package com.example.services.impl;

import com.example.services.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class LoginServiceSessionImpl implements LoginService {
    @Override
    public Optional<String> getUsername(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            return Optional.of(username);
        }
        return Optional.empty();
    }
}
