package com.conference.converter;

import com.conference.Component;
import com.conference.dao.UserDao;
import com.conference.data.entity.Conference;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class RequestToConferenceConverter implements Converter<HttpServletRequest, Conference> {
    private final ConversionService conversionService;
    private final UserDao userDao;

    public RequestToConferenceConverter(ConversionService conversionService, UserDao userDao) {
        this.conversionService = conversionService;
        this.userDao = userDao;
    }

    @Override
    public Conference convert(HttpServletRequest req) {
        Conference data = new Conference();
        data.setId(conversionService.convert(req.getParameter("id"), Integer.class));
        data.setTotalTickets(conversionService.convert(req.getParameter("totalTickets"), Integer.class));
        data.setName(req.getParameter("name"));
        Integer moderatorId = conversionService.convert(req.getParameter("moderator"), Integer.class);
        if (moderatorId != null) {
            data.setModerator(userDao.findById(moderatorId));
        }
        data.setDate(conversionService.convert(req.getParameter("date"), Date.class));
        data.setDescription(req.getParameter("description"));
        return data;
    }
}
