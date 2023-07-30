package ru.practicum.main.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventStatus;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidTimeException;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.repository.ParticipationRequestRepository;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class Utility {
    private final UserRepository userRepository;
    private final CategoryRepository catRepository;
    private final EventRepository eventRepository;
    private final ParticipationRequestRepository requestRepository;

    public User checkUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с идентификатором =%d не найден", userId)));
    }

    public Category checkCategory(Integer catId) {
        return catRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Категория с идентификатором =%d не найдена", catId)));
    }

    public Event checkEvent(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с идентификатором =%d не найдено", eventId)));
    }

    public Event checkPublishedEvent(Integer eventId) {
        return eventRepository.findEventByIdAndStateIs(eventId, EventStatus.PUBLISHED).orElseThrow(() ->
                new NotFoundException(String.format("Событие с идентификатором =%d не найдено или не опубликовано", eventId)));
    }

    public Event checkAbilityToParticipationCreateRequest(Integer eventId, Integer userId) {
        Event event = checkEvent(eventId);
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }
        List<ParticipationRequest> existingRequests =
        requestRepository.findParticipationRequestsByEvent_IdAndEvent_Initiator_Id(eventId, userId);
        if (existingRequests != null && !existingRequests.isEmpty()) {
            for (ParticipationRequest request : existingRequests) {
                if (Objects.equals(request.getEvent(), eventId)) {
                    throw new ConflictException("Нельзя добавить повторный запрос ");
                }
            }
        }
        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConflictException("У события достигнут лимит запросов на участие");
        }
        return event;
    }

    public ParticipationRequest checkParticipationRequest(Integer requestId, Integer userId) {
        return requestRepository.findParticipationRequestByIdAndRequestor_Id(requestId, userId).orElseThrow(() ->
                new NotFoundException("Запрос не найден или недоступен"));
    }

    public LocalDateTime validTime(LocalDateTime createdOn, LocalDateTime eventDate, Integer difference) {
        if (Duration.between(createdOn, eventDate).toMinutes() < Duration.ofHours(difference).toMinutes()) {
            throw new ValidTimeException(String.format("Обратите внимание: дата и время, на которые намечено событие," +
                    " не может быть раньше, чем через =%d час/часа от текущего момента", difference));
        }
        return createdOn;
    }
}
