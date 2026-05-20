package com.empresa.iogurtes.gestaoiogurtes.core.service;

import com.empresa.iogurtes.gestaoiogurtes.core.dto.users.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.user.*;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationErrorCode;
import com.empresa.iogurtes.gestaoiogurtes.core.exception.validator.ValidationException;
import com.empresa.iogurtes.gestaoiogurtes.core.model.Empresa;
import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.UserRole;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.TurnoTipo;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import com.empresa.iogurtes.gestaoiogurtes.core.ports.PasswordHasher;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.EmpresaRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.repository.UserRoleRepository;
import com.empresa.iogurtes.gestaoiogurtes.core.validator.UserValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private UserResponse createFuncionario(CreateFuncionarioRequest info, UserRoleType roleType) {
        UserRole role = userRoleRepository.findByRole(roleType)
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.INVALID_ROLE));

        userValidator.validateCreateFuncionario(info);
        if (userRepository.existsByEmail(info.email()))
            throw new ValidationException(ValidationErrorCode.EMAIL_ALREADY_EXISTS);
        TurnoTipo turno = parseTurno(info.turno());

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(), passwordHash, turno, info.dataAdmissao());
            user.setRole(role);
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_CREATE_FAILED);
        }
    }

    @Transactional
    public UserResponse createFuncionarioMP(CreateFuncionarioRequest info) {
        return createFuncionario(info, UserRoleType.FUNCIONARIO_MP);
    }

    @Transactional
    public UserResponse createFuncionarioOP(CreateFuncionarioRequest info) {
        return createFuncionario(info, UserRoleType.FUNCIONARIO_OP);
    }

    @Transactional
    public UserResponse createCliente(CreateClienteRequest info) {
            UserRole role = userRoleRepository.findByRole(UserRoleType.CLIENTE)
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.INVALID_ROLE));

            Empresa empresa = empresaRepository.findByIdAndIsActiveIsTrue(info.empresaId())
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND));

            userValidator.validateCreateCliente(info);

            if (userRepository.existsByEmail(info.email()))
                throw new ValidationException(ValidationErrorCode.EMAIL_ALREADY_EXISTS);

            try {
                String passwordHash = passwordHasher.hash(info.password());
                User user = new User(empresa, info.nome(), info.email(),
                        passwordHash, null, null);
                user.setRole(role);
                return toResponse(userRepository.save(user));
            } catch (Exception e) {
                throw new ClienteException(ClienteErrorCode.CLIENTE_CREATE_FAILED);
            }
    }

    @Transactional
    public UserResponse createAdmin(CreateAdminRequest info) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.ADMIN)
                .orElseThrow(() -> new AdminException(AdminErrorCode.INVALID_ROLE));

        userValidator.validateCreateAdmin(info);

        if (userRepository.existsByEmail(info.email()))
            throw new ValidationException(ValidationErrorCode.EMAIL_ALREADY_EXISTS);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(),
                    passwordHash, null, null);
            user.setRole(role);
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new AdminException(AdminErrorCode.ADMIN_CREATE_FAILED);
        }
    }

    @Transactional
    public UserResponse createGestor(CreateGestorRequest info) {
        UserRole role = userRoleRepository.findByRole(UserRoleType.GESTOR)
                .orElseThrow(() -> new GestorException(GestorErrorCode.INVALID_ROLE));

        userValidator.validateCreateGestor(info);

        if (userRepository.existsByEmail(info.email()))
            throw new ValidationException(ValidationErrorCode.EMAIL_ALREADY_EXISTS);

        try {
            String passwordHash = passwordHasher.hash(info.password());
            User user = new User(null, info.nome(), info.email(),
                    passwordHash, null, info.dataAdmissao());
            user.setRole(role);
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

    public Page<UserResponse> findAllFuncionarios(Pageable pageable) {
        return userRepository.findAllByRole_RoleInAndIsActiveTrue(List.of(UserRoleType.FUNCIONARIO_MP, UserRoleType.FUNCIONARIO_OP), pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllFuncionarios_MP(Pageable pageable) {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.FUNCIONARIO_MP, pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllFuncionarios_OP(Pageable pageable) {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.FUNCIONARIO_OP, pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllClientes(Pageable pageable) {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.CLIENTE, pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllAdmins(Pageable pageable) {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.ADMIN, pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllGestores(Pageable pageable) {
        return userRepository.findAllByRole_RoleAndIsActiveTrue(UserRoleType.GESTOR, pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllActive(Pageable pageable) {
        return userRepository.findAllByIsActiveTrue(pageable)
                .map(this::toResponse);
    }

    public Page<UserResponse> findAllInactive(Pageable pageable) {
        return userRepository.findAllByIsActiveFalse(pageable)
                .map(this::toResponse);
    }

    @Transactional
    public UserResponse updateFuncionario(UUID id, UpdateFuncionarioRequest info) {
        User user = userRepository.findByIdAndRole_RoleInAndIsActiveIsTrue(id,
                        List.of(UserRoleType.FUNCIONARIO_MP, UserRoleType.FUNCIONARIO_OP))
                .orElseThrow(() -> new FuncionarioException(FuncionarioErrorCode.FUNCIONARIO_NOT_FOUND));

        userValidator.validateUpdateFuncionario(info);
        TurnoTipo turno = parseTurno(info.turno());

        try {
            user.setNome(info.nome());
            user.setDataAdmissao(info.dataAdmissao());
            user.setTurno(turno);

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
    public UserResponse updateCliente(UUID id, UpdateClienteRequest info) {
        User user = userRepository.findByIdAndRole_RoleAndIsActiveIsTrue(id, UserRoleType.CLIENTE)
                .orElseThrow(() -> new ClienteException(ClienteErrorCode.CLIENTE_NOT_FOUND));

        if (info.empresaId() != null) {
            Empresa empresa = empresaRepository.findByIdAndIsActiveIsTrue(info.empresaId())
                    .orElseThrow(() -> new ClienteException(ClienteErrorCode.EMPRESA_NOT_FOUND));
            user.setEmpresa(empresa);
        }

        try {
            user.setNome(info.nome());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new ClienteException(ClienteErrorCode.CLIENTE_UPDATE_FAILED);
        }
    }

    @Transactional
    public UserResponse updateAdmin(UUID id, UpdateAdminRequest info) {
        User user = userRepository.findByIdAndRole_RoleAndIsActiveIsTrue(id, UserRoleType.ADMIN)
                .orElseThrow(() -> new AdminException(AdminErrorCode.ADMIN_NOT_FOUND));

        try {
            user.setNome(info.nome());
            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new AdminException(AdminErrorCode.ADMIN_UPDATE_FAILED);
        }
    }

    @Transactional
    public UserResponse updateGestor(UUID id, UpdateGestorRequest info) {
        User user = userRepository.findByIdAndRole_RoleAndIsActiveIsTrue(id, UserRoleType.GESTOR)
                .orElseThrow(() -> new GestorException(GestorErrorCode.GESTOR_NOT_FOUND));

        userValidator.validateUpdateGestor(info);
        TurnoTipo turno = parseTurno(info.turno());

        try {
            user.setNome(info.nome());
            user.setDataAdmissao(info.dataAdmissao());

            if (info.novaRole() != null && info.novaRole() != UserRoleType.GESTOR) {
                UserRole role = userRoleRepository.findByRole(info.novaRole())
                        .orElseThrow(() -> new GestorException(GestorErrorCode.INVALID_ROLE));

                user.setTurno(turno);
                user.setRole(role);
            }

            return toResponse(userRepository.save(user));
        } catch (Exception e) {
            throw new GestorException(GestorErrorCode.GESTOR_UPDATE_FAILED);
        }
    }

    @Transactional
    public void softDelete(UUID id) {
        User user = userRepository.findByIdAndIsActiveIsTrue(id)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        try {
            user.softDelete();
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserException(UserErrorCode.USER_DELETE_FAILED);
        }
    }


    private TurnoTipo parseTurno(String turno) {
        if (turno == null) return null;
        try {
            return TurnoTipo.valueOf(turno.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException(ValidationErrorCode.TURNO_INVALID);
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