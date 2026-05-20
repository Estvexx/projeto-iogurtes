package com.empresa.iogurtes.gestaoiogurtes;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CertificacaoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.certificacao.CreateCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.EmpresaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.empresa.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_certificacao.AddCertificacaoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.CreateFornecedorTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedor_tipos.FornecedorTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.CreateFornecedorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.fornecedores.FornecedorResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.CreateMateriaPrimaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materiaprima.MateriaPrimaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.CreateTipoMateriaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.materias_tipo.MateriaTipoResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.moeda.CreateMoedaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.pallet_tipo.CreatePalletTipoRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final.CreateProdutoFinalRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final.CreateProdutoMateriaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.produto_final.ProdutoFinalResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.CreateAdminRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.CreateClienteRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.CreateFuncionarioRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.CreateGestorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.EstadoFisico;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRoleRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class GestaoIogurtesApplication {

	public static void main(String[] args) {

		SpringApplication.run(GestaoIogurtesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService,
								 EmpresaService empresaService,
								 CertificacaoService certificacaoService,
								 FornecedorTipoService fornecedorTipoService,
								 FornecedorService fornecedorService,
								 MateriaPrimaService materiaPrimaService,
								 TipoMateriaService tipoMateriaService,
								 ProdutoFinalService produtoFinalService,
								 OrdemProducaoService ordemProducaoService,
								 PalletTipoService palletTipoService,
								 EncomendaService encomendaService,
								 LoginService loginService,
								 UserRoleRepository userRoleRepository, MoedaService moedaService) {
		return args -> {

			for (UserRoleType roleType : UserRoleType.values()) {
				if (userRoleRepository.findByRole(roleType).isEmpty()) {
					userRoleRepository.save(new UserRole(roleType));
				}
			}

			userService.createAdmin(new CreateAdminRequest("Maria Costa", "maria.costa@empresa.com", "MariaCosta@123"));
			userService.createAdmin(new CreateAdminRequest("António Silva", "antonio.silva@empresa.com", "AntonioSilva@456"));
			userService.createAdmin(new CreateAdminRequest("Francisco Esteves", "francisco.esteves@empresa.com", "FranciscoEsteves@789"));
			userService.createGestor(new CreateGestorRequest("Carla Mendes", "carla.mendes@empresa.com", "CarlaMendes@123", LocalDate.of(2022, 3, 15)));
			userService.createGestor(new CreateGestorRequest("Rui Oliveira", "rui.oliveira@empresa.com", "RuiOliveira@456", LocalDate.of(2021, 7, 20)));
			userService.createGestor(new CreateGestorRequest("Inês Sousa", "ines.sousa@empresa.com", "InesSousa@789", LocalDate.of(2023, 1, 10)));
			userService.createFuncionarioMP(new CreateFuncionarioRequest("Ana Ferreira", "ana.ferreira@empresa.com", "AnaFerreira@123", "MANHA", LocalDate.of(2024, 3, 1)));
			userService.createFuncionarioOP(new CreateFuncionarioRequest("Bruno Lima", "bruno.lima@empresa.com", "BrunoLima@2024",  "NOITE", LocalDate.of(2023, 11, 15)));
			userService.createFuncionarioMP(new CreateFuncionarioRequest("Joana Pinto", "joana.pinto@empresa.com", "JoanaPinto@321",  "TARDE", LocalDate.of(2022, 6, 5)));

			// ========== CRIAR EMPRESAS ==========
			EmpresaResponse e1 = empresaService.createEmpresa(new CreateEmpresaRequest("LactoNorte - Cooperativa de Laticínios", "501234567", "+351252345678", "Rua dos Laticínios, 150", "4760-012", "Vila Nova de Famalicão"));
			EmpresaResponse e2 = empresaService.createEmpresa(new CreateEmpresaRequest("Frutas do Vale Lda", "509876543", "+351275123456", "Quinta da Fruta Fresca, Lote 12", "6230-456", "Fundão"));
			EmpresaResponse e3 = empresaService.createEmpresa(new CreateEmpresaRequest("Embalagens Alimentar S.A.", "508765432", "+351234567890", "Zona Industrial de Aveiro, Lote 8", "3800-123", "Aveiro"));
			EmpresaResponse e4 = empresaService.createEmpresa(new CreateEmpresaRequest("Açúcares & Mel Portugal", "507654321", "+351243987654", "Estrada Nacional 3, Km 145", "2000-123", "Santarém"));
			EmpresaResponse e5 = empresaService.createEmpresa(new CreateEmpresaRequest("Cacau & Especiarias Gourmet", "506543210", "+351213456789", "Avenida da República, 88, Piso 3", "1050-012", "Lisboa"));

			userService.createCliente(new CreateClienteRequest("Pedro Santos", "pedro.santos@empresa.com", "PedroSantos@12", e1.id()));

			// Empresa para softdelete (sem clientes associados)
			EmpresaResponse eApagar = empresaService.createEmpresa(new CreateEmpresaRequest("Empresa Para Apagar Lda", "500000001", "+351210000001", "Rua Temporária, 1", "1000-001", "Lisboa"));

			// ========== UPDATES ==========
			System.out.println("\n========== UPDATES EMPRESAS ==========");
			EmpresaResponse e1Atualizada = empresaService.updateEmpresa(e1.id(), new UpdateEmpresaRequest("LactoNorte UPDATED", "501234567", "+351252999999", "Rua dos Laticínios, 200", "4760-012", "Vila Nova de Famalicão"));
			System.out.println("Empresa atualizada: " + e1Atualizada);

			EmpresaResponse e2Atualizada = empresaService.updateEmpresa(e2.id(), new UpdateEmpresaRequest("Frutas do Vale UPDATED", "509876543", "+351275999999", "Quinta Nova, Lote 99", "6230-456", "Fundão"));
			System.out.println("Empresa atualizada: " + e2Atualizada);

			// ========== SOFT DELETE ==========
			System.out.println("\n========== SOFT DELETE EMPRESA ==========");
			empresaService.softDelete(eApagar.id());
			System.out.println("Empresa apagada: " + eApagar.id());


			System.out.println("\n========== FIND BY ID ==========");
			System.out.println(empresaService.findById(e1.id()));

			// ========== CRIAR CERTIFICAÇÕES ==========
			// Certificações obrigatórias para a indústria de lacticínios e alimentar
			CertificacaoResponse c1 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("ISO 22000", "Sistema de Gestão de Segurança Alimentar - norma internacional para a segurança dos alimentos em toda a cadeia de produção")
			);

			CertificacaoResponse c2 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("FSSC 22000", "Food Safety System Certification - certificação de segurança alimentar para fabricantes de embalagens e produtos alimentares")
			);

			CertificacaoResponse c3 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("IFS Food", "International Featured Standards - norma para auditoria de qualidade e segurança de produtos alimentares de marca própria")
			);

			CertificacaoResponse c4 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("BRCGS Food", "Brand Reputation Compliance Global Standard - norma global para segurança alimentar, reconhecida pela GFSI")
			);

			CertificacaoResponse c5 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("HACCP", "Hazard Analysis and Critical Control Points - sistema preventivo de controlo de perigos na produção alimentar")
			);

			CertificacaoResponse c6 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("Certificação Kosher", "Certificação que atesta que o produto está em conformidade com as leis dietéticas judaicas (Kashrut)")
			);

			CertificacaoResponse c7 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("Certificação Halal", "Certificação que garante que o produto está em conformidade com os requisitos dietéticos islâmicos")
			);

			CertificacaoResponse c8 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("ISO 14001", "Sistema de Gestão Ambiental - norma internacional para gestão do impacto ambiental das operações")
			);

			CertificacaoResponse c9 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("Biológico PT-BIO-03", "Certificação de Agricultura Biológica - norma portuguesa para produtos biológicos, reconhecida pela UE")
			);

			CertificacaoResponse c10 = certificacaoService.createCertificacao(
					new CreateCertificacaoRequest("Fair Trade", "Certificação de Comércio Justo - garante práticas comerciais justas para produtores de países em desenvolvimento, como o cacau e o açúcar")
			);

			FornecedorTipoResponse ft1 = fornecedorTipoService.createFornecedorTipo(
					new CreateFornecedorTipoRequest("Lacticínios", "Fornecedores de leite, natas, manteiga e outros derivados lácteos para produção de iogurtes.")
			);

			FornecedorTipoResponse ft2 = fornecedorTipoService.createFornecedorTipo(
					new CreateFornecedorTipoRequest("Frutas", "Fornecedores de frutas frescas, polpas e concentrados para iogurtes de sabores.")
			);

			FornecedorTipoResponse ft3 = fornecedorTipoService.createFornecedorTipo(
					new CreateFornecedorTipoRequest("Embalagens", "Fornecedores de copos, tampas, rótulos e materiais de embalagem alimentar.")
			);

			FornecedorTipoResponse ft4 = fornecedorTipoService.createFornecedorTipo(
					new CreateFornecedorTipoRequest("Açúcares e Adoçantes", "Fornecedores de açúcar, mel, stevia e outros adoçantes naturais ou artificiais.")
			);

			FornecedorTipoResponse ft5 = fornecedorTipoService.createFornecedorTipo(
					new CreateFornecedorTipoRequest("Cacau e Especiarias", "Fornecedores de cacau, baunilha, canela e outros aromatizantes para iogurtes gourmet.")
			);

			FornecedorResponse f1 = fornecedorService.createFornecedor(
					new CreateFornecedorRequest(
							"LactoNorte - Cooperativa de Laticínios",
							"501234567",
							"geral@lactonorte.pt",
							"+351252345678",
							"Rua dos Laticínios, 150",
							"Vila Nova de Famalicão",
							ft1.id(),
							List.of(
									new AddCertificacaoRequest(c1.id(), LocalDate.of(2024, 1, 1), LocalDate.of(2026, 12, 31)),
									new AddCertificacaoRequest(c2.id(), LocalDate.of(2024, 3, 15), LocalDate.of(2027, 3, 14)),
									new AddCertificacaoRequest(c5.id(), LocalDate.of(2023, 6, 1), LocalDate.of(2026, 5, 31))
							)
					)
			);

			FornecedorResponse f2 = fornecedorService.createFornecedor(
					new CreateFornecedorRequest(
							"Frutas do Vale Lda",
							"509876543",
							"info@frutasdovale.pt",
							"+351275123456",
							"Quinta da Fruta Fresca, Lote 12",
							"Fundão",
							ft2.id(),
							List.of(
									new AddCertificacaoRequest(c3.id(), LocalDate.of(2024, 2, 1), LocalDate.of(2027, 1, 31)),
									new AddCertificacaoRequest(c5.id(), LocalDate.of(2023, 9, 1), LocalDate.of(2026, 8, 31)),
									new AddCertificacaoRequest(c9.id(), LocalDate.of(2024, 4, 1), LocalDate.of(2025, 3, 31))
							)
					)
			);

			moedaService.createMoeda(new CreateMoedaRequest("EUR", "Euro", "€", BigDecimal.ONE));
			moedaService.createMoeda(new CreateMoedaRequest("USD", "Dólar Americano", "$", new BigDecimal("0.92")));
			moedaService.createMoeda(new CreateMoedaRequest("GBP", "Libra Esterlina", "£", new BigDecimal("1.17")));
			moedaService.createMoeda(new CreateMoedaRequest("BRL", "Real Brasileiro", "R$", new BigDecimal("0.16")));
			moedaService.createMoeda(new CreateMoedaRequest("CHF", "Franco Suíço", "CHF", new BigDecimal("1.05")));

			// ─── Tipos de Matéria-Prima ─────────────────────────────────────────

			MateriaTipoResponse tipoBases = tipoMateriaService.create(
					new CreateTipoMateriaRequest("Bases", "Leites e bases lácteas", new BigDecimal("6.00")));
			MateriaTipoResponse tipoAdocantes = tipoMateriaService.create(
					new CreateTipoMateriaRequest("Adoçantes", "Açúcares e adoçantes", new BigDecimal("23.00")));
			MateriaTipoResponse tipoSabor = tipoMateriaService.create(
					new CreateTipoMateriaRequest("Sabor", "Frutas, polpas e aromas", new BigDecimal("23.00")));
			MateriaTipoResponse tipoOutro = tipoMateriaService.create(
					new CreateTipoMateriaRequest("Outro", "Embalagens e outros materiais", new BigDecimal("23.00")));

			// ─── Matérias-Primas ────────────────────────────────────────────────

			MateriaPrimaResponse mpLeite = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Leite de Vaca", "L",
							new BigDecimal("100.000"), tipoBases.id()));
			MateriaPrimaResponse mpLeitePo = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Leite em Pó Inteira", "kg",
							new BigDecimal("100.000"), tipoBases.id()));
			MateriaPrimaResponse mpFermento = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Fermento Lácteo", "kg",
							new BigDecimal("100.000"), tipoBases.id()));
			MateriaPrimaResponse mpAcucar = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Açúcar Branco", "kg",
							new BigDecimal("100.000"), tipoAdocantes.id()));
			MateriaPrimaResponse mpMorango = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Polpa de Morango", "kg",
							new BigDecimal("100.000"), tipoSabor.id()));
			MateriaPrimaResponse mpPessego = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Polpa de Pêssego", "kg",
							new BigDecimal("100.000"), tipoSabor.id()));
			MateriaPrimaResponse mpFramboesa = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Polpa de Framboesa", "kg",
							new BigDecimal("100.000"), tipoSabor.id()));
			MateriaPrimaResponse mpBaunilha = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Extrato de Baunilha", "kg",
							new BigDecimal("100.000"), tipoSabor.id()));
			MateriaPrimaResponse mpCacau = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Cacau em Pó", "kg",
							new BigDecimal("100.000"), tipoSabor.id()));
			MateriaPrimaResponse mpEmbalagem = materiaPrimaService.createMateriaPrima(
					new CreateMateriaPrimaRequest("Embalagem Iogurte 125g", "un",
							new BigDecimal("10000.000"), tipoOutro.id()));

			// ─── Produtos Finais ────────────────────────────────────────────────

			ProdutoFinalResponse pNatural = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte Natural", null, "NAT", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.35"), new BigDecimal("2.80"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.1000")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0030")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			ProdutoFinalResponse pMorango = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte de Morango", null, "MOR", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.42"), new BigDecimal("3.36"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.0900")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0300")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpMorango.id(), new BigDecimal("0.0150")),
									new CreateProdutoMateriaRequest(mpAcucar.id(), new BigDecimal("0.0080")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			ProdutoFinalResponse pPessego = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte de Pêssego", null, "PES", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.42"), new BigDecimal("3.36"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.0900")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0300")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpPessego.id(), new BigDecimal("0.0150")),
									new CreateProdutoMateriaRequest(mpAcucar.id(), new BigDecimal("0.0080")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			ProdutoFinalResponse pFramboesa = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte de Framboesa", null, "FRA", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.48"), new BigDecimal("3.84"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.0900")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0300")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpFramboesa.id(), new BigDecimal("0.0150")),
									new CreateProdutoMateriaRequest(mpAcucar.id(), new BigDecimal("0.0080")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			ProdutoFinalResponse pBaunilha = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte de Baunilha", null, "BAU", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.45"), new BigDecimal("3.60"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.1000")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0300")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpBaunilha.id(), new BigDecimal("0.0010")),
									new CreateProdutoMateriaRequest(mpAcucar.id(), new BigDecimal("0.0080")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			ProdutoFinalResponse pChocolate = produtoFinalService.createProdutoFinal(
					new CreateProdutoFinalRequest("Iogurte de Chocolate", null, "CHO", EstadoFisico.SOLIDO,
							21, new BigDecimal("0.45"), new BigDecimal("3.60"),
							new BigDecimal("6.00"), true, 0,
							List.of(
									new CreateProdutoMateriaRequest(mpLeite.id(), new BigDecimal("0.0950")),
									new CreateProdutoMateriaRequest(mpLeitePo.id(), new BigDecimal("0.0300")),
									new CreateProdutoMateriaRequest(mpFermento.id(), new BigDecimal("0.0008")),
									new CreateProdutoMateriaRequest(mpCacau.id(), new BigDecimal("0.0080")),
									new CreateProdutoMateriaRequest(mpAcucar.id(), new BigDecimal("0.0100")),
									new CreateProdutoMateriaRequest(mpEmbalagem.id(), new BigDecimal("1"))
							)));

			List<CreatePalletTipoRequest> pallets = List.of(
					new CreatePalletTipoRequest("Pallet Standard", new BigDecimal("500.000")),
					new CreatePalletTipoRequest("Meia Pallet", new BigDecimal("250.000")),
					new CreatePalletTipoRequest("Pallet Pequeno", new BigDecimal("125.000"))
			);

			for (CreatePalletTipoRequest pallet : pallets) {
				try {
					palletTipoService.create(pallet);
					System.out.println("✅ Pallet inserido: " + pallet.nome());
				} catch (Exception e) {
					System.out.println("⚠️ Pallet já existe: " + pallet.nome());
				}
			}

			/*
			try {
				loginService.execute("bruno.lima@empresa.com", "BrunoLima@2024");
				System.out.println("Login de user inativo passou (nao esperado)");
			} catch (IllegalArgumentException ex) {
				System.out.println("Login de user inativo bloqueado: " + ex.getMessage());
			}

			userService.changePassword(userId1, "MariaNova@123!");

			try {
				loginService.execute("maria.costa@empresa.com", "MariaNova@123!");;
				System.out.println("Login de maria com nova passe com sucesso");
			} catch (IllegalArgumentException ex) {
				System.out.println("Login de user inativo bloqueado: " + ex.getMessage());
			}

			// ============================================
			// LISTAGENS FINAIS
			// ============================================
			System.out.println("\n========== RESUMO ATIVOS / TOTAL ==========");
			System.out.println("Empresas: " + empresaService.getAll().size() + " / " + empresaService.getAllIncludingInactive().size());
			System.out.println("Users: " + userService.getAll().size() + " / " + userService.getAllIncludingInactive().size());
			System.out.println("Fornecedores: " + fornecedorService.getAll().size() + " / " + fornecedorService.getAllIncludingInactive().size());
			System.out.println("Matérias-primas: " + materiaPrimaService.getAll().size() + " / " + materiaPrimaService.getAllIncludingInactive().size());
			System.out.println("Movimentos MP: " + movimentoStockMPService.getAll().size() + " / " + movimentoStockMPService.getAllIncludingInactive().size());
			System.out.println("Produtos finais: " + produtoFinalService.getAll().size() + " / " + produtoFinalService.getAllIncludingInactive().size());
			System.out.println("Ordens produção: " + ordemProducaoService.getAll().size() + " / " + ordemProducaoService.getAllIncludingInactive().size());
			System.out.println("Pallet tipos: " + palletTipoService.getAll().size() + " / " + palletTipoService.getAllIncludingInactive().size());
			System.out.println("Encomendas: " + encomendaService.getAll().size() + " / " + encomendaService.getAllIncludingInactive().size());

			System.out.println("\n========== LISTAGEM DE EMPRESAS ==========");
			empresaService.getAll().forEach(System.out::println);



			System.out.println("\n========== LISTAGEM DE FORNECEDORES ==========");
			fornecedorService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE MATÉRIAS PRIMAS ==========");
			materiaPrimaService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE MOVIMENTOS ==========");
			movimentoStockMPService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE PRODUTOS FINAIS ==========");
			produtoFinalService.getAll().forEach(System.out::println);
*/
			//produtoFinalService.getMateriasByProdutoId(ProdutoFinalId1)
			//		.forEach(System.out::println);
		};
	}
}