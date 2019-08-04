package com.conference.data.dto;

import com.conference.validation.NotEmpty;

public class UserLoginDto {
    @NotEmpty("Specify user name")
    private String userName;

    @NotEmpty("Specify password")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
