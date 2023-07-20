package ru.practicum.main.location.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "location")
@AllArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float lat;
    private float lon;
}