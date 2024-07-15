package com.rocketseat.planner.participants;

import com.rocketseat.planner.abstracts.AbstractController;
import com.rocketseat.planner.trip.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class ParticipantsController extends AbstractController {

    @PutMapping("/participants/{participantId}/confirm")
    public ResponseEntity confirmParticipant(@PathVariable UUID participantId) {
        Optional<Participants> participants = participantsRepository.findById(participantId);
        if(participants.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Participants rawParticipant = participants.get();
        rawParticipant.setIsConfirmed(true);
        participantsRepository.save(rawParticipant);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/participants/invite")
    public ResponseEntity inviteParticipants(@PathVariable UUID id,
                                             @RequestBody List<ParticipantRequestPayload> participants) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        participantsService.registerParticipantsToEvent(participants, trip.get());
        if(trip.get().getIsConfirmed()) {
            participantsService.triggerConfirmationEmailToParticipants(trip.get());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<Participants>> getParticipants(@PathVariable UUID id){
        List<Participants> participants = participantsRepository.findByTripId(id);
        if(participants.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(participants);
    }

    @GetMapping("/participant/{idParticipant}")
    public ResponseEntity<Participants> getParticipant(@PathVariable UUID idParticipant) {
        Optional<Participants> participant = participantsRepository.findById(idParticipant);
        return participant.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
