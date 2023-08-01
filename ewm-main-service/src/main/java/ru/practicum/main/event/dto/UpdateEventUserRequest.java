package ru.practicum.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import ru.practicum.main.category.dto.CategoryDto;
import ru.practicum.main.event.model.StateActionUser;
import ru.practicum.main.location.model.Location;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {
    @Length(min = 20, max = 2000)
    private String annotation;
    private CategoryDto category;
    @Length(min = 20, max = 7000)
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionUser stateAction;
    @Length(min = 3, max = 120)
    private String title;
}