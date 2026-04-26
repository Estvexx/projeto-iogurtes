package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import jakarta.persistence.*;


@Entity
@Table(name = "user_roles")  // ← só isto
public class UserRole extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = true)
    private UserRoleType role;

    public UserRole() {}
    public UserRole(UserRoleType role) { this.role = role; }
    public UserRoleType getRole() { return role; }
    public void setRole(UserRoleType role) { this.role = role; }
}