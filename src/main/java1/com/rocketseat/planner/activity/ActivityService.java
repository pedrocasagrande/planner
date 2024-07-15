package com.rocketseat.planner.activity;

import com.rocketseat.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestPayload payload, Trip trip) {
        Activity activity = new Activity();
        activity.setTrip(trip);
        activity.setActivityTitle(payload.title());
        activity.setOccursAt(LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME));
        activity = repository.save(activity);
        return new ActivityResponse(activity.getId());
    }
}
