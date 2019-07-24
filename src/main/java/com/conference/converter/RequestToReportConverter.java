package com.conference.converter;

import com.conference.data.entity.Report;

import javax.servlet.http.HttpServletRequest;

public class RequestToReportConverter implements Converter<HttpServletRequest, Report> {
    private final ConversionService conversionService;

    public RequestToReportConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Report convert(HttpServletRequest req) {
        Report report = new Report();
        report.setId(conversionService.convert(req.getParameter("id"), Integer.class));
        report.setTheme(req.getParameter("theme"));
        report.setPlace(req.getParameter("place"));
        report.setReporter(req.getParameter("reporter"));
        report.setDescription(req.getParameter("description"));
        return report;
    }
}
