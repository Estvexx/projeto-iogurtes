package com.empresa.iogurtes.gestaoiogurtes.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "deletedat")
    private LocalDateTime deletedAt;

    @Column(name = "createdat", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedat", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void softDelete() {
        isActive = false;
        deletedAt = LocalDateTime.now();
    }

    public void restore() {
        isActive = true;
        deletedAt = null;
    }

    public UUID getId() { return id; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setId(UUID id) { this.id = id; }
    public void setActive(boolean active) { isActive = active; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

