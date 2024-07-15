package com.rocketseat.planner.link;

import com.rocketseat.planner.abstracts.AbstractController;
import com.rocketseat.planner.trip.Trip;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class LinkController extends AbstractController {

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        LinkResponse response = linkService.registerLink(payload, trip.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/link/{linkId}")
    public ResponseEntity updateLink(@PathVariable UUID linkId, @RequestBody LinkRequestPayload payload) {
        Optional<Link> link = linkRepository.findById(linkId);
        if(link.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        linkService.updateLink(link.get(), payload);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<Link>> getAllLinks(@PathVariable UUID id) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<Link> links = linkRepository.findByTripId(id);
        return ResponseEntity.ok(links);
    }

    @GetMapping("/{id}/link/{linkId}")
    public ResponseEntity<Link> getLink(@PathVariable UUID id, @PathVariable UUID linkId) {
        Optional<Trip> trip = tripRepository.findById(id);
        if(trip.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Link> link = linkRepository.findById(linkId);
        return link.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
