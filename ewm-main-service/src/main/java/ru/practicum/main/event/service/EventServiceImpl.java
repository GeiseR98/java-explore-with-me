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
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.location.service.LocationService;
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
    private final EventMapper mapper;

    @Override
    @Transactional
    public EventDto createEvents(Integer userId, NewEventDto newEventDto) {
        newEventDto.setLocation(locationService.save(newEventDto.getLocation()));

        log.debug("Попытка добавления нового события.");

        Event event = mapper.toEntity(
                newEventDto,
                utility.checkUser(userId),
                utility.checkCategory(newEventDto.getCategory()),
                utility.validTime(LocalDateTime.now(), newEventDto.getEventDate()));

        event = eventRepository.save(event);
        return mapper.toDto(event);
    }

    @Override
    @Transactional
    public EventDto getEventByUserFullInfo(Integer userId, Integer eventId) {
        return mapper.toDto(eventRepository.findEventByIdAndInitiator_Id(
                utility.checkEvent(eventId).getId(),
                utility.checkUser(userId).getId())
                .orElseThrow(() -> new NotFoundException("Вероятно что данное событие создавали не вы")));
    }

    @Override
    public List<EventShortDto> getEventsByUser(Integer userId, Integer from, Integer size) {
        Pageable page = Page.paged(from, size);
        return eventRepository.findEventsByInitiator_Id(utility.checkUser(userId).getId(), page).stream()
                .map(mapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventDto getEventById(Integer eventId) {
        Event event = utility.checkPublishedEvent(eventId);
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return mapper.toDto(event);
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
                .map(mapper::toShortDto)
                .collect(Collectors.toList());
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
