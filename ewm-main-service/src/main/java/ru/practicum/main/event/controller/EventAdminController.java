package ru.practicum.main.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.event.dto.UpdateEventAdminRequest;
import ru.practicum.main.event.dto.EventDto;
import ru.practicum.main.event.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
@Validated
public class EventAdminController {
    private final EventService eventService;

    @PatchMapping(path = "/{eventId}")
    public EventDto changeEvents(@PathVariable(name = "eventId") @Positive Integer eventId,
                                     @RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest) {
        log.debug("Редактирование данных события и его статуса (отклонение/публикация).");
        return eventService.changeEvents(eventId, updateEventAdminRequest);
    }


}
