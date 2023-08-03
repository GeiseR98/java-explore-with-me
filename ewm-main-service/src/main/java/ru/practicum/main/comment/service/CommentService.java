package ru.practicum.main.comment.service;

import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(Integer eventId, Integer from, Integer size);

    CommentDto createComment(Integer userId, Integer eventId, NewCommentDto newCommentDto);
}
