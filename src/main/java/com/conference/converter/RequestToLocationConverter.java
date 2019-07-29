package com.conference.converter;

import com.conference.Component;
import com.conference.data.entity.Location;

import javax.servlet.http.HttpServletRequest;

@Component
public class RequestToLocationConverter implements Converter<HttpServletRequest, Location> {
    private final ConversionService conversionService;

    public RequestToLocationConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public Location convert(HttpServletRequest req) {
        Location data = new Location();
        data.setId(conversionService.convert(req.getParameter("id"), Integer.class));
        data.setName(req.getParameter("name"));
        data.setPlaces(conversionService.convert(req.getParameter("places"), Integer.class));
        return data;
    }
}
