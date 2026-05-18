# Análise ao Módulo de Utilizadores

## `UserService.java`

### Problema

O service concentra demasiada lógica e mistura responsabilidades. Há três problemas principais:

1. Nas criações, a `role` vem do request e não é fixada pelo caso de uso. Isso permite incoerência entre o endpoint usado e a role guardada na base de dados.
2. O método `updateFuncionario` está preso a `FUNCIONARIO_MP`, logo um utilizador `FUNCIONARIO_OP` fica fora da actualização.
3. Os métodos de leitura/actualização não aplicam filtro por `isActive`, o que abre espaço para ler ou alterar utilizadores já desactivados.
4. O `catch (Exception)` é demasiado genérico e apaga a causa real do erro.

### Código

```java
User user = userRepository.findByIdAndRole_Role(id, UserRoleType.FUNCIONARIO_MP)
        .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_NOT_FOUND));

User user = new User(null, info.nome(), info.email(), passwordHash, info.turno(), info.dataAdmissao());
user.setRole(info.role());

} catch (Exception e) {
    throw new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_CREATE_FAILED);
}
```

### Impacto

- Um funcionário de produção (`FUNCIONARIO_OP`) pode nunca ser actualizado por este método.
- Um request mal construído pode criar um utilizador com uma role diferente da esperada pelo endpoint.
- Utilizadores soft delete continuam acessíveis em operações que não filtram `isActive`.
- A origem real das falhas fica escondida, o que dificulta muito o diagnóstico.

### Sugestão

- Fixar a role no service conforme o caso de uso e remover `role` dos DTOs de criação.
- Trocar `findByIdAndRole_Role(...)` por queries que incluam `isActive = true` quando o objectivo for trabalhar apenas com registos activos.
- Substituir `catch (Exception)` por excepções mais específicas.
- Separar melhor regras de negócio, persistência e tratamento de erros.

---

## `CreateFuncionarioRequest.java`, `CreateClienteRequest.java`, `CreateAdminRequest.java`, `CreateGestorRequest.java` e `UserResponse.java`

### Problema

Os DTOs de criação incluem o campo `role`, mas cada endpoint já representa um tipo específico de utilizador. Isso cria redundância e permite inconsistência entre a intenção do endpoint e o valor guardado.

Além disso, `UserResponse` não expõe `isActive` nem `deletedAt`, apesar de o módulo usar soft delete e ter listagens activas/inactivas.

### Código

```java
public record CreateFuncionarioRequest(
        String nome,
        String email,
        String password,
        String turno,
        String role,
        LocalDate dataAdmissao
) {}

public record CreateClienteRequest(
        String nome,
        String email,
        String password,
        String role,
        UUID empresaId
) {}

public record UserResponse(
        UUID id,
        String nome,
        String email,
        TurnoTipo turno,
        UUID empresaId,
        LocalDate dataAdmissao,
        UserRoleType role,
        LocalDateTime createdAt
) {}
```

### Impacto

- O service fica dependente de um valor que devia ser inferido pela operação, não pelo cliente.
- A API pode persistir uma role errada para o tipo de utilizador que está a ser criado.
- O consumo do módulo fica menos claro porque o estado activo/inactivo não aparece na resposta.

### Sugestão

- Remover `role` dos DTOs de criação e definir a role no service com base no método chamado.
- Se for importante expor o estado, incluir `isActive` no `UserResponse`.
- Se o histórico for relevante, considerar também `deletedAt` na resposta.

---

## `UserValidator.java`

### Problema

O validador não está a validar apenas dados; também está a resolver entidades na base de dados (`Empresa` e `UserRole`). Isso mistura validação com acesso a persistência e aumenta o acoplamento.

Também aqui existe um problema de coerência: `parseRole(...)` aceita qualquer role existente na enumeração, mas não garante que essa role seja a esperada pelo caso de uso do service.

### Código

```java
Empresa empresa = empresaRepository.findById(info.empresaId())
        .orElseThrow(() -> new ValidationException(ValidationErrorCode.EMPRESA_NOT_FOUND));

UserRole role = parseRole(info.role());

return new ValidatedCliente(info.nome(), info.email(), info.password(), role, empresa);
```

### Impacto

- O validador deixa de ser simples e previsível.
- Os testes ficam mais pesados porque passam a depender da base de dados.
- O fluxo de criação fica dependente de entidades completas quando, em muitos casos, bastavam IDs e valores normalizados.

### Sugestão

- Deixar o validador validar apenas formato, obrigatoriedade e regras simples.
- Mover a resolução de `Empresa` e `UserRole` para o service, onde a orquestração do caso de uso faz mais sentido.
- Reduzir a duplicação entre validações de criação e de actualização, se possível.

---

## `LoginService.java` e `LoginValidator.java`

### Problema

Há duplicação e inconsistência nas regras de validação de login. O `LoginService` valida as credenciais de uma forma e o `LoginValidator` valida de outra.

Além disso, o `LoginValidator` não está a ser usado pelo `LoginService`, o que o transforma em código morto.

### Código

```java
if (email == null || !email.contains("@") || password == null || password.isBlank()) {
    throw new IllegalArgumentException("Credenciais invalidas");
}

if (password == null || password.length() < 6)
    throw new IllegalArgumentException("Password inválida");
```

### Impacto

- Existem duas políticas diferentes para a mesma operação.
- O sistema pode evoluir com comportamentos inconsistentes se alguém alterar apenas uma das validações.
- O código fica mais difícil de manter e perceber.

### Sugestão

- Escolher uma única fonte de validação para login.
- Se a regra simples for suficiente, manter apenas uma classe e remover a duplicação.
- Se quiseres manter `LoginValidator`, o `LoginService` deve usá-lo directamente.

---

## `BaseEntity.java`, `UserRepository.java` e `UserRoleRepository.java`

### Problema

O soft delete está implementado na base comum, mas o módulo de utilizadores não o respeita de forma consistente.

A base tem campos e métodos para desactivar registos, mas os repositórios continuam a expor queries que podem devolver entidades inactivas se forem usadas sem cuidado.

### Código

```java
@Column(name = "is_active", nullable = false)
private boolean isActive = true;

public void softDelete() {
    isActive = false;
    deletedAt = LocalDateTime.now();
}

Optional<User> findByEmail(String email);
List<User> findAllByRole_Role(UserRoleType role);
Optional<User> findByIdAndRole_Role(UUID id, UserRoleType role);
Optional<UserRole> findByRole(UserRoleType role);
```

### Impacto

- Um utilizador desactivado pode continuar a ser encontrado por métodos que não verificam `isActive`.
- A política de soft delete passa a depender da disciplina de cada service, em vez de estar mais protegida pela camada de acesso a dados.
- O `UserRole` também herda o comportamento de soft delete, mas o código não deixa claro se isso é realmente desejado para papéis fixos.

### Sugestão

- Criar queries explícitas para registos activos sempre que o caso de uso o exigir.
- Rever se `findById`, `findByEmail` e `findByRole` deviam também filtrar por `isActive = true`.
- Centralizar a regra de acesso a entidades activas para evitar regressões.

---

## Conclusão técnica

O módulo de utilizadores está funcional, mas ainda mostra sinais de acoplamento excessivo entre validação, orquestração de negócio e persistência. O maior risco está na combinação entre:

- DTOs com `role` a mais;
- services que confiam demasiado nesses valores;
- e soft delete aplicado na entidade, mas não respeitado de forma consistente nas leituras e actualizações.

Em termos de arquitectura, está perto de uma separação aceitável, mas ainda não está limpo o suficiente para considerar o módulo realmente desacoplado.

