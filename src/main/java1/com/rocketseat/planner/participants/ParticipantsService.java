package com.rocketseat.planner.participants;

import com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantsService {

    @Autowired
    private ParticipantsRepository repository;

    public void registerParticipantsToEvent(List<ParticipantRequestPayload> participants, Trip trip) {
        for (ParticipantRequestPayload email : participants) {
            Participants participant = new Participants();
            participant.setTrip(trip);
            participant.setParticipantEmail(email.email);
            participant.setParticipantName(email.name);
            participant.setIsConfirmed(false);
            repository.save(participant);
        }
    }

    public void triggerConfirmationEmailToParticipants(Trip trip) {
        List<Participants> tripParticipants = repository.findByTripId(trip.getId());
        if(tripParticipants.isEmpty()) {
            return;
        }
        for(Participants participant : tripParticipants) {
            if(participant.getIsConfirmed()) {
                return;
            }
            System.out.println("Email enviado para: " + participant.getParticipantEmail());
        }
    }
}
