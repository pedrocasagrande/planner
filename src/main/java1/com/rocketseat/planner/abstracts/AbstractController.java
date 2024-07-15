package com.rocketseat.planner.abstracts;

import com.rocketseat.planner.activity.ActivityRepository;
import com.rocketseat.planner.activity.ActivityService;
import com.rocketseat.planner.link.LinkRepository;
import com.rocketseat.planner.link.LinkService;
import com.rocketseat.planner.participants.ParticipantsRepository;
import com.rocketseat.planner.participants.ParticipantsService;
import com.rocketseat.planner.trip.TripRepository;
import com.rocketseat.planner.trip.TripService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractController {

    //Trip
    @Autowired
    protected TripRepository tripRepository;

    @Autowired
    protected TripService tripService;

    //Participants
    @Autowired
    protected ParticipantsRepository participantsRepository;

    @Autowired
    protected ParticipantsService participantsService;

    //Activity
    @Autowired
    protected ActivityRepository activityRepository;

    @Autowired
    protected ActivityService activityService;

    //Link
    @Autowired
    protected LinkService linkService;

    @Autowired
    protected LinkRepository linkRepository;
}
