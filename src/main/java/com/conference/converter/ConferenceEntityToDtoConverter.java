package com.conference.converter;

import com.conference.Component;
import com.conference.data.dto.ConferenceDto;
import com.conference.data.entity.Conference;

@Component
public class ConferenceEntityToDtoConverter implements Converter<Conference, ConferenceDto> {

    @Override
    public ConferenceDto convert(Conference entity) {
        ConferenceDto dto = new ConferenceDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate());
        dto.setModerator(entity.getModerator());
        dto.setDescription(entity.getDescription());
        dto.setTotalTickets(entity.getTotalTickets());
        return dto;
    }
}
