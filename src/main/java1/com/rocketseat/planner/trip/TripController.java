package com.rocketseat.planner.trip;

import com.rocketseat.planner.abstracts.AbstractController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController extends AbstractController {

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        return ResponseEntity.ok(tripService.createTrip(payload));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTrip(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tripService.updateTrip(payload, trip.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity confirmTravel(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tripService.confirmTravel(trip.get());
        return ResponseEntity.noContent().build();
    }
}
