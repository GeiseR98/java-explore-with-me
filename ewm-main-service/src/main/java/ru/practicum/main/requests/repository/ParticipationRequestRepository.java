package ru.practicum.main.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.requests.model.ParticipationRequest;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Integer> {

    List<ParticipationRequest> findParticipationRequestsByEventsWithRequests_IdAndEventsWithRequests_Initiator_Id(int eventId, int userId);

}
