package ru.practicum.main.stat.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.stat.service.model.EndpointHit;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Integer> {
}
