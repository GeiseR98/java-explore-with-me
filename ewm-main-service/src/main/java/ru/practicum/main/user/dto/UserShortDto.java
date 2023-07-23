package ru.practicum.main.user.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserShortDto {
    @NotNull
    private Integer id;
    @NotBlank
    @Length(max = 256)
    private String name;
}
