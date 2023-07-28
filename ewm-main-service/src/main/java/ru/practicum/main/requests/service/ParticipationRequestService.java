package ru.practicum.main.requests.service;

import ru.practicum.main.requests.dto.ParticipationRequestDto;

public interface ParticipationRequestService {
    ParticipationRequestDto createRequestsByUserOtherEvents(Integer userId, Integer eventId);
}
