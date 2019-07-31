package com.conference.converter;

import com.conference.Component;
import com.conference.data.entity.Role;
import com.conference.data.entity.User;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestToUserConverter implements Converter<HttpServletRequest, User> {
    private final ConversionService conversionService;

    public RequestToUserConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public User convert(HttpServletRequest req) {
        User data = new User();
        data.setId(conversionService.convert(req.getParameter("id"), Integer.class));
        data.setName(req.getParameter("name"));
        data.setEmail(req.getParameter("email"));
        data.setPassword(req.getParameter("password"));
        data.setRole(conversionService.convert(req.getParameter("role"), Role.class));
        return data;
    }
}
