package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // dar get por tipo (funcionario, cliente...)
    List<User> findAllByRole_Role(UserRoleType role);
    Optional<User> findByIdAndRole_RoleIn(UUID id, List<UserRoleType> roles);
    List<User> findAllByRole_RoleAndIsActiveTrue(UserRoleType role);
    List<User> findAllByRole_RoleInAndIsActiveTrue(Collection<UserRoleType> roles);
    Optional<User> findByIdAndRole_Role(UUID id, UserRoleType role);
    List<User> findAllByIsActiveTrue();
    List<User> findAllByIsActiveFalse();

    //Para ser consumido pelo modulo empresa
    boolean existsByEmpresa_IdAndIsActiveTrue(UUID empresaId);
}