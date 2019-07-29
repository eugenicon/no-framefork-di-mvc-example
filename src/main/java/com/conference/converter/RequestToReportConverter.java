package com.conference.converter;

import com.conference.Component;
import com.conference.dao.LocationDao;
import com.conference.data.entity.Report;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestToReportConverter implements Converter<HttpServletRequest, Report> {
    private final ConversionService conversionService;
    private final LocationDao locationDao;

    public RequestToReportConverter(ConversionService conversionService, LocationDao locationDao) {
        this.conversionService = conversionService;
        this.locationDao = locationDao;
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
        report.setReporter(req.getParameter("reporter"));
        report.setDescription(req.getParameter("description"));
        return report;
    }
}
