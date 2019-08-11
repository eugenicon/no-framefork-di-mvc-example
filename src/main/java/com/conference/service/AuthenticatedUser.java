package com.conference.service;

import com.conference.data.entity.Role;

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
}
