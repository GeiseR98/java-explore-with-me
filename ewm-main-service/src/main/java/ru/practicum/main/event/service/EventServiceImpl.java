package ru.practicum.main.event.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.EventShortDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.mapper.EventMapper;
import ru.practicum.main.event.model.Event;
import ru.practicum.main.event.model.EventStatus;
import ru.practicum.main.event.model.QEvent;
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.ConflictException;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.location.service.LocationService;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateRequest;
import ru.practicum.main.requests.dto.EventRequestStatusUpdateResult;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.mapper.ParticipationRequestMapper;
import ru.practicum.main.requests.model.ParticipationRequest;
import ru.practicum.main.requests.model.ParticipationRequestStatus;
import ru.practicum.main.requests.repository.ParticipationRequestRepository;
import ru.practicum.main.utility.Filter;
import ru.practicum.main.utility.Page;
import ru.practicum.main.utility.QPredicates;
import ru.practicum.main.utility.Utility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final Utility utility;
    private final LocationService locationService;
    private final EventMapper eventMapper;
    private final ParticipationRequestRepository requestRepository;
    private final ParticipationRequestMapper requestMapper;

    @Override
    @Transactional
    public EventDto createEvents(Integer userId, NewEventDto newEventDto) {
        newEventDto.setLocation(locationService.save(newEventDto.getLocation()));

        log.debug("Попытка добавления нового события.");

        Event event = eventMapper.toEntity(
                newEventDto,
                utility.checkUser(userId),
                utility.checkCategory(newEventDto.getCategory()),
                utility.validTime(LocalDateTime.now(), newEventDto.getEventDate()));

        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto getEventByUserFullInfo(Integer userId, Integer eventId) {
        return eventMapper.toDto(eventRepository.findEventByIdAndInitiator_Id(
                utility.checkEvent(eventId).getId(),
                utility.checkUser(userId).getId())
                .orElseThrow(() -> new NotFoundException("Вероятно что данное событие создавали не вы")));
    }

    @Override
    public List<EventShortDto> getEventsByUser(Integer userId, Integer from, Integer size) {
        Pageable page = Page.paged(from, size);
        return eventRepository.findEventsByInitiator_Id(utility.checkUser(userId).getId(), page).stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventDto getEventById(Integer eventId) {
        Event event = utility.checkPublishedEvent(eventId);
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    @Override
    @Transactional
    public List<EventShortDto> getEvents(String text, List<Integer> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable,
                                         String sort, Integer from, Integer size) {
        Pageable page = Page.paged(from, size, sort);
        Filter filter = Filter.builder()
                .text(text)
                .categories(categories)
                .paid(paid)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .onlyAvailable(onlyAvailable)
                .build();
        Predicate predicate = getPredicates(filter);
        List<Event> events = eventRepository.findAll(predicate, page).stream()
                .collect(Collectors.toList());

        events.forEach(event -> event.setViews(event.getViews() + 1));

        eventRepository.saveAll(events);

        return events.stream()
                .map(eventMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ParticipationRequestDto> getRequestsByUser(Integer userId, Integer eventId) {
        log.debug("Найдены запросы на участие");
        return requestRepository.findParticipationRequestsByEvent_IdAndEvent_Initiator_Id(eventId, userId).stream()
                .map(requestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult changeStatusRequestsByUser(Integer userId, Integer eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        List<ParticipationRequest> requestList = requestRepository.findParticipationRequestsByEvent_IdAndEvent_Initiator_Id(eventId, userId);

        if (requestList.isEmpty()) {
            throw new NotFoundException("Событие не найдено или недоступно");
        }

        Event event = utility.checkEvent(eventId);

        for (ParticipationRequest request : requestList) {
            boolean isUnlimitedParticipantsOrModerationDisabled = (request.getEvent().getParticipantLimit() == 0) ||
                    !request.getEvent().getRequestModeration();
            boolean isParticipantLimitReached = request.getEvent().getConfirmedRequests() >= request.getEvent().getParticipantLimit();
            boolean isRequestPending = request.getState().equals(ParticipationRequestStatus.PENDING);

            if (isUnlimitedParticipantsOrModerationDisabled) {
                request.setState(ParticipationRequestStatus.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            } else if (isRequestPending) {
                if (isParticipantLimitReached) {
                    request.setState(ParticipationRequestStatus.REJECTED);
                } else {
                    request.setState(eventRequestStatusUpdateRequest.getStatus());
                    if (request.getState().equals(ParticipationRequestStatus.CONFIRMED)) {
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    }
                }
            } else {
                throw new ConflictException("Нарушение целостности данных.");
            }
        }

        List<ParticipationRequestDto> requestsConfirmed = new ArrayList<>();
        List<ParticipationRequestDto> requestsRejected = new ArrayList<>();

        for (ParticipationRequest request : requestList) {
            if (request.getState().equals(ParticipationRequestStatus.CONFIRMED)) {
                requestsConfirmed.add(requestMapper.toDto(request));
            }
            if (request.getState().equals(ParticipationRequestStatus.REJECTED)) {
                requestsRejected.add(requestMapper.toDto(request));
            }
        }
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(requestsConfirmed)
                .rejectedRequests(requestsRejected)
                .build();
    }


    private Predicate getPredicates(Filter filter) {
        LocalDateTime timeNow = checkDate(filter);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(QPredicates.builder()
                .add(filter.getText(), QEvent.event.annotation::likeIgnoreCase)
                .add(filter.getText(), QEvent.event.description::likeIgnoreCase)
                .buildOr());
        predicates.add(QPredicates.builder()
                .add(filter.getCategories(), QEvent.event.category.id::in)
                .add(filter.getPaid(), QEvent.event.paid::eq)
                .add(timeNow, QEvent.event.eventDate::goe)
                .add(filter.getRangeEnd(), QEvent.event.eventDate::loe)
                .add(EventStatus.PUBLISHED, QEvent.event.state::eq)
                .buildAnd());
        return ExpressionUtils.allOf(predicates);
    }

    private LocalDateTime checkDate(Filter filter) {
        if (filter.getRangeStart() == null ||
                filter.getRangeEnd() == null) {
            return LocalDateTime.now();
        } else {
            return filter.getRangeStart();
        }
    }

}
