package com.rocketseat.planner.trip;

import com.rocketseat.planner.participants.ParticipantRequestPayload;
import com.rocketseat.planner.participants.Participants;

import java.io.Serializable;
import java.util.List;

public class TripRequestPayload implements Serializable {

    public String destination;
    public String startsAt;
    public String endsAt;
    public List<ParticipantRequestPayload> emailsToInvite;
    public String ownerEmail;
    public String ownerName;
}
