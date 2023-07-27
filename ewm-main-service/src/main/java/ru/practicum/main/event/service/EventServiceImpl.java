package ru.practicum.main.event.service;

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
import ru.practicum.main.event.repository.EventRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.location.service.LocationService;
import ru.practicum.main.utility.Page;
import ru.practicum.main.utility.Utility;

import java.time.LocalDateTime;
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
}
