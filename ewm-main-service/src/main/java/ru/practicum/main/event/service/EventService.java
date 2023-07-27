package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;

import java.util.List;

public interface EventService {
    EventDto createEvents(Integer userId, NewEventDto eventDto);

    EventDto getEventByUserFullInfo(Integer userId, Integer eventId);

    List<EventShortDto> getEventsByUser(Integer userId, Integer from, Integer size);
}
