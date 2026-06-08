package com.empresa.iogurtes.gestaoiogurtes.core.controller;


import com.empresa.iogurtes.gestaoiogurtes.core.dto.auth.LoginRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.auth.LoginResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}