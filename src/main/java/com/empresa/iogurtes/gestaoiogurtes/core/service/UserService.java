package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.*;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import com.empresa.iogurtes.gestaoiogurtes.core.ports.PasswordHasher;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.UserValidator;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final EmpresaRepository empresaRepository;
    private final UserValidator userValidator;
    private final PasswordHasher passwordHasher;

    public UserService(UserRepository userRepository,
                       EmpresaRepository empresaRepository,
                       UserValidator userValidator,
                       UserRoleRepository userRoleRepository,
                       PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.empresaRepository = empresaRepository;
        this.userValidator = userValidator;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public UserResponse createFuncionarioMP(CreateFuncionarioRequest request) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.FUNCIONARIO_MP)
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.INVALID_ROLE));

        ValidatedFuncionario info = userValidator.validateCreateFuncionarioMP(request, role);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(), passwordHash, info.turno(), info.dataAdmissao());
            user.setRole(info.role());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_CREATE_FAILED);
        }
    }

    @Transactional
    public UserResponse createFuncionarioOP(CreateFuncionarioRequest request) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.FUNCIONARIO_OP)
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.INVALID_ROLE));

        ValidatedFuncionario info = userValidator.validateCreateFuncionarioOP(request, role);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(), passwordHash, info.turno(), info.dataAdmissao());
            user.setRole(info.role());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_CREATE_FAILED);
        }
    }

    @Transactional
    public UserResponse createCliente(CreateClienteRequest request) {
            UserRole role = userRoleRepository.findByRole(UserRoleType.CLIENTE)
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.INVALID_ROLE));

            // esta parte é ncessaria porque se o id vier nulo antes do repository da erro critico
            if (request.empresaId() == null)
            throw new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND);

            Empresa empresa = empresaRepository.findById(request.empresaId())
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND));

            ValidatedCliente info = userValidator.validateCreateCliente(request, role);

            try {
                String passwordHash = passwordHasher.hash(info.password());
                User user = new User(info.empresa(), info.nome(), info.email(),
                        passwordHash, null, null);
                user.setRole(info.role());
                return toResponse(userRepository.save(user));
            } catch (Exception e) {
                throw new ClienteException(ClienteErrorCode.CLIENTE_CREATE_FAILED);
            }
    }

    @Transactional
    public UserResponse createAdmin(CreateAdminRequest request) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.ADMIN)
                .orElseThrow(() -> new AdminException(AdminErrorCode.INVALID_ROLE));
        ValidatedAdmin info = userValidator.validateCreateAdmin(request, role);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(),
                    passwordHash, null, null);
            user.setRole(info.role());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new AdminException(AdminErrorCode.ADMIN_CREATE_FAILED);
        }
    }

    @Transactional
    public UserResponse createGestor(CreateGestorRequest request) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.GESTOR)
                .orElseThrow(() -> new GestorException(GestorErrorCode.INVALID_ROLE));
        ValidatedGestor info = userValidator.validateCreateGestor(request, role);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(),
                    passwordHash, null, info.dataAdmissao());
            user.setRole(info.role());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new GestorException(GestorErrorCode.GESTOR_CREATE_FAILED);
        }
    }

    public UserResponse findById(UUID id) {
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_NOT_FOUND));
    }

    public List<UserResponse> findAllFuncionarios() {
        return userRepository.findAllByRole_RoleInAndIsActiveTrue(List.of(UserRoleType.FUNCIONARIO_MP, UserRoleType.FUNCIONARIO_OP))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllFuncionarios_MP() {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.FUNCIONARIO_MP)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllFuncionarios_OP() {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.FUNCIONARIO_OP)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllClientes() {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.CLIENTE)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllAdmins() {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.ADMIN)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllGestores() {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.GESTOR)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllActive() {
        return userRepository.findAllByIsActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<UserResponse> findAllInactive() {
        return userRepository.findAllByIsActiveFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateFuncionario(UUID id, UpdateFuncionarioRequest request) {
        User user = userRepository.findByIdAndRole_RoleIn(id,
                        List.of(UserRoleType.FUNCIONARIO_MP, UserRoleType.FUNCIONARIO_OP))
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_NOT_FOUND));

        ValidatedUpdateFuncionario info = userValidator.validateUpdateFuncionario(request);

        try {
            user.setNome(info.nome());
            user.setDataAdmissao(info.dataAdmissao());
            user.setTurno(info.turno());

            if (info.novaRole() != null) {
                UserRole role = userRoleRepository.findByRole(info.novaRole())
                        .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.INVALID_ROLE));
                if (info.novaRole() == UserRoleType.GESTOR)
                    user.setTurno(null);
                user.setRole(role);
            }

            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_UPDATE_FAILED);
        }
    }

    @Transactional
    public UserResponse updateCliente(UUID id, UpdateClienteRequest request) {
        User user = userRepository.findByIdAndRole_Role(id, UserRoleType.CLIENTE)
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.CLIENTE_NOT_FOUND));

        if (request.empresaId() == null)
            throw new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND);

        ValidatedUpdateCliente info = userValidator.validateUpdateCliente(request);

        Empresa empresa = empresaRepository.findById(info.empresaId())
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND));

        try {
            user.setNome(info.nome());
            user.setEmpresa(empresa);
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new ClienteException(ClienteErrorCode.CLIENTE_UPDATE_FAILED);
        }
    }

    @Transactional
    public UserResponse updateAdmin(UUID id, UpdateAdminRequest request) {
        User user = userRepository.findByIdAndRole_Role(id, UserRoleType.ADMIN)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ADMIN_NOT_FOUND));

        ValidatedUpdateAdmin info = userValidator.validateUpdateAdmin(request);

        try {
            user.setNome(info.nome());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new AdminException(AdminErrorCode.ADMIN_UPDATE_FAILED);
        }
    }

    @Transactional
    public UserResponse updateGestor(UUID id, UpdateGestorRequest request) {
        User user = userRepository.findByIdAndRole_Role(id, UserRoleType.GESTOR)
                .orElseThrow(() -> new GestorException(GestorErrorCode.GESTOR_NOT_FOUND));

        ValidatedUpdateGestor info = userValidator.validateUpdateGestor(request);

        try {
            user.setNome(info.nome());
            user.setDataAdmissao(info.dataAdmissao());

            if (info.novaRole() != null && info.novaRole() != UserRoleType.GESTOR) {
                UserRole role = userRoleRepository.findByRole(info.novaRole())
                        .orElseThrow(() -> new GestorException(GestorErrorCode.INVALID_ROLE));
                user.setTurno(info.turno());
                user.setRole(role);
            }

            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new GestorException(GestorErrorCode.GESTOR_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        try {
            user.softDelete();
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserException(UserErrorCode.USER_DELETE_FAILED);
        }
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getTurno(),
                user.getEmpresa() != null ? user.getEmpresa().getId() : null,
                user.getDataAdmissao(),
                user.getRole().getRole(),
                user.getCreatedAt()
        );
    }
}