package ru.practicum.main.user.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.user.dto.UserDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AdminUserController {


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody @Valid UserDto userDto) {
        log.debug("Попытка добавить нового пользователя");
        return null;
    }
}
