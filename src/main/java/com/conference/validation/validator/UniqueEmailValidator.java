package com.conference.validation.validator;

import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;
import com.conference.service.UserService;

@Component
public class UniqueEmailValidator implements Validator<UserRegistrationDto> {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(UserRegistrationDto data) {
        return !userService.findByEmail(data.getUserName()).isPresent();
    }
}
