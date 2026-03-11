package com.iogurtes.model;

import com.iogurtes.model.enums.UserRoleType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}))
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleType role;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    public UserRole() {}

    public UserRole(User user, UserRoleType role) {
        this.user = user;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public User getUser() { return user; }
    public UserRoleType getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }


    public void setId(Integer id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setRole(UserRoleType role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}