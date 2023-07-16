package ru.practicum.main.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointHitController {

//    private final EndpointHitsService service;
//
//    @PostMapping(path = "/hit")
//    public void hit(@Valid @RequestBody EndpointHitDto endpointHitDto) {
//        log.debug("Создание запроса {}", endpointHitDto);
//        service.addHit(endpointHitDto);
//    }
//
//    @GetMapping(path = "/stats")
//    public List<ViewStats> stats(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
//                                 @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
//                                 @RequestParam(name = "uris", required = false) List<String> uris,
//                                 @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
//        log.debug("Попытка получить статистику");
//        return service.stats(start, end, uris, unique);
//    }

    @GetMapping(path = "/test")
    public String test() {
        return "Привет из стата";
    }
}
