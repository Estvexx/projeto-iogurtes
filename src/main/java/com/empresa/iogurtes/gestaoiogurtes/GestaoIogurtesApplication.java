package com.empresa.iogurtes.gestaoiogurtes;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.service.UserService;
import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;



@SpringBootApplication
public class GestaoIogurtesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoIogurtesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService) {
		return args -> {

			// João Silva – password fraca (inválida) para teste
			try {
				User u1 = userService.createUser(
						"João Silva",
						"joao.silva@empresa.com",
						"joao123", // < 8 caracteres + sem maiúscula/símbolo
						TurnoTipo.MANHA,
						LocalDate.of(2024, 1, 15),
						List.of(new UserRole(UserRoleType.FUNCIONARIO))
				);
				System.out.println("✅ Criado: " + u1);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou João Silva (password inválida): " + e.getMessage());
			}

			// Maria Costa – password válida
			try {
				User u2 = userService.createUser(
						"Maria Costa",
						"maria.costa@empresa.com",
						"MARIACOSTA_!2",
						null,
						LocalDate.of(2023, 5, 10),
						List.of(new UserRole(UserRoleType.ADMIN))
				);
				System.out.println("✅ Criado: " + u2);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Maria Costa: " + e.getMessage());
			}

			// Pedro Santos – password válida
			try {
				User u3 = userService.createUser(
						"Pedro Santos",
						"pedro.santos@empresa.com",
						"PedroSantos12!",
						null,
						LocalDate.of(2022, 8, 20),
						List.of(new UserRole(UserRoleType.EMPRESA))
				);
				System.out.println("✅ Criado: " + u3);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Pedro Santos: " + e.getMessage());
			}

			// Ana Ferreira – password válida
			try {
				User u4 = userService.createUser(
						"Ana Ferreira",
						"ana.ferreira@empresa.com",
						"AnaFerreca12#",
						TurnoTipo.TARDE,
						LocalDate.of(2024, 3, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO))
				);
				System.out.println("✅ Criado: " + u4);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Ana Ferreira: " + e.getMessage());
			}

			// Teste de user com password inválida
			try {
				User u5 = userService.createUser(
						"Teste Erro",
						"erro@empresa.com",
						"abcd", // password inválida
						null,
						LocalDate.of(2024, 1, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO))
				);
				System.out.println("✅ Criado: " + u5);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Teste Erro (password inválida): " + e.getMessage());
			}

			// Camila com 2 roles – password válida
			try {
				List<UserRole> camilaRoles = new ArrayList<>();
				camilaRoles.add(new UserRole(UserRoleType.FUNCIONARIO));
				camilaRoles.add(new UserRole(UserRoleType.ADMIN));

				User u6 = userService.createUser(
						"Camila Amorim",
						"camila.amorim@empresa.com",
						"CamilaAmorim2030_!",
						TurnoTipo.TARDE,
						LocalDate.of(2024, 3, 1),
						camilaRoles
				);
				System.out.println("✅ Criado: " + u6);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Camila Amorim: " + e.getMessage());
			}
		};
	}
}
