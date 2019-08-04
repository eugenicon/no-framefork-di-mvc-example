package com.conference.service;

import com.conference.data.entity.Role;

public class AuthenticatedUser {
    private String name;
    private Role role;

    public AuthenticatedUser(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
