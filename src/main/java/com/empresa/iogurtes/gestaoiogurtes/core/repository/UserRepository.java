package com.empresa.iogurtes.gestaoiogurtes.core.repository;
import org.springframework.stereotype.Repository;

import com.empresa.iogurtes.gestaoiogurtes.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

}