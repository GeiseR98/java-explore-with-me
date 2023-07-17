package ru.practicum.main.stat.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.main.stat.dto.EndpointHitDto;
import ru.practicum.main.stat.dto.ViewStats;
import ru.practicum.main.stat.service.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {

    @Query("SELECT h " +
            "FROM EndpointHit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2")
    List<EndpointHit> findByDateTime(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.main.stat.dto.EndpointHitDto(h.app, h.uri, h.ip, h.timestamp) " +
            "FROM EndpointHit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2")
    List<EndpointHitDto> findEndpointHitDtoByDateTime(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.main.stat.dto.ViewStats(h.app, h.uri, COUNT(h.app)) " +
            "FROM EndpointHit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.app) DESC ")
    List<ViewStats> findViewStatsByDateTime(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.main.stat.dto.ViewStats(h.app, h.uri, COUNT(h.app)) " +
            "FROM EndpointHit AS h " +
            "WHERE h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "HAVING COUNT(h.ip) = 1 " +
            "ORDER BY COUNT(h.app) DESC ")
    List<ViewStats> findViewStatsByDateTimeAndUnique(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.main.stat.dto.ViewStats(h.app, h.uri, COUNT(h.app)) " +
            "FROM EndpointHit AS h " +
            "WHERE h.uri like ?3 " +
            "AND h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.app) DESC ")
    List<ViewStats> findViewStatsByDateTimeAndUri(LocalDateTime start, LocalDateTime end, String u);

    @Query("SELECT new ru.practicum.main.stat.dto.ViewStats(h.app, h.uri, COUNT(h.app)) " +
            "FROM EndpointHit AS h " +
            "WHERE h.uri like concat(?3, '%') " +
            "AND h.timestamp BETWEEN ?1 AND ?2 " +
            "GROUP BY h.app, h.uri " +
            "HAVING COUNT(h.ip) = 1 " +
            "ORDER BY COUNT(h.app) DESC ")
    List<ViewStats> findViewStatsByDateTimeAndUriAndUnique(LocalDateTime start, LocalDateTime end, String u);
}
