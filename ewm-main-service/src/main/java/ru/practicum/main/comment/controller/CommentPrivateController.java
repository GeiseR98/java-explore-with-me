package ru.practicum.main.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.service.CommentService;
import ru.practicum.main.utility.Utility;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class CommentPrivateController {
    private final CommentService commentService;
    private final Utility utility;
    private final CommentMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDto create(@PathVariable Integer userId,
                             @RequestParam Integer eventId,
                             @RequestBody @Valid NewCommentDto newCommentDto) {
        log.debug("Создание комментария от текущего пользователя");
        return commentService.createComment(userId, eventId, newCommentDto);
    }
}
