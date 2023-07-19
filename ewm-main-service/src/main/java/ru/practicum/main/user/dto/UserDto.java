package ru.practicum.main.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int Id;
    @NotBlank
    @Length(max = 256)
    private String name;
    @Email
    @NotBlank
    @Length(max = 320)
    private String email;
}
