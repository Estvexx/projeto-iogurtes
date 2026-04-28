//v1.0
package com.empresa.iogurtes.gestaoiogurtes.desktop.bll.model;

import com.empresa.iogurtes.gestaoiogurtes.desktop.bll.model.enums.UserRoleType;

/**
 * Papel (role) atribuído a um utilizador.
 */
public class UserRole extends BaseEntity {

    private User user;
    private UserRoleType role;

    public UserRole() {}

    // ── Getters / setters ──────────────────────────────────────────

    public User getUser()              { return user; }
    public void setUser(User user)     { this.user = user; }

    public UserRoleType getRole()      { return role; }
    public void setRole(UserRoleType r){ this.role = r; }
}
