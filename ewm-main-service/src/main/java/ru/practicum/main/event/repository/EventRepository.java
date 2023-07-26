package ru.practicum.main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.event.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}