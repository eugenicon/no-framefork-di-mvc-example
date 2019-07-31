package com.conference.validation;

import com.conference.Component;
import com.conference.dao.UserDao;
import com.conference.data.entity.User;

@Component
public class UserEmailValidator implements Validator<ValidData, User> {
    private final UserDao userDao;

    public UserEmailValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean isValid(User data, ValidData annotation) {
        return !userDao.findByEmail(data.getEmail())
                .map(user -> !user.getId().equals(data.getId()))
                .orElse(false);
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
