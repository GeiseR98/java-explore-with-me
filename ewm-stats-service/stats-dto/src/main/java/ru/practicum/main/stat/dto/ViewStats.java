package ru.practicum.main.stat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewStats {
    private String app;
    private String uri;
    private Long hits;
}
