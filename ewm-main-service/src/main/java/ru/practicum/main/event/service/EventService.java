package ru.practicum.main.event.service;

import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.dto.UpdateEventUserRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto createEvents(Integer userId, NewEventDto eventDto);

    EventDto getEventByUserFullInfo(Integer userId, Integer eventId);

    List<EventShortDto> getEventsByUser(Integer userId, Integer from, Integer size);

    EventDto getEventById(Integer eventId);

    List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid,
                                  LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable, String sort, Integer from, Integer size);

    List<ParticipationRequestDto> getRequestsByUser(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult changeStatusRequestsByUser(Integer userId, Integer eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    EventDto changeEventsByUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest);
}
