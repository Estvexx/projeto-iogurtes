package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String nome, String email, String passwordHash,
                           String cargo, TurnoTipo turno, LocalDate dataAdmissao,
                           List<UserRole> roles) {

        User user = new User(
                null,
                nome,
                email,
                passwordHash,
                cargo,
                turno,
                dataAdmissao
        );

        user.setRoles(roles);

        return userRepository.save(user);
    }
}