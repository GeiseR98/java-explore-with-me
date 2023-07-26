package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.dto.NewEventDto;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventPrivateController {
    private final EventService eventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventDto createEvents(@PathVariable(name = "userId") @Positive Integer userId,
                                 @RequestBody @Valid NewEventDto eventDto) {
        log.debug("Попытка добавления нового события.");
        return eventService.createEvents(userId, eventDto);
    }

}
