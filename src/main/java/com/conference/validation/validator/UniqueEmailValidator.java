package com.conference.validation.validator;

import com.conference.Component;
import com.conference.service.UserService;

@Component
public class UniqueEmailValidator implements Validator<String> {
    private final UserService userService;

    public UniqueEmailValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String email) {
        return !userService.findByEmail(email).isPresent();
    }
}
