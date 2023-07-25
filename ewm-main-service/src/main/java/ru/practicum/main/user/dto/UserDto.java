package ru.practicum.main.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    @NotBlank
    @Length(max = 256)
    private String name;
    @Email
    @NotBlank
    @Length(max = 320)
    private String email;
}
