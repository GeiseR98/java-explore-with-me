package ru.practicum.main.user.service;

import ru.practicum.main.user.dto.UserDto;

public interface UserService {

    UserDto save(UserDto userDto);

    void removeById(Integer userId);
}
