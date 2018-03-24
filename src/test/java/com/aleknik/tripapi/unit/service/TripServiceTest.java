package com.aleknik.tripapi.unit.service;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.controller.exception.NotFoundException;
import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.repository.TripRepository;
import com.aleknik.tripapi.service.TripService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TripServiceTest {

    private TripRepository tripRepositoryMock;

    private TripService tripService;

    @Before
    public void init() {
        tripRepositoryMock = mock(TripRepository.class);

        tripService = new TripService(tripRepositoryMock);
    }

    @Test
    public void createShouldReturnCreatedTripWhenValid() {
        // Arrange
        when(tripRepositoryMock.save(isA(Trip.class))).thenReturn(new Trip());

        final Trip trip = new Trip("dest", new Date(), new Date(), "comment");

        // Act
        final Trip created = tripService.create(trip);

        // Assert
        assertNotNull(created);
    }

    @Test(expected = BadRequestException.class)
    public void createShouldThrowExceptionWhenStartDateAfterEndDate() {
        // Arrange
        final Date now = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, 1);

        final Trip trip = new Trip("dest", c.getTime(), now, "comment");

        // Act
        tripService.create(trip);
    }

    @Test
    public void findByIdShouldReturnTripIfExists() {
        // Arrange
        when(tripRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(new Trip()));

        // Act
        final Trip trip = tripService.findById(1L);

        // Assert
        assertNotNull(trip);
    }

    @Test(expected = NotFoundException.class)
    public void findByIdShouldThrowExceptionIfTripDoesNotExist() {
        // Arrange
        when(tripRepositoryMock.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act
        tripService.findById(1L);
    }

    @Test
    public void findFutureTripsShouldReturnSortedFutureStrips() {
        // Arrange
        final List<Trip> trips = new ArrayList<>();
        final Date now = new Date();
        final Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.DATE, 1);

        trips.add(new Trip("dest1", c.getTime(), c.getTime(), "comment"));
        trips.add(new Trip("dest2", now, now, "comment"));
        when(tripRepositoryMock.findAllByStartDateAfter(any(Date.class))).thenReturn(trips);

        // Act
        final List<Trip> futureTrips = tripService.findFutureTrips();

        // Assert
        assertEquals(trips.size(), futureTrips.size());
        assertEquals("dest2", futureTrips.get(0).getDestination());
    }

    @Test
    public void findAllByDestinationShouldReturnTrips() {
        // Arrange
        final List<Trip> trips = new ArrayList<>();
        trips.add(new Trip("dest", new Date(), new Date(), "comment"));
        when(tripRepositoryMock.findAllByDestination(any(String.class))).thenReturn(trips);

        // Act
        final List<Trip> allTrips = tripService.findByDestination("dest");

        // Assert
        assertEquals(trips.size(), allTrips.size());
    }
}
