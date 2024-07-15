package com.rocketseat.planner.activity;

import com.rocketseat.planner.abstracts.AbstractController;
import com.rocketseat.planner.trip.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class ActivityController extends AbstractController {

    @PostMapping("/{id}/activity")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id,
                                                             @RequestBody ActivityRequestPayload payload){
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ActivityResponse response = activityService.registerActivity(payload, trip.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/activity/{activityId}")
    public ResponseEntity updateActivity(@PathVariable UUID activityId, @RequestBody ActivityRequestPayload payload) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        if(activity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Activity rawActivity = activity.get();
        rawActivity.setActivityTitle(payload.title());
        rawActivity.setOccursAt(LocalDateTime.parse(payload.occurs_at(), DateTimeFormatter.ISO_DATE_TIME));
        activityRepository.save(rawActivity);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<Activity>> getAllActivities(@PathVariable UUID id) {
        List<Activity> activities = activityRepository.findByTripId(id);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/{id}/activity/{activityId}")
    public ResponseEntity<Activity> getActivity(@PathVariable UUID id, @PathVariable UUID activityId) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
