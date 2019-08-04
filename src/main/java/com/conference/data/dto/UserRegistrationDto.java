package com.conference.data.dto;


import com.conference.validation.Matches;
import com.conference.validation.UserRegistrationValidator;
import com.conference.validation.ValidData;


public class UserRegistrationDto {
    @Matches(regex = ".{4,}", message = "Username should be at least 4 symbols")
    private String userName;

    @Matches(regex = ".{5,}", message = "Password should be at least 5 symbols")
    private String password;

    @ValidData(validator = UserRegistrationValidator.class,
            message = "Confirmation should match the password")
    private String passwordConfirmation;

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

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
