package ru.practicum.main.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class Utility {
    private final UserRepository userRepository;

    public User checkUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с идентификатором =%d не найден", userId)));
    }
}
