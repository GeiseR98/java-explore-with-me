package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventDto;

public interface EventService {
    EventDto createEvents(Integer userId, EventDto eventDto);
}
