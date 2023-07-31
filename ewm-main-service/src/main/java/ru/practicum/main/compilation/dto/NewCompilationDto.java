package ru.practicum.main.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    @UniqueElements
    private List<Integer> eventIds;
    @Builder.Default
    private Boolean pinned = false;
    @NotBlank
    @Length(max = 120)
    private String title;
}
