package com.aleknik.tripapi.unit.service;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.model.dto.PlacesResponseDto;
import com.aleknik.tripapi.service.PlacesService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.mockito.Mockito.*;

public class PlacesServiceTest {

    private RestTemplate restTemplateMock;

    private PlacesService placesService;

    @Before
    public void init() {
        restTemplateMock = mock(RestTemplate.class);

        placesService = new PlacesService(restTemplateMock);
    }

    @Test
    public void checkIfLocationExistsShouldNotThrowExceptionIfLocationExists() {
        // Arrange
        final PlacesResponseDto dto = new PlacesResponseDto(PlacesResponseDto.OK);
        ResponseEntity<PlacesResponseDto> entity = new ResponseEntity<>(dto, null, HttpStatus.OK);

        when(restTemplateMock.exchange(any(URI.class), any(HttpMethod.class),
                any(HttpEntity.class), eq(PlacesResponseDto.class))).thenReturn(entity);

        // Act
        placesService.checkIfLocationExists("test");
    }

    @Test(expected = BadRequestException.class)
    public void checkIfLocationExistsShouldThrowExceptionIfLocationDoesNotExists() {
        // Arrange
        final PlacesResponseDto dto = new PlacesResponseDto(PlacesResponseDto.ZERO_RESULTS);
        ResponseEntity<PlacesResponseDto> entity = new ResponseEntity<>(dto, null, HttpStatus.OK);

        when(restTemplateMock.exchange(any(URI.class), any(HttpMethod.class),
                any(HttpEntity.class), eq(PlacesResponseDto.class))).thenReturn(entity);

        // Act
        placesService.checkIfLocationExists("test");
    }
}
