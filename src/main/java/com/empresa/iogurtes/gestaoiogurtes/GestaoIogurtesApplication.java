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
								 MateriaPrimaService materiaPrimaService,
								 MovimentoStockMPService movimentoStockMPService,
								 ProdutoFinalService produtoFinalService) {
		return args -> {
			// Secçao de Empresa
			Empresa e1 = empresaService.createEmpresa("LactoNorte - Cooperativa de Laticínios", "501234567", "+351252345678", "Rua dos Laticínios, 150", "4760-012", "Vila Nova de Famalicão");
			Empresa e2 = empresaService.createEmpresa("Frutas do Vale Lda", "509876543", "+351275123456", "Quinta da Fruta Fresca, Lote 12", "6230-456", "Fundão");
			Empresa e3 = empresaService.createEmpresa("Embalagens Alimentar S.A.", "508765432", "+351234567890", "Zona Industrial de Aveiro, Lote 8", "3800-123", "Aveiro");
			Empresa e4 = empresaService.createEmpresa("Açúcares & Mel Portugal", "507654321", "+351243987654", "Estrada Nacional 3, Km 145", "2000-123", "Santarém");
			Empresa e5 = empresaService.createEmpresa("Cacau & Especiarias Gourmet", "506543210", "+351213456789", "Avenida da República, 88, Piso 3", "1050-012", "Lisboa");

			UUID empresaId1 = e1 != null ? e1.getId() : null;
			UUID empresaId2 = e2 != null ? e2.getId() : null;
			UUID empresaId3 = e3 != null ? e3.getId() : null;
			UUID empresaId4 = e4 != null ? e4.getId() : null;
			UUID empresaId5 = e5 != null ? e5.getId() : null;


			// Secçao de Users
			User user1 = userService.createUser("Maria Costa", "maria.costa@empresa.com", "MariaCosta@123", "TARDE", LocalDate.of(2023, 5, 10), List.of("ADMIN"), null);
			User user2 = userService.createUser("António Silva", "antonio.silva@empresa.com", "AntonioSilva@456", null, LocalDate.of(2022, 8, 15), List.of("ADMIN"), null);
			User user3 = userService.createUser("Ana Ferreira", "ana.ferreira@empresa.com", "AnaFerreira@123", "MANHA", LocalDate.of(2024, 3, 1), List.of("FUNCIONARIO"), null);
			User user4 = userService.createUser("Bruno Lima", "bruno.lima@empresa.com", "BrunoLima@2024", "NOITE", LocalDate.of(2023, 11, 15), List.of("FUNCIONARIO"), null);
			User user5 = userService.createUser("Pedro Santos", "pedro.santos@empresa.com", "PedroSantos@12", null, null, List.of("EMPRESA"), empresaId1);
			User user6 = userService.createUser("Sofia Ribeiro", "sofia.ribeiro@empresa.com", "SofiaRibeiro@99", null, null, List.of("EMPRESA"), empresaId2);
			User user7 = userService.createUser("Francisco Esteves", "francisco.esteves@empresa.com", "FranciscoEsteves@789","MANHA",LocalDate.of(2024, 1, 20),List.of("ADMIN",  "FUNCIONARIO"), null);

			UUID userId1 = user1 != null ? user1.getId() : null;
			UUID userId2 = user2 != null ? user2.getId() : null;
			UUID userId3 = user3 != null ? user3.getId() : null;
			UUID userId4 = user4 != null ? user4.getId() : null;

			User user1Att = userService.updateUser(userId1, "Maria Costinha", "NOITE", List.of("ADMIN", "FUNCIONARIO"));

			// Secçao de Fornecedores
			Fornecedor forn1 = fornecedorService.createFornecedor("Agrilac S.A.", "501234567", "agrilac@fornecedor.com", "+351910000001", "Rua dos Laticínios, 10, Porto", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2026, 12, 31))));
			Fornecedor forn2 = fornecedorService.createFornecedor("BioLeite Lda", "509876543", "bioleite@fornecedor.com", "+351910000002", "Avenida do Campo, 55, Braga", List.of(new FornecedorCertificacao(TipoCertificacao.BIO, "Certificação Biológica", LocalDate.of(2026, 4, 15)), new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 22000", LocalDate.of(2026, 9, 20))));
			Fornecedor forn3 = fornecedorService.createFornecedor("FrutasNorte", "508765432", "geral@frutasnorte.pt", "+351910000003", "Zona Agrícola Norte, Lote 4, Viseu", List.of(new FornecedorCertificacao(TipoCertificacao.BIO, "Bio Portugal", LocalDate.of(2026, 5, 10)), new FornecedorCertificacao(TipoCertificacao.HACCP, "HACCP Frutas", LocalDate.of(2026, 8, 15))));
			Fornecedor forn4 = fornecedorService.createFornecedor("Embalagens Silva", "507654321", "vendas@embalagensilva.pt", "+351910000004", "Rua das Indústrias, 88, Aveiro", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 9001", LocalDate.of(2026, 11, 30)), new FornecedorCertificacao(TipoCertificacao.BIO, "Embalagem Reciclável", LocalDate.of(2026, 7, 25))));
			Fornecedor forn5 = fornecedorService.createFornecedor("Especiarias do Mundo", "506543210", "contacto@especiarias.pt", "+351910000005", "Avenida das Especiarias, 42, Lisboa", List.of(new FornecedorCertificacao(TipoCertificacao.ISO, "ISO 22000", LocalDate.of(2026, 10, 18)), new FornecedorCertificacao(TipoCertificacao.OUTRA, "Comércio Justo", LocalDate.of(2026, 4, 12)), new FornecedorCertificacao(TipoCertificacao.BIO, "Bio Mundial", LocalDate.of(2026, 6, 8))));

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

			// Movimentos Stock MP
				// Entradas
			MovimentoStockMP m1 = movimentoStockMPService.registarMovimento(userId3, mpLeite.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("5000.000"), "Entrada inicial de leite");
			MovimentoStockMP m2 = movimentoStockMPService.registarMovimento(userId3, mpAcucar.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("300.000"), "Entrada inicial de açúcar");
			MovimentoStockMP m3 = movimentoStockMPService.registarMovimento(userId4, mpLeitePo.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("100.000"), "Entrada inicial leite em pó");
			MovimentoStockMP m4 = movimentoStockMPService.registarMovimento(userId3, mpFermento.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("10.000"), "Entrada inicial fermento");
			MovimentoStockMP m5 = movimentoStockMPService.registarMovimento(userId4, mpEmbalagem.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("5000.000"), "Entrada inicial embalagens");
			MovimentoStockMP m6 = movimentoStockMPService.registarMovimento(userId4, mpMorango.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("50.000"), "Entrada inicial polpa morango");
			MovimentoStockMP m7 = movimentoStockMPService.registarMovimento(userId3, mpPessego.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("40.000"), "Entrada inicial polpa pêssego");
			MovimentoStockMP m8 = movimentoStockMPService.registarMovimento(userId3, mpFramboesa.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("30.000"), "Entrada inicial polpa framboesa");
			MovimentoStockMP m9 = movimentoStockMPService.registarMovimento(userId4, mpBaunilha.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("5.000"), "Entrada inicial extrato baunilha");
			MovimentoStockMP m10 = movimentoStockMPService.registarMovimento(userId3, mpCacau.getId(), TipoMovimentoMP.ENTRADA, new BigDecimal("20.000"), "Entrada inicial cacau");

			// Saídas
			MovimentoStockMP m11 = movimentoStockMPService.registarMovimento(userId3, mpLeite.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("100.000"), "Saída para produção");
			MovimentoStockMP m12 = movimentoStockMPService.registarMovimento(userId4, mpAcucar.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("20.000"), "Saída para produção");
			MovimentoStockMP m13 = movimentoStockMPService.registarMovimento(userId4, mpLeitePo.getId(), TipoMovimentoMP.SAIDA, new BigDecimal("5.000"), "Saída para produção");

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

			UUID ProdutoFinalId1 = pMorango != null ? pMorango.getId() : null;
			// ============================================
			// LISTAGENS FINAIS
			// ============================================
			System.out.println("\n========== LISTAGEM DE EMPRESAS ==========");
			empresaService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE USERS ==========");
			userService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE FORNECEDORES ==========");
			fornecedorService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE MATÉRIAS PRIMAS ==========");
			materiaPrimaService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE MOVIMENTOS ==========");
			movimentoStockMPService.getAll().forEach(System.out::println);

			System.out.println("\n========== LISTAGEM DE PRODUTOS FINAIS ==========");
			produtoFinalService.getAll().forEach(System.out::println);

			//produtoFinalService.getMateriasByProdutoId(ProdutoFinalId1)
			//		.forEach(System.out::println);
		};
	}
}