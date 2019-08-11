package com.conference.service;

import com.conference.data.entity.Role;

import java.util.Objects;

public class AuthenticatedUser {
    private Integer id;
    private String email;
    private String name;
    private Role role;

    public AuthenticatedUser(String email, Role role) {
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticatedUser that = (AuthenticatedUser) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(name, that.name)) return false;
        return role == that.role;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, role);
    }
}
