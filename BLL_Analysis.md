# Business Logic Layer (BLL) Analysis

## 1. Service classes

### `EmpresaService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `EmpresaRepository empresaRepository` (Constructor injection)
  - `EmpresaValidator empresaValidator` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public Empresa createEmpresa(String nomeEmpresa, String nipc, String telefone, String morada, String codigoPostal, String cidade)`
    - **@Transactional**: None.
    - **Description**: Validates input data, creates an `Empresa` instance, and saves it.
    - **Business rules**: Calls `empresaValidator.validateCreateEmpresa`.
    - **Exceptions**: Bubbles `IllegalArgumentException` from validator.
  - `public Empresa getById(UUID id)`
    - **@Transactional**: None.
    - **Description**: Retrieves an `Empresa` by ID.
    - **Business rules**: Throws exception if not found.
    - **Exceptions**: Throws `IllegalArgumentException("Empresa não encontrada!")` if no empresa corresponds to the given ID.
  - `public List<Empresa> getAll()`
    - **@Transactional**: None.
    - **Description**: Retrieves all `Empresa` records.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public Empresa update(UUID id, String nomeEmpresa, String nipc, String telefone, String morada, String codigoPostal, String cidade)`
    - **@Transactional**: None.
    - **Description**: Validates input, retrieves existing entity, updates its properties, and persists it.
    - **Business rules**: Calls `empresaValidator.validateUpdateEmpresa`. Fetches the entity via `getById(id)` to ensure it exists before proceeding.
    - **Exceptions**: Throws `IllegalArgumentException("Empresa não encontrada!")` (via `getById`) if it doesn't exist, plus validator exceptions.
  - `public void delete(UUID id)`
    - **@Transactional**: None.
    - **Description**: Retrieves existing entity and deletes it.
    - **Business rules**: Entity must exist to be deleted.
    - **Exceptions**: Throws `IllegalArgumentException("Empresa não encontrada!")` (via `getById`) if it doesn't exist.

### `FornecedorService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `FornecedorRepository fornecedorRepository` (Constructor injection)
  - `FornecedorValidator fornecedorValidator` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public Fornecedor createFornecedor(String nome, String nif, String email, String telefone, String morada, List<FornecedorCertificacao> certificacoes)`
    - **@Transactional**: None.
    - **Description**: Validates and maps the relationship from the parent `Fornecedor` to the child `certificacoes` instances before persisting.
    - **Business rules**: Calls `fornecedorValidator.validateCreateFornecedor`. Iterates over `certificacoes` setting `materia.setFornecedor(fornecedor)` to establish bidirectional relationship explicitly.
    - **Exceptions**: Bubbles validator exceptions.
  - `public Fornecedor updateFornecedor(UUID id, String nome, String nif, String email, String telefone, String morada)`
    - **@Transactional**: None.
    - **Description**: Retrieves entity, validates string fields, applies changes, and saves.
    - **Business rules**: Calls `fornecedorValidator.validateUpdateFornecedor`.
    - **Exceptions**: Throws `IllegalArgumentException("Fornecedor não encontrado")` if it doesn't exist.
  - `public Fornecedor getById(UUID id)`
    - **@Transactional**: None.
    - **Description**: Fetches `Fornecedor` by ID.
    - **Business rules**: None.
    - **Exceptions**: Throws `IllegalArgumentException("Fornecedor não encontrado")` if missing.
  - `public List<Fornecedor> getAll()`
    - **@Transactional**: None.
    - **Description**: Retrieves all records.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public void delete(UUID id)`
    - **@Transactional**: None.
    - **Description**: Retrieves entity and deletes.
    - **Business rules**: Entity must exist.
    - **Exceptions**: Throws `IllegalArgumentException("Fornecedor não encontrado")` if missing.

### `MateriaPrimaService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `MateriaPrimaRepository materiaPrimaRepository` (Constructor injection)
  - `MateriaPrimaValidator materiaPrimaValidator` (Constructor injection)
  - `FornecedorRepository fornecedorRepository` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public MateriaPrima createMateriaPrima(String nome, String unidade, TipoMateriaPrima tipo, BigDecimal stockAtual, BigDecimal stockMinimo, BigDecimal precoUnitario, UUID fornecedorId)`
    - **@Transactional**: None.
    - **Description**: Validates, links a `Fornecedor` via reference, instantiates and saves.
    - **Business rules**: Resolves `fornecedorRepository.getReferenceById(fornecedorId)` without full entity fetch query. Calls validator.
    - **Exceptions**: Bubbles validator exceptions.
  - `public MateriaPrima updateMateriaPrima(UUID id, String nome, String unidade, TipoMateriaPrima tipo, BigDecimal stockMinimo, BigDecimal precoUnitario, UUID fornecedorId)`
    - **@Transactional**: None.
    - **Description**: Retrieves entity and supplier reference, validates and updates.
    - **Business rules**: Calls validator, updates fields, updates relation to `Fornecedor` by fetching a database reference object.
    - **Exceptions**: Throws `IllegalArgumentException("Matéria prima não encontrada")` if missing.
  - `public MateriaPrima getById(UUID id)`
    - **@Transactional**: None.
    - **Description**: Retrieves `MateriaPrima` by ID.
    - **Business rules**: None.
    - **Exceptions**: Throws `IllegalArgumentException("Matéria prima não encontrada")`.
  - `public List<MateriaPrima> getAll()`
    - **@Transactional**: None.
    - **Description**: Retrieves all items.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public void delete(UUID id)`
    - **@Transactional**: None.
    - **Description**: Validates existence and deletes.
    - **Business rules**: Entity must exist.
    - **Exceptions**: Throws `IllegalArgumentException("Matéria prima não encontrada")` if missing.

### `MovimentoStockMPService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `MovimentoStockMPRepository movimentoRepository` (Constructor injection)
  - `MateriaPrimaRepository materiaPrimaRepository` (Constructor injection)
  - `UserRepository userRepository` (Constructor injection)
  - `MovimentoStockMPValidator validator` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public MovimentoStockMP registarMovimento(UUID userId, UUID materiaId, TipoMovimentoMP tipo, BigDecimal quantidade, String observacao)`
    - **@Transactional**: Present (no attributes).
    - **Description**: Registers a new stock movement and updates the current stock of the corresponding raw material.
    - **Business rules**: 
      - Validates domain logic using `validator.validateMovimento`.
      - Requires existing `MateriaPrima`.
      - **Stock Update Calculation**: Adjusts the related `MateriaPrima` stock dynamically using a switch statement handling `ENTRADA` (adds), `SAIDA` (subtracts) and `AJUSTE` (overwrites). 
      - Persists the modified `materiaPrimaRepository.save(materia)` prior to saving the `MovimentoStockMP`.
    - **Exceptions**: Throws `IllegalArgumentException("Matéria prima não encontrada!")` if missing.
  - `public List<MovimentoStockMP> getByMateria(UUID materiaId)`
    - **@Transactional**: None.
    - **Description**: Reads movements mapped to specific `MateriaPrima`.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public List<MovimentoStockMP> getByUser(UUID userId)`
    - **@Transactional**: None.
    - **Description**: Reads movements mapped to specific `User`.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public List<MovimentoStockMP> getAll()`
    - **@Transactional**: None.
    - **Description**: Retrieves all movements.
    - **Business rules**: None.
    - **Exceptions**: None.

### `ProdutoFinalService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `ProdutoFinalRepository produtoFinalRepository` (Constructor injection)
  - `MateriaPrimaRepository materiaPrimaRepository` (Constructor injection)
  - `ProdutoFinalValidator produtoFinalValidator` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public ProdutoFinal createProduto(String codigoSku, String nome, String descricao, Integer validadeDias, BigDecimal precoVenda, BigDecimal precoPorKg, Integer quantidadeLote, List<ProdutoMateria> materias)`
    - **@Transactional**: None.
    - **Description**: Validates input, creates product, configures child ingredient associations natively, saves product.
    - **Business rules**: Traverses provided `ProdutoMateria` configurations, mapping their parent to this product directly using `materia.setProduto(produto)`.
    - **Exceptions**: Bubbles validator exceptions.
  - `public ProdutoFinal updateProduto(UUID id, String nome, String descricao, Integer validadeDias, BigDecimal precoVenda, BigDecimal precoPorKg, Integer quantidadeLote, Boolean visivelCliente)`
    - **@Transactional**: None.
    - **Description**: Validates update criteria and overwrites specific primitive fields on database product instance.
    - **Business rules**: Note that ingredients list (`materias`) is NOT updated or modifiable through this method setup.
    - **Exceptions**: Throws `IllegalArgumentException("Produto não encontrado!")`.
  - `public ProdutoFinal getById(UUID id)`
    - **@Transactional**: None.
    - **Description**: Fetches `ProdutoFinal` by ID.
    - **Business rules**: None.
    - **Exceptions**: Throws `IllegalArgumentException("Produto não encontrado!")`.
  - `public List<ProdutoFinal> getAll()`
    - **@Transactional**: None.
    - **Description**: Retrieves all `ProdutoFinal` items.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public void delete(UUID id)`
    - **@Transactional**: None.
    - **Description**: Checks for existence and deletes.
    - **Business rules**: Must exist in DB before proceeding to delete command.
    - **Exceptions**: Throws `IllegalArgumentException("Produto não encontrado!")`.

### `UserService` (`com.empresa.iogurtes.gestaoiogurtes.core.service`)
- **Dependencies Injected**:
  - `UserRepository userRepository` (Constructor injection)
  - `UserValidator userValidator` (Constructor injection)
  - `BCryptPasswordEncoder passwordEncoder` (Constructor injection)
  - `EmpresaRepository empresaRepository` (Constructor injection)
- **Class-level `@Transactional`**: Not present.
- **Methods**:
  - `public User createUser(String nome, String email, String password, TurnoTipo turno, LocalDate dataAdmissao, List<UserRole> roles, UUID empresaId)`
    - **@Transactional**: None.
    - **Description**: Validates string formatting / constraints, securely encodes the password, builds model objects fixing bidirectional parent maps.
    - **Business rules**: Generates encoded password via `passwordEncoder.encode(password)`. Conditionally resolves existing reference entity for `Empresa` if `empresaId` is given. Sets associations iteratively onto each mapped `UserRole`. 
    - **Exceptions**: Bubbles validator limits.
  - `public User updateUser(UUID id, String nome, TurnoTipo turno)`
    - **@Transactional**: None.
    - **Description**: Basic properties update interface for a User. Allows modifying standard fields like name. 
    - **Business rules**: Performs explicit check on roles by feeding them back into validator check via `user.getRoles()` fetch. Wait... the role isn't modifiable natively through this method execution path. 
    - **Exceptions**: Throws `IllegalArgumentException("Utilizador não encontrado")`.
  - `public User getById(UUID id)`
    - **@Transactional**: None.
    - **Description**: Resolves single `User`.
    - **Business rules**: None.
    - **Exceptions**: Throws `IllegalArgumentException("Utilizador não encontrado")`.
  - `public List<User> getAll()`
    - **@Transactional**: None.
    - **Description**: Lists all user entities.
    - **Business rules**: None.
    - **Exceptions**: None.
  - `public void delete(UUID id)`
    - **@Transactional**: None.
    - **Description**: Ensures user is strictly retrievable before dispatching delete request logic.
    - **Business rules**: Must exist in DB prior to proceeding.
    - **Exceptions**: Throws `IllegalArgumentException("Utilizador não encontrado")`.

---

## 2. Business rules implemented

### `MovimentoStockMPService.registarMovimento`
- **Logic Description**:
  1. Executes validator checks for the stock movement (negative amounts, ID verifications).
  2. Fetches the associated `User` proxy entity by proxy reference.
  3. Uses `materiaPrimaRepository.findById(materiaId)` to load existing Raw Material state.
  4. Modifies the material's `stockAtual` calculation conditionally via a switch:
     - IF `ENTRADA` -> `materia.setStockAtual(materia.getStockAtual().add(quantidade))`
     - IF `SAIDA` -> `materia.setStockAtual(materia.getStockAtual().subtract(quantidade))`
     - IF `AJUSTE` -> `materia.setStockAtual(quantidade)`
  5. **Side Effect**: Instantly triggers `materiaPrimaRepository.save(materia)` to propagate stock alterations into the database.
  6. Continues to instantiate a new `MovimentoStockMP` transaction log object using the freshly updated values.
  7. Finishes executing by calling `movimentoRepository.save(movimento)`. 

### `ProdutoFinalService.createProduto`
- **Logic Description**:
  1. Validates standard variables for creation.
  2. Spawns root `ProdutoFinal` instance holding main primitives.
  3. Executes an enhancement for-loop linking bidirectional relation mapping on its list: `materia.setProduto(produto)`. 
  4. Stores the explicit list mapping memory back locally onto the parent object properties before triggering the JPA engine. 

### `FornecedorService.createFornecedor`
- **Logic Description**:
  1. Validates parameters mapping logic. 
  2. Spawns root `Fornecedor` instance.
  3. Links child components on bidirectional loop iterations: `certificacao.setFornecedor(fornecedor)`.
  4. Sets entire mapped Array list into instance properties to cascade inserts effectively later into the data engine automatically.

### `UserService.createUser`
- **Logic Description**: 
  1. Rejects invalid credentials passing rules engine limits.
  2. Handles encoding of raw passwords with `.encode(password)` to avoid storing raw plaintext payloads.
  3. Verifies input references exist inside internal databases safely generating `Empresa` objects via proxy retrieval mappings (only passing primary keys without executing heavy database lookups mapping out).
  4. Forces mapping the root object reference context onto native nested list contexts matching their bidirectional parameters natively inside internal mapping blocks securely via iterations (`role.setUser(user)`). 

---

## 3. Custom exceptions
**Not found.** 
The BLL currently uses standard Java exceptions, exclusively `IllegalArgumentException`, to denote validation and business rule violations without specialized exception hierarchies.

---

## 4. Global exception handler
**Not found.** 
There are currently no classes annotated with `@ControllerAdvice` or `@RestControllerAdvice` configured to capture and uniformly map these `IllegalArgumentException` thrown messages uniformly into standardized HTTP payload schemas.

---

## 5. DTOs
**Not found.** 
There are no dedicated Data Transfer Objects defined anywhere in the `core` context space representing this system design. The service layer directly returns mapped `@Entity` instances explicitly out to its calling blocks, leaving representation or projection logic tightly matched towards raw domain structure definitions.

---

## 6. Package structure
- **Services**: `com.empresa.iogurtes.gestaoiogurtes.core.service`
- **Exceptions**: Not found 
- **DTOs**: Not found 
- **Exception handlers**: Not found 

---

## 7. What is NOT yet implemented

- **Missing Service Classes**: The data layer maps several core business concept groups without parallel implementation orchestration defined inside the Service boundaries. Specifically, there are missing service management pipelines bridging actions towards:
  - `ConsumoProducao` operations (no `ConsumoProducaoService`).
  - `Encomenda` orchestration (no `EncomendaService`).
  - `EncomendaOrdem` orchestration (no `EncomendaOrdemService`).
  - `EncomendaPallet` orchestration (no `EncomendaPalletService`).
  - `MovimentoStockPF` tracking (no `MovimentoStockPFService`).
  - `OrdemProducao` (no `OrdemProducaoService`).
  - `PalletTipo` (no `PalletTipoService`).

- **Missing Business Rules Checkpoints**:
  - The system defines entities to control `OrdemProducao` and final supply chain stocks `MovimentoStockPF`, yet it misses the implementation of a full control loop validating correct Raw Ingredients allocations towards batch sizes before scheduling Production instances into database transaction states explicitly based inside the Service rules parameters locally missing.
  - Transactions rely almost universally on application default commit patterns rather than utilizing `@Transactional` isolation markers for critical compound routines. Out of all methods that perform multi-step modifications (e.g. `MateriaPrimaService` or `FornecedorService`), only `MovimentoStockMPService.registarMovimento` uses `@Transactional`. This opens up scenarios for race conditions or partial commits if the JVM fails midway through executing loops.
  - Exception reporting leaks system internals directly into `IllegalArgumentException` blocks without translation rules standardizing outputs systematically. Or natively catching these globally missing proper `@ControllerAdvice` hooks bridging domain concepts into HTTP codes correctly on execution limits reached properly.
