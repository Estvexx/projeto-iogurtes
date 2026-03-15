package com.empresa.iogurtes.gestaoiogurtes;

import com.empresa.iogurtes.gestaoiogurtes.core.model.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.*;
import com.empresa.iogurtes.gestaoiogurtes.core.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class GestaoIogurtesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoIogurtesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService,
								 EmpresaService empresaService,
								 FornecedorService fornecedorService,
								 MateriaPrimaService materiaPrimaService) {
		return args -> {

			// ============================================
			// SECÇÃO EMPRESAS
			// ============================================
			System.out.println("\n========== A CRIAR EMPRESAS ==========");

			Empresa e1 = null, e2 = null, e3 = null;

			try {
				e1 = empresaService.createEmpresa(
						"Lacticínios Porto S.A.", "123456789",
						"+351912345678", "Rua da Industria, 45", "4000-123", "Porto"
				);
				System.out.println("✅ Empresa criada: " + e1.getNomeEmpresa() + " | ID: " + e1.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro empresa 1: " + e.getMessage());
			}

			try {
				e2 = empresaService.createEmpresa(
						"Frutas e Laticínios Norte Lda.", "987654321",
						"+351913456789", "Avenida das Flores, 12", "4050-210", "Porto"
				);
				System.out.println("✅ Empresa criada: " + e2.getNomeEmpresa() + " | ID: " + e2.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro empresa 2: " + e.getMessage());
			}

			try {
				e3 = empresaService.createEmpresa(
						"Iogurtes e Compotas Algarve S.A.", "112233445",
						"+351914567890", "Estrada Nacional 125, 200", "8000-456", "Faro"
				);
				System.out.println("✅ Empresa criada: " + e3.getNomeEmpresa() + " | ID: " + e3.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro empresa 3: " + e.getMessage());
			}

			// ============================================
			// SECÇÃO USERS
			// ============================================
			System.out.println("\n========== A CRIAR USERS ==========");

			UUID empresaId1 = e1 != null ? e1.getId() : null;
			UUID empresaId2 = e2 != null ? e2.getId() : null;
			UUID empresaId3 = e3 != null ? e3.getId() : null;

			// ADMIN sem empresa
			try {
				User admin = userService.createUser(
						"Maria Costa", "maria.costa@empresa.com", "MariaCosta@123",
						null, LocalDate.of(2023, 5, 10),
						List.of(new UserRole(UserRoleType.ADMIN)),
						null
				);
				System.out.println("✅ Admin criado: " + admin.getNome());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro admin: " + e.getMessage());
			}

			// FUNCIONARIO turno MANHA
			try {
				List<UserRole> roles = new ArrayList<>();
				roles.add(new UserRole(UserRoleType.FUNCIONARIO));

				User f1 = userService.createUser(
						"Ana Ferreira", "ana.ferreira@empresa.com", "AnaFerreira@123",
						TurnoTipo.MANHA, LocalDate.of(2024, 3, 1),
						roles, null
				);
				System.out.println("✅ Funcionario criado: " + f1.getNome() + " | Turno: MANHA");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro funcionario 1: " + e.getMessage());
			}

			// FUNCIONARIO turno NOITE
			try {
				User f2 = userService.createUser(
						"Bruno Lima", "bruno.lima@empresa.com", "BrunoLima@2024",
						TurnoTipo.NOITE, LocalDate.of(2023, 11, 15),
						List.of(new UserRole(UserRoleType.FUNCIONARIO)),
						null
				);
				System.out.println("✅ Funcionario criado: " + f2.getNome() + " | Turno: NOITE");
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro funcionario 2: " + e.getMessage());
			}

			// EMPRESA com empresa associada
			try {
				User ue1 = userService.createUser(
						"Pedro Santos", "pedro.santos@empresa.com", "PedroSantos@12",
						null, null,
						List.of(new UserRole(UserRoleType.EMPRESA)),
						empresaId1
				);
				System.out.println("✅ User empresa criado: " + ue1.getNome() + " | Empresa: " + e1.getNomeEmpresa());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro user empresa 1: " + e.getMessage());
			}

			try {
				User ue2 = userService.createUser(
						"Carla Mendes", "carla.mendes@empresa.com", "CarlaMendes@99",
						null, null,
						List.of(new UserRole(UserRoleType.EMPRESA)),
						empresaId2
				);
				System.out.println("✅ User empresa criado: " + ue2.getNome() + " | Empresa: " + e2.getNomeEmpresa());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro user empresa 2: " + e.getMessage());
			}

			// Teste password fraca (deve falhar)
			try {
				userService.createUser(
						"Teste Erro", "erro@empresa.com", "123",
						null, null,
						List.of(new UserRole(UserRoleType.ADMIN)),
						null
				);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Esperado - password fraca: " + e.getMessage());
			}

			// Teste EMPRESA sem empresaId (deve falhar)
			try {
				userService.createUser(
						"Teste Empresa Sem ID", "semid@empresa.com", "TesteSemId@1",
						null, null,
						List.of(new UserRole(UserRoleType.EMPRESA)),
						null
				);
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Esperado - empresa sem ID: " + e.getMessage());
			}

			// ============================================
			// SECÇÃO FORNECEDORES
			// ============================================
			System.out.println("\n========== A CRIAR FORNECEDORES ==========");

			Fornecedor forn1 = null, forn2 = null;

			try {
				forn1 = fornecedorService.createFornecedor(
						"Agrilac S.A.", "501234567",
						"agrilac@fornecedor.com", "+351910000001",
						"Rua dos Laticínios, 10",
						List.of(
								new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2026, 12, 31)),
								new FornecedorCertificacao(TipoCertificacao.HACCP, "HACCP Alimentar", LocalDate.of(2026, 6, 30))
						)
				);
				System.out.println("✅ Fornecedor criado: " + forn1.getNome() + " | ID: " + forn1.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro fornecedor 1: " + e.getMessage());
			}

			try {
				forn2 = fornecedorService.createFornecedor(
						"BioLeite Lda.", "509876543",
						"bioleite@fornecedor.com", "+351910000002",
						"Avenida do Campo, 55",
						List.of(
								new FornecedorCertificacao(TipoCertificacao.BIO, "Certificação Biológica", LocalDate.of(2026, 3, 15))
						)
				);
				System.out.println("✅ Fornecedor criado: " + forn2.getNome() + " | ID: " + forn2.getId());
			} catch (IllegalArgumentException e) {
				System.out.println("⚠️ Erro fornecedor 2: " + e.getMessage());
			}

			// ============================================
			// SECÇÃO MATERIAS PRIMAS
			// ============================================
            System.out.println("\n========== A CRIAR MATÉRIAS PRIMAS ==========");

            UUID fornId1 = forn1 != null ? forn1.getId() : null;
            UUID fornId2 = forn2 != null ? forn2.getId() : null;

            Object[][] materias = {
                    // BASES
                    {"Leite de Vaca",         "L",  TipoMateriaPrima.BASES,   "5000.000", "1000.000", "0.490",  fornId1},
                    {"Leite em Pó Inteira",   "kg", TipoMateriaPrima.BASES,    "200.000",  "50.000",   "3.200",  fornId1},
                    {"Fermento Lácteo",       "kg", TipoMateriaPrima.BASES,    "20.000",   "5.000",    "18.000", fornId1},
                    {"Embalagem Iogurte 125g","un", TipoMateriaPrima.OUTRO,     "50000.000","10000.000","0.045",  fornId1},
                    // ADOÇANTES
                    {"Açúcar Branco",         "kg", TipoMateriaPrima.ADOCANTES, "300.000",  "80.000",   "0.850",  fornId1},
                    {"Mel",                   "kg", TipoMateriaPrima.ADOCANTES,   "50.000",   "10.000",   "4.500",  fornId2},
                    // SABORES / FRUTAS
                    {"Polpa de Morango",      "kg", TipoMateriaPrima.SABOR,     "150.000",  "30.000",   "2.100",  fornId2},
                    {"Polpa de Pêssego",      "kg", TipoMateriaPrima.SABOR,     "150.000",  "30.000",   "1.900",  fornId2},
                    {"Polpa de Framboesa",    "kg", TipoMateriaPrima.SABOR,     "100.000",  "20.000",   "3.500",  fornId2},
                    {"Extrato de Baunilha",   "kg", TipoMateriaPrima.SABOR,"10.000", "3.000",    "22.000", fornId2},
                    {"Cacau em Pó",           "kg", TipoMateriaPrima.SABOR, "40.000",   "10.000",   "5.800",  fornId2},
            };

            for (Object[] m : materias) {
                try {
                    MateriaPrima mp = materiaPrimaService.createMateriaPrima(
                            (String) m[0],
                            (String) m[1],
                            (TipoMateriaPrima) m[2],
                            new BigDecimal((String) m[3]),
                            new BigDecimal((String) m[4]),
                            new BigDecimal((String) m[5]),
                            (UUID) m[6]
                    );
                    System.out.println("✅ Matéria prima criada: " + mp.getNome() + " | Stock: " + mp.getStockAtual());
                } catch (IllegalArgumentException e) {
                    System.out.println("⚠️ Erro matéria prima " + m[0] + ": " + e.getMessage());
                }
            }

			// ============================================
			// LISTAGENS FINAIS
			// ============================================
			System.out.println("\n========== LISTAGEM DE EMPRESAS ==========");
			empresaService.getAll().forEach(e ->
					System.out.println("📌 " + e.getNomeEmpresa() + " | " + e.getCidade()));

			System.out.println("\n========== LISTAGEM DE USERS ==========");
			userService.getAll().forEach(u ->
					System.out.println("👤 " + u.getNome() + " | " + u.getEmail() + " | Empresa: " +
							(u.getEmpresa() != null ? u.getEmpresa().getNomeEmpresa() : "N/A")));

			System.out.println("\n========== LISTAGEM DE FORNECEDORES ==========");
			fornecedorService.getAll().forEach(f ->
					System.out.println("🏭 " + f.getNome() + " | NIF: " + f.getNif()));

			System.out.println("\n========== LISTAGEM DE MATÉRIAS PRIMAS ==========");
			materiaPrimaService.getAll().forEach(mp ->
					System.out.println("🧴 " + mp.getNome() + " | Stock: " + mp.getStockAtual() + " " + mp.getUnidade()));
		};
	}
}