package ru.practicum.main.requests.cotroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.requests.dto.ParticipationRequestDto;
import ru.practicum.main.requests.service.ParticipationRequestService;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ParticipationRequestPrivateController {
    private final ParticipationRequestService participationRequestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ParticipationRequestDto createParticipationRequest(@PathVariable(name = "userId") Integer userId,
                                                              @PathVariable(name = "eventId") Integer eventId) {
        log.debug("Добавление запроса от текущего пользователя на участие в событии");
        return participationRequestService.createRequestsByUserOtherEvents(userId, eventId);
    }
}
