package com.iogurtes;

import com.iogurtes.model.User;
import com.iogurtes.repository.UserRepository;
import com.iogurtes.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // Cria contexto Spring
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserRepository userRepository = context.getBean(UserRepository.class);
        UserService userService = new UserService(userRepository);

        // criar user
        User user = new User();
        user.setNome("João dos Arcos");
        user.setEmail("joao@email.com");
        user.setPasswordHash("123456");
        user.setDataAdmissao(LocalDate.now());

        userService.createUser(user);

        // listar users
        userService.getAllUsers().forEach(System.out::println);

        context.close();
    }
}