package com.rocketseat.planner.participants;

import com.rocketseat.planner.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParticipantsRepository extends JpaRepository<Participants, UUID> {

    List<Participants> findByTripId(UUID trip);}
