package com.empresa.iogurtes.gestaoiogurtes.core.repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdAndIsActiveIsTrue(UUID id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByEmailAndIsActiveTrue(String email);

    boolean existsByEmailAndIsActiveTrue(String email);

    Optional<User> findByIdAndRole_RoleInAndIsActiveIsTrue(UUID id, List<UserRoleType> roles);
    Page<User> findAllByRole_RoleAndIsActiveTrue(UserRoleType role, Pageable pageable);
    Page<User> findAllByRole_RoleInAndIsActiveTrue(Collection<UserRoleType> roles, Pageable pageable);
    Optional<User> findByIdAndRole_RoleAndIsActiveIsTrue(UUID id, UserRoleType role);
    Page<User> findAllByIsActiveTrue(Pageable pageable);
    Page<User> findAllByIsActiveFalse(Pageable pageable);

    //Para ser consumido pelo modulo empresa
    boolean existsByEmpresa_IdAndIsActiveTrue(UUID empresaId);
}