package com.aleknik.tripapi.unit.service;

import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.repository.TripRepository;
import com.aleknik.tripapi.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
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
}
