package com.empresa.iogurtes.gestaoiogurtes;

import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.service.UserService;
import org.springframework.boot.CommandLineRunner;

import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.service.EmpresaService;

import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
public class GestaoIogurtesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoIogurtesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService, EmpresaService empresaService) {
		return args -> {

			// ============================================
			// SECÇAO EMPRESAS (criar primeiro para ter IDs)
			// ============================================
			Empresa e1 = null, e2 = null, e3 = null, e4 = null, e5 = null;

			try {
				e1 = empresaService.createEmpresa(
						"Lacticínios Porto S.A.",
						"123456789",
						"+351912345678",
						"Rua da Industria, 45",
						"4000-123",
						"Porto"
				);
				System.out.println("✅ Empresa criada: " + e1.getNomeEmpresa() + " | ID: " + e1.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro ao criar empresa 1: " + e.getMessage());
			}

			try {
				e2 = empresaService.createEmpresa(
						"Frutas e Laticínios Norte Lda.",
						"987654321",
						"+351913456789",
						"Avenida das Flores, 12",
						"4050-210",
						"Porto"
				);
				System.out.println("✅ Empresa criada: " + e2.getNomeEmpresa() + " | ID: " + e2.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro ao criar empresa 2: " + e.getMessage());
			}

			try {
				e3 = empresaService.createEmpresa(
						"Iogurtes e Compotas Algarve S.A.",
						"112233445",
						"+351914567890",
						"Estrada Nacional 125, 200",
						"8000-456",
						"Faro"
				);
				System.out.println("✅ Empresa criada: " + e3.getNomeEmpresa() + " | ID: " + e3.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro ao criar empresa 3: " + e.getMessage());
			}

			try {
				e4 = empresaService.createEmpresa(
						"Laticínios do Minho Lda.",
						"556677889",
						"+351915678901",
						"Rua do Comércio, 33",
						"4700-320",
						"Braga"
				);
				System.out.println("✅ Empresa criada: " + e4.getNomeEmpresa() + " | ID: " + e4.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro ao criar empresa 4: " + e.getMessage());
			}

			try {
				e5 = empresaService.createEmpresa(
						"Laticínios Serra da Estrela",
						"998877665",
						"",
						"Avenida das Montanhas, 5",
						"6200-150",
						"Covilhã"
				);
				System.out.println("✅ Empresa criada: " + e5.getNomeEmpresa() + " | ID: " + e5.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro ao criar empresa 5: " + e.getMessage());
			}

			// ============================================
			// SECÇAO USERS (com empresaId)
			// ============================================

			UUID empresaId1 = e1 != null ? e1.getId() : null;
			UUID empresaId2 = e2 != null ? e2.getId() : null;
			UUID empresaId3 = e3 != null ? e3.getId() : null;
			UUID empresaId4 = null;

			// 1. João Silva – password fraca (inválida) para teste
			try {
				User u1 = userService.createUser(
						"João Silva",
						"joao.silva@empresa.com",
						"123", // password fraca - deve falhar
						TurnoTipo.MANHA,
						LocalDate.of(2024, 1, 15),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId1
				);
				System.out.println("✅ User criado: " + u1.getNome());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou João Silva (password inválida): " + e.getMessage());
			}

			// 2. Maria Costa – ADMIN sem empresa
			try {
				User u2 = userService.createUser(
						"Maria Costa",
						"maria.costa@empresa.com",
						"MARIACOSTaA_!2",
						null,
						LocalDate.of(2023, 5, 10),
						List.of(new UserRole(UserRoleType.ADMIN)),
						null
				);
				System.out.println("✅ User criado: " + u2.getNome() + " | Role: ADMIN");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Maria Costa: " + e.getMessage());
			}

			// 3. Pedro Santos – EMPRESA (representante de empresa)
			try {
				User u3 = userService.createUser(
						"Pedro Santos",
						"pedro.santos@empresa.com",
						"PedroSantos12!",
						null,
						LocalDate.of(2022, 8, 20),
						List.of(new UserRole(UserRoleType.EMPRESA)),
						empresaId2
				);
				System.out.println("✅ User criado: " + u3.getNome() + " | Role: EMPRESA");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Pedro Santos: " + e.getMessage());
			}

			// 4. Ana Ferreira – FUNCIONARIO com turno TARDE
			try {
				User u4 = userService.createUser(
						"Ana Ferreira",
						"ana.ferreira@empresa.com",
						"AnaFerreca12#",
						TurnoTipo.TARDE,
						LocalDate.of(2024, 3, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId1
				);
				System.out.println("✅ User criado: " + u4.getNome() + " | Turno: TARDE");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Ana Ferreira: " + e.getMessage());
			}

			// 5. Teste de user com password inválida
			try {
				User u5 = userService.createUser(
						"Teste Erro",
						"erro@empresa.com",
						"abcd",
						null,
						LocalDate.of(2024, 1, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId3
				);
				System.out.println("✅ User criado: " + u5.getNome());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Teste Erro (password inválida): " + e.getMessage());
			}

			// 6. Camila Amorim – multiplas roles (FUNCIONARIO + ADMIN)
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
						camilaRoles,
						empresaId2
				);
				System.out.println("✅ User criado: " + u6.getNome() + " | Roles: FUNCIONARIO + ADMIN");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Camila Amorim: " + e.getMessage());
			}

			// 7. Bruno Lima – FUNCIONARIO turno NOITE
			try {
				User u7 = userService.createUser(
						"Bruno Lima",
						"bruno.lima@empresa.com",
						"BrunoLima2024@",
						TurnoTipo.NOITE,
						LocalDate.of(2023, 11, 15),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId3
				);
				System.out.println("✅ User criado: " + u7.getNome() + " | Turno: NOITE");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Bruno Lima: " + e.getMessage());
			}

			// 8. Carla Mendes – FUNCIONARIO sem turno definido
			try {
				User u8 = userService.createUser(
						"Carla Mendes",
						"carla.mendes@empresa.com",
						"CarlaMendes#99",
						null,
						LocalDate.of(2024, 6, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId4
				);
				System.out.println("✅ User criado: " + u8.getNome() + " | Sem turno definido");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Carla Mendes: " + e.getMessage());
			}

			// 9. Teste de email duplicado (se correres 2x, deve falhar na 2ª)
			try {
				User u9 = userService.createUser(
						"Duplicado Teste",
						"joao.silva@empresa.com", // mesmo email do u1
						"Duplicado123!",
						TurnoTipo.MANHA,
						LocalDate.of(2024, 1, 1),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						empresaId1
				);
				System.out.println("✅ User criado: " + u9.getNome());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Não criou Duplicado Teste (email duplicado?): " + e.getMessage());
			}

			// ============================================
			// TESTES DE LEITURA (GET ALL)
			// ============================================
			System.out.println("\n========== LISTAGEM DE EMPRESAS ==========");
			List<Empresa> empresas = empresaService.getAll();
			empresas.forEach(e -> System.out.println("📌 " + e.getNomeEmpresa() + " | " + e.getCidade()));

			System.out.println("\n========== LISTAGEM DE USERS ==========");
			List<User> users = userService.getAll();
			users.forEach(u -> System.out.println("👤 " + u.getNome() + " | " + u.getEmail() + " | Empresa: " +
					(u.getEmpresa() != null ? u.getEmpresa().getNomeEmpresa() : "N/A")));

		};
	}
}