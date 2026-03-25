package com.empresa.iogurtes.gestaoiogurtes.core.config;

import com.empresa.iogurtes.gestaoiogurtes.core.ports.PasswordHasher;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.service.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordHasher passwordHasher(BCryptPasswordEncoder encoder) {
        return new BCryptPasswordHasher(encoder);
    }

    @Bean
    public LoginService loginService(UserRepository userRepository,
                                     PasswordHasher passwordHasher) {
        return new LoginService(userRepository, passwordHasher);
    }
}