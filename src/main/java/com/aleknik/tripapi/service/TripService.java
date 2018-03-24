package com.aleknik.tripapi.service;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.controller.exception.NotFoundException;
import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

/**
 * Service layer for trips' business logic.
 */
@Service
public class TripService {

    private final TripRepository tripRepository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Trip create(Trip trip) {
        if (trip.getStartDate().after(trip.getEndDate())) {
            throw new BadRequestException("Start date is after end date!");
        }
        return tripRepository.save(trip);
    }

    /**
     * Find trip by ID.
     *
     * @param id Trip ID
     * @return Trip matching id
     */
    public Trip findById(Long id) {
        return tripRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("No trip with ID %s found!", id)));
    }

    /**
     * Find future trips.
     *
     * @return Sorted list of future trips
     */
    public List<Trip> findFutureTrips() {
        final Date curr = new Date();
        final List<Trip> futureTrips = tripRepository.findAllByStartDateAfter(curr);

        final LocalDateTime currentDate = LocalDateTime.ofInstant(curr.toInstant(), ZoneId.systemDefault());
        futureTrips.sort((o1, o2) -> {
            final LocalDateTime start1 = LocalDateTime.ofInstant(o1.getStartDate().toInstant(), ZoneId.systemDefault());
            long days1 = ChronoUnit.DAYS.between(currentDate.toLocalDate(), start1.toLocalDate());

            final LocalDateTime start2 = LocalDateTime.ofInstant(o2.getStartDate().toInstant(), ZoneId.systemDefault());
            long days2 = ChronoUnit.DAYS.between(currentDate.toLocalDate(), start2.toLocalDate());

            return Long.compare(days1, days2);
        });

        return futureTrips;
    }

    /**
     * Find trips by destination.
     *
     * @param destination Destination name
     * @return List of trips with matching destination name
     */
    public List<Trip> findByDestination(String destination) {
        return tripRepository.findAllByDestination(destination);
    }
}
