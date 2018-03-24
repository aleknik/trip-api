package com.aleknik.tripapi.unit.controller;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.controller.exception.NotFoundException;
import com.aleknik.tripapi.model.domain.Trip;
import com.aleknik.tripapi.model.dto.TripCreateDto;
import com.aleknik.tripapi.service.PlacesService;
import com.aleknik.tripapi.service.TripService;
import com.aleknik.tripapi.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
public class TripControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripService tripService;

    @MockBean
    private PlacesService placesService;

    @Test
    public void createShouldReturnOKIfValid() throws Exception {
        // Arrange
        given(tripService.create(any(Trip.class)))
                .willReturn(new Trip());

        final String json = JsonUtil.json(new TripCreateDto("a", new Date(), new Date(), "a"));

        // Act
        final MockHttpServletResponse response = mvc.perform(
                post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    public void createShouldReturnBadRequestWhenContentIsInvalid() throws Exception {
        // Arrange
        final String json = JsonUtil.json(new TripCreateDto(null, new Date(), new Date(), "a"));

        // Act
        final MockHttpServletResponse response = mvc.perform(
                post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void createShouldReturnBadRequestWhenDestinationDoesNotExist() throws Exception {
        // Arrange
        willThrow(new BadRequestException()).given(placesService).checkIfLocationExists(anyString());
        final String json = JsonUtil.json(new TripCreateDto("a", new Date(), new Date(), "a"));

        // Act
        final MockHttpServletResponse response = mvc.perform(
                post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void findByIdShouldReturnOKIfExists() throws Exception {
        // Arrange
        given(tripService.findById(any(Long.class)))
                .willReturn(new Trip());

        // Act
        final MockHttpServletResponse response = mvc.perform(
                get("/api/trips/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void findByIdShouldReturnNotFoundIfDoesNotExist() throws Exception {
        // Arrange
        given(tripService.findById(any(Long.class)))
                .willThrow(new NotFoundException());

        // Act
        final MockHttpServletResponse response = mvc.perform(
                get("/api/trips/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void findFutureTripsShouldReturnOk() throws Exception {
        // Arrange
        given(tripService.findFutureTrips())
                .willReturn(new ArrayList<>());

        // Act
        final MockHttpServletResponse response = mvc.perform(
                get("/api/trips/future-trips")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void findByDestinationShouldReturnOk() throws Exception {
        // Arrange
        given(tripService.findByDestination(anyString()))
                .willReturn(new ArrayList<>());

        // Act
        final MockHttpServletResponse response = mvc.perform(
                get("/api/trips/destination")
                        .param("name", "test")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}