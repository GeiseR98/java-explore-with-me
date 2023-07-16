package ru.practicum.main.stat.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EndpointHitController {

    @GetMapping(path = "/test")
    public String test() {
        return "Привет из стата";
    }
}
