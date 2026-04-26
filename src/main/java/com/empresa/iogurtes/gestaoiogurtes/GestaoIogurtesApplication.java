package com.empresa.iogurtes.gestaoiogurtes;

import com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto.CreateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto.EmpresaResponse;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.empresa.dto.UpdateEmpresaRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.CreateAdminRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.CreateClienteRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.CreateFuncionarioRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.domain.users.dto.CreateGestorRequest;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRoleRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@EnableScheduling
@SpringBootApplication
public class GestaoIogurtesApplication {

	public static void main(String[] args) {

		SpringApplication.run(GestaoIogurtesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService,
								 EmpresaService empresaService,
								 FornecedorService fornecedorService,
								 MateriaPrimaService materiaPrimaService,
								 MovimentoStockMPService movimentoStockMPService,
								 ProdutoFinalService produtoFinalService,
								 OrdemProducaoService ordemProducaoService,
								 PalletTipoService palletTipoService,
								 EncomendaService encomendaService,
								 LoginService loginService,
								 UserRoleRepository userRoleRepository) {
		return args -> {

			for (UserRoleType roleType : UserRoleType.values()) {
				if (userRoleRepository.findByRole(roleType).isEmpty()) {
					userRoleRepository.save(new UserRole(roleType));
				}
			}

			userService.createAdmin(new CreateAdminRequest("Maria Costa", "maria.costa@empresa.com", "MariaCosta@123", "ADMIN"));
			userService.createAdmin(new CreateAdminRequest("António Silva", "antonio.silva@empresa.com", "AntonioSilva@456", "ADMIN"));
			userService.createAdmin(new CreateAdminRequest("Francisco Esteves", "francisco.esteves@empresa.com", "FranciscoEsteves@789", "ADMIN"));
			userService.createGestor(new CreateGestorRequest("Carla Mendes", "carla.mendes@empresa.com", "CarlaMendes@123", "GESTOR", LocalDate.of(2022, 3, 15)));
			userService.createGestor(new CreateGestorRequest("Rui Oliveira", "rui.oliveira@empresa.com", "RuiOliveira@456", "GESTOR", LocalDate.of(2021, 7, 20)));
			userService.createGestor(new CreateGestorRequest("Inês Sousa", "ines.sousa@empresa.com", "InesSousa@789", "GESTOR", LocalDate.of(2023, 1, 10)));
			userService.createFuncionario(new CreateFuncionarioRequest("Ana Ferreira", "ana.ferreira@empresa.com", "AnaFerreira@123", "MANHA", "FUNCIONARIO_MP", LocalDate.of(2024, 3, 1)));
			userService.createFuncionario(new CreateFuncionarioRequest("Bruno Lima", "bruno.lima@empresa.com", "BrunoLima@2024", "NOITE", "FUNCIONARIO_OP", LocalDate.of(2023, 11, 15)));
			userService.createFuncionario(new CreateFuncionarioRequest("Joana Pinto", "joana.pinto@empresa.com", "JoanaPinto@321", "TARDE", "FUNCIONARIO_MP", LocalDate.of(2022, 6, 5)));

			System.out.println("\n========== LISTAGEM DE USERS ==========");
			userService.findAllActive().forEach(System.out::println);
			System.out.println("\n========== TODOS OS USERS ATIVOS ==========");
			userService.findAllActive().forEach(System.out::println);
			System.out.println("\n========== ADMINS ==========");
			userService.findAllAdmins().forEach(System.out::println);
			System.out.println("\n========== GESTORES ==========");
			userService.findAllGestores().forEach(System.out::println);
			System.out.println("\n========== FUNCIONARIOS (MP + OP) ==========");
			userService.findAllFuncionarios().forEach(System.out::println);
			System.out.println("\n========== FUNCIONARIOS MP ==========");
			userService.findAllFuncionarios_MP().forEach(System.out::println);
			System.out.println("\n========== FUNCIONARIOS OP ==========");
			userService.findAllFuncionarios_OP().forEach(System.out::println);
			System.out.println("\n========== CLIENTES ==========");
			userService.findAllClientes().forEach(System.out::println);
			System.out.println("\n========== USERS INATIVOS ==========");
			userService.findAllInactive().forEach(System.out::println);

			// ========== CRIAR EMPRESAS ==========
			EmpresaResponse e1 = empresaService.createEmpresa(new CreateEmpresaRequest("LactoNorte - Cooperativa de Laticínios", "501234567", "+351252345678", "Rua dos Laticínios, 150", "4760-012", "Vila Nova de Famalicão"));
			EmpresaResponse e2 = empresaService.createEmpresa(new CreateEmpresaRequest("Frutas do Vale Lda", "509876543", "+351275123456", "Quinta da Fruta Fresca, Lote 12", "6230-456", "Fundão"));
			EmpresaResponse e3 = empresaService.createEmpresa(new CreateEmpresaRequest("Embalagens Alimentar S.A.", "508765432", "+351234567890", "Zona Industrial de Aveiro, Lote 8", "3800-123", "Aveiro"));
			EmpresaResponse e4 = empresaService.createEmpresa(new CreateEmpresaRequest("Açúcares & Mel Portugal", "507654321", "+351243987654", "Estrada Nacional 3, Km 145", "2000-123", "Santarém"));
			EmpresaResponse e5 = empresaService.createEmpresa(new CreateEmpresaRequest("Cacau & Especiarias Gourmet", "506543210", "+351213456789", "Avenida da República, 88, Piso 3", "1050-012", "Lisboa"));

			userService.createCliente(new CreateClienteRequest("Pedro Santos", "pedro.santos@empresa.com", "PedroSantos@12", "CLIENTE", e1.id()));

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

			// ========== GETS ==========
			System.out.println("\n========== TODAS AS EMPRESAS ATIVAS ==========");
			empresaService.findAllActive().forEach(System.out::println);

			System.out.println("\n========== TODAS AS EMPRESAS INATIVAS ==========");
			empresaService.findAllInactive().forEach(System.out::println);

			System.out.println("\n========== TODAS AS EMPRESAS ==========");
			empresaService.findAll().forEach(System.out::println);

			System.out.println("\n========== FIND BY ID ==========");
			System.out.println(empresaService.findById(e1.id()));


			/*// Testes de diferentes formatos de telefone (devem ser normalizados e guardados)
			Empresa eTel1 = empresaService.createEmpresa("Empresa Telefone 1", "505111111", "912345678", "Rua A", "1000-001", "Lisboa");
			Empresa eTel2 = empresaService.createEmpresa("Empresa Telefone 2", "504222222", "+351 913 222 333", "Rua B", "1000-002", "Porto");
			Empresa eTel3 = empresaService.createEmpresa("Empresa Telefone 3", "503333333", "(351) 914444555", "Rua C", "1000-003", "Braga");
			System.out.println("Telefones normalizados (empresas): " + eTel1.getTelefone() + " | " + eTel2.getTelefone() + " | " + eTel3.getTelefone());

			// Testes de telefones estrangeiros
			Empresa eExt1 = empresaService.createEmpresa("Empresa Telefone Espanha", "501010101", "+34 612 345 678", "Calle A", "1000-005", "Madrid");
			Empresa eExt2 = empresaService.createEmpresa("Empresa Telefone França", "501010102", "+33 6 12 34 56 78", "Rue B", "1000-006", "Paris");
			Empresa eExt3 = empresaService.createEmpresa("Empresa Telefone EUA", "501010103", "+1 202 555 0147", "Street C", "1000-007", "Washington");
			System.out.println("Telefones estrangeiros normalizados: " + eExt1.getTelefone() + " | " + eExt2.getTelefone() + " | " + eExt3.getTelefone());

			try {
				empresaService.createEmpresa("Empresa Telefone Estrangeiro Inválido", "501010104", "+99 123", "Rua E", "1000-008", "Roma");
				System.out.println("Telefone estrangeiro inválido passou (nao esperado)");
			} catch (IllegalArgumentException ex) {
				System.out.println("Telefone estrangeiro inválido bloqueado com sucesso: " + ex.getMessage());
			}

			try {
				empresaService.createEmpresa("Empresa Telefone Inválido", "502444444", "123", "Rua D", "1000-004", "Coimbra");
				System.out.println("Telefone inválido passou (nao esperado)");
			} catch (IllegalArgumentException ex) {
				System.out.println("Telefone inválido bloqueado com sucesso: " + ex.getMessage());
			}
*/







			try {
				User authUser = loginService.execute("maria.costa@empresa.com", "MariaCosta@123");
				System.out.println("Login OK: " + authUser.getEmail());
			} catch (IllegalArgumentException ex) {
				System.out.println("Login falhou (nao esperado): " + ex.getMessage());
			}

			try {
				loginService.execute("maria.costa@empresa.com", "PasswordErrada");
				System.out.println("Login com password errada passou (nao esperado)");
			} catch (IllegalArgumentException ex) {
				System.out.println("Login com password errada bloqueado: " + ex.getMessage());
			}

			/*// Secçao de Fornecedores
			Fornecedor forn1 = fornecedorService.createFornecedor("Agrilac S.A.", "501234567", "agrilac@fornecedor.com", "+351910000001", "Rua dos Laticínios, 10, Porto", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2026, 12, 31))));
			Fornecedor forn2 = fornecedorService.createFornecedor("BioLeite Lda", "509876543", "bioleite@fornecedor.com", "+351910000002", "Avenida do Campo, 55, Braga", List.of(new FornecedorCertificacao(TipoCertificacao.BIO, "Certificação Biológica", LocalDate.of(2026, 4, 15)), new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 22000", LocalDate.of(2026, 9, 20))));
			Fornecedor forn3 = fornecedorService.createFornecedor("FrutasNorte", "508765432", "geral@frutasnorte.pt", "+351910000003", "Zona Agrícola Norte, Lote 4, Viseu", List.of(new FornecedorCertificacao(TipoCertificacao.BIO, "Bio Portugal", LocalDate.of(2026, 5, 10)), new FornecedorCertificacao(TipoCertificacao.HACCP, "HACCP Frutas", LocalDate.of(2026, 8, 15))));
			Fornecedor forn4 = fornecedorService.createFornecedor("Embalagens Silva", "507654321", "vendas@embalagensilva.pt", "+351910000004", "Rua das Indústrias, 88, Aveiro", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2026, 11, 30)), new FornecedorCertificacao(TipoCertificacao.BIO, "Embalagem Reciclável", LocalDate.of(2026, 7, 25))));
			Fornecedor forn5 = fornecedorService.createFornecedor("Especiarias do Mundo", "506543210", "contacto@especiarias.pt", "+351910000005", "Avenida das Especiarias, 42, Lisboa", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 22000", LocalDate.of(2026, 10, 18)), new FornecedorCertificacao(TipoCertificacao.OUTRA, "Comércio Justo", LocalDate.of(2026, 4, 12)), new FornecedorCertificacao(TipoCertificacao.BIO, "Bio Mundial", LocalDate.of(2026, 6, 8))));
			Fornecedor fornTel1 = fornecedorService.createFornecedor("Fornecedor Telefone 1", "505777777", "tel1@fornecedor.com", "915666777", "Rua Tel 1", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2027, 1, 10))));
			Fornecedor fornTel2 = fornecedorService.createFornecedor("Fornecedor Telefone 2", "505888888", "tel2@fornecedor.com", "+351 916 777 888", "Rua Tel 2", List.of(new FornecedorCertificacao(TipoCertificacao.HACCP, "HACCP", LocalDate.of(2027, 2, 10))));
			System.out.println("Telefones normalizados (fornecedores): " + fornTel1.getTelefone() + " | " + fornTel2.getTelefone());

			UUID fornId1 = forn1 != null ? forn1.getId() : null;
			UUID fornId2 = forn2 != null ? forn2.getId() : null;
			UUID fornId3 = forn3 != null ? forn3.getId() : null;

			// Matérias Primas
			MateriaPrima mpLeite = materiaPrimaService.createMateriaPrima("Leite de Vaca", "L", TipoMateriaPrima.BASES, new BigDecimal("0.000"), new BigDecimal("1000.000"), new BigDecimal("0.490"), fornId1);
			MateriaPrima mpAcucar = materiaPrimaService.createMateriaPrima("Açúcar Branco", "kg", TipoMateriaPrima.ADOCANTES, new BigDecimal("0.000"), new BigDecimal("80.000"), new BigDecimal("0.850"), fornId1);
			MateriaPrima mpMorango = materiaPrimaService.createMateriaPrima("Polpa de Morango", "kg", TipoMateriaPrima.SABOR, new BigDecimal("0.000"), new BigDecimal("30.000"), new BigDecimal("2.100"), fornId2);
			MateriaPrima mpLeitePo = materiaPrimaService.createMateriaPrima("Leite em Pó Inteira", "kg", TipoMateriaPrima.BASES, new BigDecimal("0.000"), new BigDecimal("50.000"), new BigDecimal("3.200"), fornId1);
			MateriaPrima mpFermento = materiaPrimaService.createMateriaPrima("Fermento Lácteo", "kg", TipoMateriaPrima.BASES, new BigDecimal("0.000"), new BigDecimal("5.000"), new BigDecimal("18.000"), fornId1);
			MateriaPrima mpEmbalagem = materiaPrimaService.createMateriaPrima("Embalagem Iogurte 125g", "un", TipoMateriaPrima.OUTRO, new BigDecimal("0.000"), new BigDecimal("10000.000"), new BigDecimal("0.045"), fornId1);
			MateriaPrima mpPessego = materiaPrimaService.createMateriaPrima("Polpa de Pêssego", "kg", TipoMateriaPrima.SABOR, new BigDecimal("0.000"), new BigDecimal("30.000"), new BigDecimal("1.900"), fornId2);
			MateriaPrima mpFramboesa = materiaPrimaService.createMateriaPrima("Polpa de Framboesa", "kg", TipoMateriaPrima.SABOR, new BigDecimal("0.000"), new BigDecimal("20.000"), new BigDecimal("3.500"), fornId2);
			MateriaPrima mpBaunilha = materiaPrimaService.createMateriaPrima("Extrato de Baunilha", "kg", TipoMateriaPrima.SABOR, new BigDecimal("0.000"), new BigDecimal("3.000"), new BigDecimal("22.000"), fornId2);
			MateriaPrima mpCacau = materiaPrimaService.createMateriaPrima("Cacau em Pó", "kg", TipoMateriaPrima.SABOR, new BigDecimal("0.000"), new BigDecimal("10.000"), new BigDecimal("5.800"), fornId2);
*/
			// Movimentos Stock MP
				// Entradas
			/*MovimentoStockMP m1 = movimentoStockMPService.registarMovimento(userId3, mpLeite.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial de leite");
			MovimentoStockMP m2 = movimentoStockMPService.registarMovimento(userId3, mpAcucar.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial de açúcar");
			MovimentoStockMP m3 = movimentoStockMPService.registarMovimento(userId4, mpLeitePo.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial leite em pó");
			MovimentoStockMP m4 = movimentoStockMPService.registarMovimento(userId3, mpFermento.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial fermento");
			MovimentoStockMP m5 = movimentoStockMPService.registarMovimento(userId4, mpEmbalagem.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial embalagens");
			MovimentoStockMP m6 = movimentoStockMPService.registarMovimento(userId4, mpMorango.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial polpa morango");
			MovimentoStockMP m7 = movimentoStockMPService.registarMovimento(userId3, mpPessego.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial polpa pêssego");
			MovimentoStockMP m8 = movimentoStockMPService.registarMovimento(userId3, mpFramboesa.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial polpa framboesa");
			MovimentoStockMP m9 = movimentoStockMPService.registarMovimento(userId4, mpBaunilha.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial extrato baunilha");
			MovimentoStockMP m10 = movimentoStockMPService.registarMovimento(userId3, mpCacau.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10000.000"), "Entrada inicial cacau");
*/
			// Saídas
			//MovimentoStockMP m11 = movimentoStockMPService.registarMovimento(userId3, mpLeite.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("100.000"), "Saída para produção");
			//MovimentoStockMP m12 = movimentoStockMPService.registarMovimento(userId4, mpAcucar.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("20.000"), "Saída para produção");
			//MovimentoStockMP m13 = movimentoStockMPService.registarMovimento(userId4, mpLeitePo.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("5.000"), "Saída para produção");
/*
			// Produtos Finais
			ProdutoFinal pNatural = produtoFinalService.createProduto(
					"YOG-NAT-125",
					"Iogurte Natural",
					null,
					21,
					new BigDecimal("0.35"),
					new BigDecimal("2.80"),
					1,
					List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.1000")),
							new ProdutoMateria(mpLeitePo, new BigDecimal("0.0030")),
							new ProdutoMateria(mpFermento, new BigDecimal("0.0008")),
							new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));

			ProdutoFinal pMorango = produtoFinalService.createProduto("YOG-MOR-125", "Iogurte de Morango", null, 21, new BigDecimal("0.42"), new BigDecimal("3.36"), 1, List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.0900")), new ProdutoMateria(mpLeitePo, new BigDecimal("0.0300")), new ProdutoMateria(mpFermento, new BigDecimal("0.0008")), new ProdutoMateria(mpMorango, new BigDecimal("0.0150")), new ProdutoMateria(mpAcucar, new BigDecimal("0.0080")), new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));
			ProdutoFinal pPessego = produtoFinalService.createProduto("YOG-PES-125", "Iogurte de Pêssego", null, 21, new BigDecimal("0.42"), new BigDecimal("3.36"), 1, List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.0900")), new ProdutoMateria(mpLeitePo, new BigDecimal("0.0300")), new ProdutoMateria(mpFermento, new BigDecimal("0.0008")), new ProdutoMateria(mpPessego, new BigDecimal("0.0150")), new ProdutoMateria(mpAcucar, new BigDecimal("0.0080")), new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));
			ProdutoFinal pFramboesa = produtoFinalService.createProduto("YOG-FRA-125", "Iogurte de Framboesa", null, 21, new BigDecimal("0.48"), new BigDecimal("3.84"), 1, List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.0900")), new ProdutoMateria(mpLeitePo, new BigDecimal("0.0300")), new ProdutoMateria(mpFermento, new BigDecimal("0.0008")), new ProdutoMateria(mpFramboesa, new BigDecimal("0.0150")), new ProdutoMateria(mpAcucar, new BigDecimal("0.0080")), new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));
			ProdutoFinal pBaunilha = produtoFinalService.createProduto("YOG-BAU-125", "Iogurte de Baunilha", null, 21, new BigDecimal("0.45"), new BigDecimal("3.60"), 1, List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.1000")), new ProdutoMateria(mpLeitePo, new BigDecimal("0.0300")), new ProdutoMateria(mpFermento, new BigDecimal("0.0008")), new ProdutoMateria(mpBaunilha, new BigDecimal("0.0010")), new ProdutoMateria(mpAcucar, new BigDecimal("0.0080")), new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));
			ProdutoFinal pChocolate = produtoFinalService.createProduto("YOG-CHO-125", "Iogurte de Chocolate", null, 21, new BigDecimal("0.45"), new BigDecimal("3.60"), 1, List.of(new ProdutoMateria(mpLeite, new BigDecimal("0.0950")), new ProdutoMateria(mpLeitePo, new BigDecimal("0.0300")), new ProdutoMateria(mpFermento, new BigDecimal("0.0008")), new ProdutoMateria(mpCacau, new BigDecimal("0.0080")), new ProdutoMateria(mpAcucar, new BigDecimal("0.0100")), new ProdutoMateria(mpEmbalagem, new BigDecimal("1"))));

			UUID ProdutoFinalId1 = pNatural != null ? pNatural.getId() : null;
			UUID ProdutoFinalId2 = pMorango != null ? pMorango.getId() : null;*/

			/*OrdemProducao ordem1 = ordemProducaoService.createOrdem(
					userId1, LocalDateTime.of(2026, 3, 26, 16, 5), LocalDateTime.of(2026, 3, 26, 16, 6), "observação",
					List.of(
							new OrdemProducaoProduto(null, ProdutoFinalId1, new BigDecimal("100.000")),
							new OrdemProducaoProduto(null, ProdutoFinalId2, new BigDecimal("50.000"))
					)
			);

			OrdemProducao ordem2 = ordemProducaoService.createOrdem(
					userId1, LocalDateTime.of(2026, 3, 26, 16, 5),LocalDateTime.of(2026, 3, 26, 16, 6), "observação",
					List.of(
							new OrdemProducaoProduto(null, ProdutoFinalId1, new BigDecimal("100.000"))
					)
			);

			PalletTipo pallet1 = palletTipoService.create("EUR Pallet (Standard)", new BigDecimal("1200.000"));
			PalletTipo pallet2 = palletTipoService.create("Industrial Pallet", new BigDecimal("800.000"));
			PalletTipo pallet3 = palletTipoService.create("Half Pallet", new BigDecimal("600.000"));

			UUID palletId1 = pallet1 != null ? pallet1.getId() : null;
			UUID palletId2 = pallet2 != null ? pallet2.getId() : null;
			UUID palletId3 = pallet3 != null ? pallet3.getId() : null;

			Encomenda encomenda1 = encomendaService.createEncomenda(
					userId5,
					List.of(
							new EncomendaPallet(ProdutoFinalId1, palletId3, 2, new BigDecimal("150.00")),
							new EncomendaPallet(ProdutoFinalId2, palletId3, 1, new BigDecimal("200.00"))
					)
			);*/

			// ============================================
			// TESTES DE DELETE
			// ============================================
			/*encomendaService.delete(encomenda1.getId());
			ordemProducaoService.delete(ordem2.getId());
			produtoFinalService.delete(pChocolate.getId());
			materiaPrimaService.delete(mpBaunilha.getId());
			if (palletId2 != null) {
				palletTipoService.delete(palletId2);
			}
			if (fornId3 != null) {
				fornecedorService.delete(fornId3);
			}
			userService.delete(userId4);
			empresaService.delete(empresaId3);

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