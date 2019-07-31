package com.conference.converter;

import com.conference.Component;
import com.conference.dao.LocationDao;
import com.conference.dao.UserDao;
import com.conference.data.entity.Report;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class RequestToReportConverter implements Converter<HttpServletRequest, Report> {
    private final ConversionService conversionService;
    private final LocationDao locationDao;
    private final UserDao userDao;

    public RequestToReportConverter(ConversionService conversionService, LocationDao locationDao, UserDao userDao) {
        this.conversionService = conversionService;
        this.locationDao = locationDao;
        this.userDao = userDao;
    }

    @Override
    public Report convert(HttpServletRequest req) {
        Report report = new Report();
        report.setId(conversionService.convert(req.getParameter("id"), Integer.class));
        report.setTheme(req.getParameter("theme"));
        Integer locationId = conversionService.convert(req.getParameter("place"), Integer.class);
        if (locationId != null) {
            report.setPlace(locationDao.findById(locationId));
        }
        Integer userId = conversionService.convert(req.getParameter("reporter"), Integer.class);
        if (userId != null) {
            report.setReporter(userDao.findById(userId));
        }
        report.setStartTime(conversionService.convert(req.getParameter("startTime"), Date.class));
        report.setEndTime(conversionService.convert(req.getParameter("endTime"), Date.class));
        report.setDescription(req.getParameter("description"));
        return report;
    }
}
