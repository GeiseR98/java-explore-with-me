package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.NewEventDto;

public interface EventService {
    EventDto createEvents(Integer userId, NewEventDto eventDto);

    EventDto getEventByUserFullInfo(Integer userId, Integer eventId);
}
