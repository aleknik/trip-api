package com.aleknik.tripapi.controller;

import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.model.dto.TripCreateDto;
import com.aleknik.tripapi.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/trips")
    public ResponseEntity create(@Valid @RequestBody TripCreateDto tripCreateDto) throws URISyntaxException {
        final Trip trip = tripService.create(tripCreateDto.createTrip());
        return ResponseEntity.created(new URI("/api/trips/" + trip.getId()))
                .body(trip);
    }

    @GetMapping("/trips/{id}")
    public ResponseEntity create(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.findById(id));
    }

    @GetMapping("/trips/future-trips")
    public ResponseEntity findFutureTrips() {
        return ResponseEntity.ok(tripService.findFutureTrips());
    }
}
