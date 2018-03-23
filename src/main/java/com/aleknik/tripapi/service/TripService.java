package com.aleknik.tripapi.service;

import com.aleknik.tripapi.controller.exception.NotFoundException;
import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip create(Trip trip){
        return tripRepository.save(trip);
    }

    public Trip findById(Long id){
        return tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No trip with ID %s found!", id)));
    }
}
