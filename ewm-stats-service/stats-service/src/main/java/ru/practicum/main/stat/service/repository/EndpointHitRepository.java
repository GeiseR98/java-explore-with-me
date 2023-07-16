package ru.practicum.main.stat.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.stat.service.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT h " +
            "FROM EndpointHit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2")
    List<EndpointHit> findByDateTime(LocalDateTime start, LocalDateTime end);

}
