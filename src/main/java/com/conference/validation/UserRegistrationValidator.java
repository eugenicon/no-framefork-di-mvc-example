package com.conference.validation;

import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;

@Component
public class UserRegistrationValidator implements Validator<ValidData, UserRegistrationDto> {

    @Override
    public boolean isValid(UserRegistrationDto data, ValidData annotation) {
        return data.getPassword().equals(data.getPasswordConfirmation());
    }

    @Override
    public String getErrorMessage(ValidData annotation) {
        return annotation.message();
    }

    @Override
    public Class<ValidData> getSupportedType() {
        return ValidData.class;
    }
}
