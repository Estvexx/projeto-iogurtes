package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.auth.LoginRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.auth.LoginResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.auth.AuthErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.auth.AuthException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.security.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (BadCredentialsException exception) {
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        User user = userRepository.findByEmailAndIsActiveTrue(request.email())
                .orElseThrow(() -> new AuthException(AuthErrorCode.INVALID_CREDENTIALS));

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole().getRole().name(),
                token
        );
    }
}