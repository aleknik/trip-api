package com.aleknik.tripapi.controller;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.model.dto.TripCreateDto;
import com.aleknik.tripapi.service.PlacesService;
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

    private final PlacesService placesService;

    @Autowired
    public TripController(TripService tripService, PlacesService placesService) {
        this.tripService = tripService;
        this.placesService = placesService;
    }

    /**
     * POST /api/trips
     * Creates a trip.
     *
     * @param tripCreateDto DTO with trip info
     * @return ResponseEntity with trip
     */
    @PostMapping("/trips")
    public ResponseEntity create(@Valid @RequestBody TripCreateDto tripCreateDto) {

        placesService.checkIfLocationExists(tripCreateDto.getDestination());

        final Trip trip = tripService.create(tripCreateDto.createTrip());
        try {
            return ResponseEntity.created(new URI("/api/trips/" + trip.getId()))
                    .body(trip);
        } catch (URISyntaxException e) {
            throw new BadRequestException();
        }
    }

    /**
     * GET /api/trips/ID
     * Gets a trip by its ID.
     *
     * @param id ID of trip
     * @return ResponseEntity with trip
     */
    @GetMapping("/trips/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.findById(id));
    }

    /**
     * GET /api/trips/future-trips
     * Gets sorted list of future trips.
     *
     * @return ResponseEntity with sorted list of trips
     */
    @GetMapping("/trips/future-trips")
    public ResponseEntity findFutureTrips() {
        return ResponseEntity.ok(tripService.findFutureTrips());
    }

    /**
     * GET /api/trips/destination?name=destination
     * Gets sorted list of future trips.
     *
     * @param name Trip destination
     * @return ResponseEntity with list of trips matching destination
     */
    @GetMapping("/trips/destination")
    public ResponseEntity findByDestination(@RequestParam String name) {
        return ResponseEntity.ok(tripService.findByDestination(name));
    }
}
