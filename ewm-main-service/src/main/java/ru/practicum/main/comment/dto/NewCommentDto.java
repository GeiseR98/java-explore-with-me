package ru.practicum.main.comment.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class NewCommentDto {
    @NotEmpty
    @Size(min = 4, max = 7000)
    private String text;
}
