package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.service.EventService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPublicController {
    private final EventService eventService;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{eventId}")
    public EventDto getEventById(@PathVariable(name = "eventId") @Positive Integer eventId) {
        log.debug("Получение подробной информации об опубликованном событии по его идентификаторе");
        return eventService.getEventById(eventId);
    }
}
