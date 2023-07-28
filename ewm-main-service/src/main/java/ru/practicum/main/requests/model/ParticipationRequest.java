package ru.practicum.main.requests.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "requests")
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Integer event;
    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private Integer requestor;
    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus state;
}
