package com.rocketseat.planner.trip;

import com.rocketseat.planner.participants.ParticipantsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    @Autowired
    private ParticipantsService service;

    public TripCreateResponse createTrip(TripRequestPayload payload) {
        Trip trip = new Trip();
        trip = tripFactory(payload, trip);
        repository.save(trip);
        service.registerParticipantsToEvent(payload.emailsToInvite, trip);
        service.triggerConfirmationEmailToParticipants(trip);
        return new TripCreateResponse(trip.getId());
    }

    public void updateTrip(TripRequestPayload payload, Trip trip) {
        trip = tripFactory(payload, trip);
        repository.save(trip);
        service.registerParticipantsToEvent(payload.emailsToInvite, trip);
        service.triggerConfirmationEmailToParticipants(trip);
    }

    public void confirmTravel(Trip trip) {
        trip.setIsConfirmed(true);
        repository.save(trip);
        service.triggerConfirmationEmailToParticipants(trip);
    }

    protected Trip tripFactory(@NotNull TripRequestPayload payload, Trip trip) {
        trip.setDestination(payload.destination);
        trip.setEndsAt(LocalDateTime.parse(payload.endsAt, DateTimeFormatter.ISO_DATE_TIME));
        trip.setStartsAt(LocalDateTime.parse(payload.startsAt, DateTimeFormatter.ISO_DATE_TIME));
        trip.setOwnerName(payload.ownerName);
        trip.setOwnerEmail(payload.ownerEmail);
        trip.setIsConfirmed(false);
        return trip;
    }
}
