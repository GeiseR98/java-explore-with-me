package ru.practicum.main.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.stat.dto.EndpointHitDto;
import ru.practicum.main.stat.service.service.EndpointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointHitController {

    private final EndpointHitService service;

    @GetMapping(path = "/test")
    public String test() {
        return "Привет из стата";
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/hit")
    public EndpointHitDto hit (@Valid @RequestBody EndpointHitDto endpointHitDto) {
        log.debug("Создание запроса {}" , endpointHitDto);
        endpointHitDto.setTimestamp(LocalDateTime.now());
        return service.addHit(endpointHitDto);
    }
}
