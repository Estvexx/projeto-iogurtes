# Data Access Layer (DAL) Analysis

## 1. Technology stack
- **ORM framework**: Spring Data JPA (Hibernate underlying). Version specified via Spring Boot `4.0.3` starter parent.
- **Database connection config**: Configured in `application.properties`.
- **Approach**: Code First (schema generated via `spring.jpa.hibernate.ddl-auto=create`).
- **Additional persistence libraries**: PostgreSQL JDBC driver (`org.postgresql:postgresql`).

---

## 2. Entity classes

For all entities, **Lombok is NOT used**. Standard constructors, getters, and setters are explicitly defined.

### `ConsumoProducao` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "consumos_producao", uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "materia_id"}))`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `ordem`: `OrdemProducao` with `@ManyToOne`, `@JoinColumn(name = "ordem_id", nullable = false)`
  - `materia`: `MateriaPrima` with `@ManyToOne`, `@JoinColumn(name = "materia_id", nullable = false)`
  - `quantidadeKg`: `BigDecimal` with `@Column(name = "quantidade_kg", nullable = false, precision = 12, scale = 3)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

### `Empresa` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "empresas")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `nomeEmpresa`: `String` with `@Column(name = "nome_empresa", nullable = false, length = 150)`
  - `nipc`: `String` with `@Column(name = "nipc", unique = true, nullable = false, length = 20)`
  - `telefone`: `String` with `@Column(name = "telefone", length = 20)`
  - `morada`: `String` with `@Column(name = "morada", length = 200)`
  - `codigoPostal`: `String` with `@Column(name = "codigo_postal", length = 20)`
  - `cidade`: `String` with `@Column(name = "cidade", length = 100)`

### `Encomenda` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "encomendas")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `user`: `User` with `@ManyToOne`, `@JoinColumn(name = "user_id")`
  - `estado`: `EstadoEncomenda` with `@Enumerated(EnumType.STRING)`, `@Column(name = "estado")`
  - `dataEncomenda`: `LocalDateTime` with `@Column(name = "data_encomenda")`
  - `totalPreco`: `BigDecimal` with `@Column(name = "total_preco", precision = 12, scale = 2)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `pallets`: `List<EncomendaPallet>` with `@OneToMany(mappedBy = "encomenda", cascade = CascadeType.ALL, orphanRemoval = true)`

### `EncomendaOrdem` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "encomenda_ordens", uniqueConstraints = @UniqueConstraint(columnNames = {"ordem_id", "encomenda_pallet_id"}))`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `ordem`: `OrdemProducao` with `@ManyToOne`, `@JoinColumn(name = "ordem_id", nullable = false)`
  - `encomendaPallet`: `EncomendaPallet` with `@ManyToOne`, `@JoinColumn(name = "encomenda_pallet_id", nullable = false)`
  - `quantidadePallets`: `Integer` with `@Column(name = "quantidade_pallets", nullable = false)`
  - `estado`: `EstadoEncomendaOrdem` with `@Enumerated(EnumType.STRING)`, `@Column(name = "estado", nullable = false)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`

### `EncomendaPallet` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "encomenda_pallets", uniqueConstraints = @UniqueConstraint(columnNames = {"encomenda_id", "produto_id", "pallet_tipo_id"}))`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `encomenda`: `Encomenda` with `@ManyToOne`, `@JoinColumn(name = "encomenda_id", nullable = false)`
  - `produto`: `ProdutoFinal` with `@ManyToOne`, `@JoinColumn(name = "produto_id", nullable = false)`
  - `palletTipo`: `PalletTipo` with `@ManyToOne`, `@JoinColumn(name = "pallet_tipo_id", nullable = false)`
  - `quantidadePallets`: `Integer` with `@Column(name = "quantidade_pallets", nullable = false)`
  - `precoPorPallet`: `BigDecimal` with `@Column(name = "preco_por_pallet", nullable = false, precision = 10, scale = 2)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `ordens`: `List<EncomendaOrdem>` with `@OneToMany(mappedBy = "encomendaPallet", cascade = CascadeType.ALL, orphanRemoval = true)`

### `Fornecedor` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "fornecedores")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `nome`: `String` with `@Column(name = "nome", nullable = false, length = 150)`
  - `nif`: `String` with `@Column(name = "nif", unique = true, length = 20)`
  - `email`: `String` with `@Column(name = "email", length = 150)`
  - `telefone`: `String` with `@Column(name = "telefone", length = 20)`
  - `morada`: `String` with `@Column(name = "morada", length = 200)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `certificacoes`: `List<FornecedorCertificacao>` with `@OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)`

### `FornecedorCertificacao` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "fornecedor_certificacoes")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `fornecedor`: `Fornecedor` with `@ManyToOne`, `@JoinColumn(name = "fornecedor_id")`
  - `tipo`: `TipoCertificacao` with `@Enumerated(EnumType.STRING)`, `@Column(name = "tipo", nullable = false)`
  - `descricao`: `String` with `@Column(name = "descricao", length = 120)`
  - `validade`: `LocalDate` with `@Column(name = "validade")`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

### `MateriaPrima` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "materias_primas")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `nome`: `String` with `@Column(name = "nome", nullable = false, length = 120)`
  - `tipo`: `TipoMateriaPrima` with `@Enumerated(EnumType.STRING)`, `@Column(name = "tipo", nullable = false)`
  - `unidade`: `String` with `@Column(name = "unidade", length = 10)`
  - `stockAtual`: `BigDecimal` with `@Column(name = "stock_atual", precision = 12, scale = 3)`
  - `stockMinimo`: `BigDecimal` with `@Column(name = "stock_minimo", precision = 12, scale = 3)`
  - `precoUnitario`: `BigDecimal` with `@Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)`
  - `fornecedor`: `Fornecedor` with `@ManyToOne`, `@JoinColumn(name = "fornecedor_id", nullable = false)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`

### `MovimentoStockMP` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "movimentos_stock_mp")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `user`: `User` with `@ManyToOne`, `@JoinColumn(name = "user_id", nullable = false)`
  - `materia`: `MateriaPrima` with `@ManyToOne`, `@JoinColumn(name = "materia_id", nullable = false)`
  - `tipo`: `TipoMovimentoMP` with `@Enumerated(EnumType.STRING)`, `@Column(name = "tipo", nullable = false)`
  - `quantidade`: `BigDecimal` with `@Column(name = "quantidade", nullable = false, precision = 12, scale = 3)`
  - `observacao`: `String` with `@Column(name = "observacao", length = 200)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

### `MovimentoStockPF` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "movimentos_stock_pf")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `user`: `User` with `@ManyToOne`, `@JoinColumn(name = "user_id", nullable = false)`
  - `produto`: `ProdutoFinal` with `@ManyToOne`, `@JoinColumn(name = "produto_id", nullable = false)`
  - `tipo`: `TipoMovimentoPF` with `@Enumerated(EnumType.STRING)`, `@Column(name = "tipo", nullable = false)`
  - `quantidadeKg`: `Integer` with `@Column(name = "quantidade_kg", nullable = false)`
  - `observacao`: `String` with `@Column(name = "observacao", length = 200)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

### `OrdemProducao` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "ordens_producao")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `produto`: `ProdutoFinal` with `@ManyToOne`, `@JoinColumn(name = "produto_id", nullable = false)`
  - `quantidadeKg`: `BigDecimal` with `@Column(name = "quantidade_kg", precision = 12, scale = 3)`
  - `estado`: `EstadoOrdem` with `@Enumerated(EnumType.STRING)`, `@Column(name = "estado")`
  - `dataInicio`: `LocalDateTime` with `@Column(name = "data_inicio")`
  - `dataFim`: `LocalDateTime` with `@Column(name = "data_fim")`
  - `user`: `User` with `@ManyToOne`, `@JoinColumn(name = "user_id")`
  - `aprovadoEm`: `LocalDateTime` with `@Column(name = "aprovado_em")`
  - `observacoes`: `String` with `@Column(name = "observacoes")`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `consumos`: `List<ConsumoProducao>` with `@OneToMany(mappedBy = "ordem", cascade = CascadeType.ALL, orphanRemoval = true)`

### `PalletTipo` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "pallet_tipos")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `nome`: `String` with `@Column(name = "nome", nullable = false, length = 80)`
  - `capacidadeKg`: `BigDecimal` with `@Column(name = "capacidade_kg", nullable = false, precision = 10, scale = 3)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`

### `ProdutoFinal` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "produtos_finais")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `codigoSku`: `String` with `@Column(name = "codigo_sku", unique = true, nullable = false, length = 50)`
  - `nome`: `String` with `@Column(name = "nome", nullable = false, length = 120)`
  - `descricao`: `String` with `@Column(name = "descricao")`
  - `validadeDias`: `Integer` with `@Column(name = "validade_dias")`
  - `precoVenda`: `BigDecimal` with `@Column(name = "preco_venda", precision = 10, scale = 2)`
  - `precoPorKg`: `BigDecimal` with `@Column(name = "preco_por_kg", precision = 10, scale = 2)`
  - `visivelCliente`: `Boolean` with `@Column(name = "visivel_cliente")`
  - `stockAtual`: `Integer` with `@Column(name = "stock_atual")`
  - `quantidadeLote`: `Integer` with `@Column(name = "quantidade_lote", nullable = false)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `materias`: `List<ProdutoMateria>` with `@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)`

### `ProdutoMateria` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "produto_materias", uniqueConstraints = @UniqueConstraint(columnNames = {"produto_id", "materia_id"}))`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `produto`: `ProdutoFinal` with `@ManyToOne`, `@JoinColumn(name = "produto_id", nullable = false)`
  - `materia`: `MateriaPrima` with `@ManyToOne`, `@JoinColumn(name = "materia_id", nullable = false)`
  - `quantidadePorUnidadeProduto`: `BigDecimal` with `@Column(name = "quantidade_por_unidade_produto", nullable = false, precision = 12, scale = 3)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

### `User` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "users")`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `empresa`: `Empresa` with `@ManyToOne`, `@JoinColumn(name = "empresa_id")`
  - `nome`: `String` with `@Column(name = "nome", nullable = false, length = 100)`
  - `email`: `String` with `@Column(name = "email", unique = true, nullable = false, length = 150)`
  - `passwordHash`: `String` with `@Column(name = "password_hash", nullable = false)`
  - `turno`: `TurnoTipo` with `@Enumerated(EnumType.STRING)`, `@Column(name = "turno")`
  - `dataAdmissao`: `LocalDate` with `@Column(name = "data_admissao")`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`
  - `updatedAt`: `LocalDateTime` with `@Column(name = "updatedat")`
  - `roles`: `List<UserRole>` with `@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)`

### `UserRole` (`com.empresa.iogurtes.gestaoiogurtes.core.model`)
- **Table Name**: `@Table(name = "user_roles", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}))`
- **Fields**:
  - `id`: `UUID` with `@Id`, `@GeneratedValue(strategy = GenerationType.UUID)`
  - `user`: `User` with `@ManyToOne`, `@JoinColumn(name = "user_id", nullable = false)`
  - `role`: `UserRoleType` with `@Enumerated(EnumType.STRING)`, `@Column(name = "role", nullable = false)`
  - `createdAt`: `LocalDateTime` with `@Column(name = "createdat")`

---

## 3. Repository interfaces

All existing repositories extend Spring Data `JpaRepository<Entity, UUID>`.
No `@Query`, `@Modifying`, or `@Transactional` annotations were found. Standard query derivation methods are used. No `@Param` annotations are used since there are no custom JPQL queries.

### `EmpresaRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<Empresa, UUID>`
- **Methods**:
  ```java
  Optional<Empresa> findByNipc(String nipc);
  boolean existsByNipc(String nipc);
  boolean existsByNipcAndIdNot(String nipc, UUID id);
  ```

### `FornecedorRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<Fornecedor, UUID>`
- **Methods**:
  ```java
  boolean existsByEmail(String email);
  boolean existsByNif(String nif);
  ```

### `MateriaPrimaRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<MateriaPrima, UUID>`
- **Methods**:
  ```java
  boolean existsByNome(String nome);
  ```

### `MovimentoStockMPRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<MovimentoStockMP, UUID>`
- **Methods**:
  ```java
  List<MovimentoStockMP> findByMateriaId(UUID materiaId);
  List<MovimentoStockMP> findByUserId(UUID userId);
  ```

### `ProdutoFinalRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<ProdutoFinal, UUID>`
- **Methods**:
  ```java
  boolean existsByCodigoSku(String codigoSku);
  boolean existsByNome(String nome);
  boolean existsByNomeAndIdNot(String nome, UUID id);
  boolean existsByCodigoSkuAndIdNot(String codigoSku, UUID id);
  ```

### `UserRepository` (`com.empresa.iogurtes.gestaoiogurtes.core.repository`)
- **Extends**: `JpaRepository<User, UUID>`
- **Methods**:
  ```java
  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);
  ```

---

## 4. Database configuration

- **URL**: `jdbc:postgresql://localhost:5432/iogurtes_db`
- **Driver**: `org.postgresql.Driver`
- **Username**: `postgres`
- **Password**: `***` (masked)
- **Connection pool**: Default (HikariCP, configured via Spring Boot defaults)
- **Transaction manager**: Default (Spring Data JPA JpaTransactionManager)
- **Schema generation (`spring.jpa.hibernate.ddl-auto`)**: `create`
- **Dialect**: `org.hibernate.dialect.PostgreSQLDialect`
- **Other JPA/Hibernate properties**:
  - `spring.jpa.show-sql=true`
  - `spring.jpa.properties.hibernate.format_sql=true`

---

## 5. Enums used in persistence

All enums below are mapped using `@Enumerated(EnumType.STRING)`.

### `EstadoEncomenda` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `pendente`, `confirmada`, `expedida`, `entregue`, `cancelada`
- **Used in**: `Encomenda.estado`

### `EstadoEncomendaOrdem` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `pendente`, `produzido`, `expedido`
- **Used in**: `EncomendaOrdem.estado`

### `EstadoOrdem` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `rascunho`, `aguarda_aprovacao`, `em_producao`, `concluida`, `cancelada`
- **Used in**: `OrdemProducao.estado`

### `TipoCertificacao` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `ISO`, `BIO`, `HACCP`, `OUTRA`
- **Used in**: `FornecedorCertificacao.tipo`

### `TipoMateriaPrima` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `BASES`, `ADOCANTES`, `SABOR`, `OUTRO`
- **Used in**: `MateriaPrima.tipo`

### `TipoMovimentoMP` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `ENTRADA`, `SAIDA`, `AJUSTE`
- **Used in**: `MovimentoStockMP.tipo`

### `TipoMovimentoPF` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `producao`, `expedicao`, `ajuste`, `devolucao`
- **Used in**: `MovimentoStockPF.tipo`

### `TurnoTipo` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `MANHA`, `TARDE`, `NOITE`
- **Used in**: `User.turno`

### `UserRoleType` (`com.empresa.iogurtes.gestaoiogurtes.core.model.enums`)
- **Values**: `ADMIN`, `FUNCIONARIO`, `EMPRESA`
- **Used in**: `UserRole.role`

---

## 6. Package structure

- **Entities**: `com.empresa.iogurtes.gestaoiogurtes.core.model`
- **Enums**: `com.empresa.iogurtes.gestaoiogurtes.core.model.enums` (sub-package from entities)
- **Repositories**: `com.empresa.iogurtes.gestaoiogurtes.core.repository`
- **Config**: `com.empresa.iogurtes.gestaoiogurtes.core.config`

There are no interfaces or abstract base classes used across the models in addition to the standard classes listed above.

---

## 7. What is NOT yet implemented

- **Missing Repository Interfaces**: Given the existing 16 entities, 10 entities currently do NOT have an associated Spring Data Repository interface:
  - `ConsumoProducaoRepository`
  - `EncomendaRepository`
  - `EncomendaOrdemRepository`
  - `EncomendaPalletRepository`
  - `FornecedorCertificacaoRepository`
  - `MovimentoStockPFRepository`
  - `OrdemProducaoRepository`
  - `PalletTipoRepository`
  - `ProdutoMateriaRepository`
  - `UserRoleRepository`

- **TODO/Placeholder Comments**: Not found in any of the mapped entity, repository or enumeration files.
- **Incomplete mappings/Custom behavior**: 
  - There are no custom JPQL queries currently required or specified via `@Query` for the existing repositories.
  - Transactions rely entirely on the service layer, no `@Transactional` annotation handles implicit commits down on the repository tier level explicitly.
  - Fetch Types (such as `FetchType.LAZY` or `FetchType.EAGER`) are not explicitly defined; the JPA default semantics (`@ManyToOne` defaults to `EAGER`, `@OneToMany` defaults to `LAZY`) are taking effect unconfigured.
  - Missing sequence generators (all rely universally on `UUID` generation strategy rather than DB sequences).
