package com.empresa.iogurtes.gestaoiogurtes.core.security.user;


import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador não encontrado"));

        if (user.getRole() == null || user.getRole().getRole() == null) {
            throw new UsernameNotFoundException("Utilizador sem role associada");
        }

        if (user.getPasswordHash() == null || user.getPasswordHash().isBlank()) {
            throw new UsernameNotFoundException("Utilizador sem password definida");
        }

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password("{bcrypt}" + user.getPasswordHash())
            .authorities("ROLE_" + user.getRole().getRole().name())
            .build();
    }
}