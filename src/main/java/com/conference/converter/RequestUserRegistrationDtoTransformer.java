package com.conference.converter;

import com.conference.Component;
import com.conference.data.dto.UserRegistrationDto;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestUserRegistrationDtoTransformer implements Converter<HttpServletRequest, UserRegistrationDto> {

    @Override
    public UserRegistrationDto convert(HttpServletRequest request) {
        UserRegistrationDto user = new UserRegistrationDto();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setPasswordConfirmation(request.getParameter("passwordConfirmation"));
        return user;
    }
}
