package com.rocketseat.planner.link;

import com.rocketseat.planner.trip.Trip;
import com.rocketseat.planner.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @Autowired
    private LinkRepository repository;

    public LinkResponse registerLink(LinkRequestPayload payload, Trip trip) {
        Link link = new Link();
        link.setLinkTitle(payload.title());
        link.setUrl(payload.url());
        link.setTrip(trip);
        link = repository.save(link);
        return new LinkResponse(link.getId());
    }

    public void updateLink(Link link, LinkRequestPayload payload) {
        link.setLinkTitle(payload.title());
        link.setUrl(payload.url());
        repository.save(link);
    }
}
