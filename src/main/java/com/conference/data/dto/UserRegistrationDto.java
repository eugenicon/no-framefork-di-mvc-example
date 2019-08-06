package com.conference.data.dto;


import com.conference.validation.annotation.Matches;
import com.conference.validation.annotation.ValidData;
import com.conference.validation.validator.UniqueEmailValidator;
import com.conference.validation.validator.PasswordConfirmationValidator;


public class UserRegistrationDto {
    @Matches(regex = Matches.EMAIL, message = "Username should be valid email")
    @ValidData(validator = UniqueEmailValidator.class, message = "Email is already in use")
    private String userName;

    @Matches(regex = ".{5,}", message = "Password should be at least 5 symbols")
    private String password;

    @ValidData(validator = PasswordConfirmationValidator.class, message = "Confirmation should match the password")
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
