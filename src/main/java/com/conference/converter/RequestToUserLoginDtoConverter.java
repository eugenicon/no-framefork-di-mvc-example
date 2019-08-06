package com.conference.converter;


import com.conference.Component;
import com.conference.data.dto.UserLoginDto;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestToUserLoginDtoConverter implements Converter<HttpServletRequest, UserLoginDto> {

    @Override
    public UserLoginDto convert(HttpServletRequest request) {
        UserLoginDto user = new UserLoginDto();
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        return user;
    }
}
