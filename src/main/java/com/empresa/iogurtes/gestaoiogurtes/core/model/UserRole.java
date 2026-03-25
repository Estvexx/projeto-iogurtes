package com.empresa.iogurtes.gestaoiogurtes.core.model;

import com.empresa.iogurtes.gestaoiogurtes.core.model.enums.UserRoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}))

public class UserRole extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleType role;

    public UserRole() {}

    public UserRole(UserRoleType role) {
        this.role = role;
    }

    public User getUser() { return user; }
    public UserRoleType getRole() { return role; }

    public void setUser(User user) { this.user = user; }
    public void setRole(UserRoleType role) { this.role = role; }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + getId() +
                ", role=" + role +
                '}';
    }
}