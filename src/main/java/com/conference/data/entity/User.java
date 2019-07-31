package com.conference.data.entity;

import com.conference.validation.Matches;
import com.conference.validation.NotEmpty;
import com.conference.validation.UserEmailValidator;
import com.conference.validation.ValidData;

@ValidData(field = "email", validator = UserEmailValidator.class, message = "Email is already in use")
public class User {
    private Integer id;

    @NotEmpty("You must specify the name")
    @Matches(regex = Matches.LETTERS_ONLY, message = "User should have a valid name")
    private String name;

    @NotEmpty("You must specify a role")
    private Role role;

    @NotEmpty("You must specify user email")
    @Matches(regex = Matches.EMAIL, message = "User should have a valid email")
    private String email;

    @NotEmpty("You must set user password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
