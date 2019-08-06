package com.conference.validation.validator;

import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;

@Component
public class PasswordConfirmationValidator implements Validator<UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto data) {
        return data.getPassword().equals(data.getPasswordConfirmation());
    }
}
