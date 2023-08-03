package ru.practicum.main.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.comment.dto.CommentDto;
import ru.practicum.main.comment.dto.NewCommentDto;
import ru.practicum.main.comment.mapper.CommentMapper;
import ru.practicum.main.comment.model.Comment;
import ru.practicum.main.comment.repository.CommentRepository;
import ru.practicum.main.utility.Page;
import ru.practicum.main.utility.Utility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final Utility utility;
    private final CommentMapper mapper;

    @Override
    public List<CommentDto> getComments(Integer eventId, Integer from, Integer size) {
        Pageable page = Page.paged(from, size);

        return commentRepository.findCommentsByEvent_Id(utility.checkPublishedEvent(eventId).getId(), page).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto createComment(Integer userId, Integer eventId, NewCommentDto newCommentDto) {
        Comment comment = Comment.builder()
                .text(newCommentDto.getText())
                .event(utility.checkPublishedEvent(eventId))
                .author(utility.checkUser(userId))
                .created(LocalDateTime.now())
                .build();
        return mapper.toDto(commentRepository.save(comment));
    }
}
