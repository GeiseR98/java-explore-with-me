package ru.practicum.main.location.dto;

import javax.validation.constraints.NotNull;

public class LocationDto {
    @NotNull
    private float lat;    // широта
    @NotNull
    private float lon;    // долгота
}
