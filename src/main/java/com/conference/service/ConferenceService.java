package com.conference.service;

import com.conference.Component;
import com.conference.converter.ConversionService;
import com.conference.dao.ConferenceDao;
import com.conference.dao.OrderDao;
import com.conference.data.dto.ConferenceDto;
import com.conference.data.entity.Conference;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConferenceService {
    private final ConversionService conversionService;
    private final ConferenceDao conferenceDao;
    private final OrderDao orderDao;

    public ConferenceService(ConferenceDao conferenceDao, ConversionService conversionService, OrderDao orderDao) {
        this.conferenceDao = conferenceDao;
        this.conversionService = conversionService;
        this.orderDao = orderDao;
    }

    public Conference findById(Integer id) {
        return conferenceDao.findById(id);
    }

    public ConferenceDto viewById(Integer id) {
        return convertToDto(conferenceDao.findById(id));
    }

    public List<ConferenceDto> getAll() {
        return conferenceDao.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ConferenceDto convertToDto(Conference c) {
        ConferenceDto dto = conversionService.convert(c, ConferenceDto.class);
        dto.setPurchasedTickets(orderDao.findAllByConferenceId(c.getId()).size());
        return dto;
    }

    public void save(Conference entity) {
        conferenceDao.save(entity);
    }

    public void removeById(Integer id) {
        conferenceDao.removeById(id);
    }
}
