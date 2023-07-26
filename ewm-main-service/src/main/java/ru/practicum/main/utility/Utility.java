package ru.practicum.main.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.main.category.model.Category;
import ru.practicum.main.category.repository.CategoryRepository;
import ru.practicum.main.exception.NotFoundException;
import ru.practicum.main.exception.ValidTimeException;
import ru.practicum.main.user.model.User;
import ru.practicum.main.user.repository.UserRepository;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class Utility {
    private final UserRepository userRepository;
    private final CategoryRepository catRepository;

    public User checkUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с идентификатором =%d не найден", userId)));
    }

    public Category checkCategory(Integer catId) {
        return catRepository.findById(catId).orElseThrow(() ->
                new NotFoundException(String.format("Категория с идентификатором =%d не найдена", catId)));
    }

    public void validTime(String time) {
        LocalDateTime startDate = LocalDateTime.parse(time, Constants.formatter);
        if (Duration.between(LocalDateTime.now(), startDate).toMinutes() < Duration.ofHours(2).toMinutes()) {
            throw new ValidTimeException("Обратите внимание: дата и время, на которые намечено событие," +
                    " не может быть раньше, чем через два часа от текущего момента");
        }
    }
}
