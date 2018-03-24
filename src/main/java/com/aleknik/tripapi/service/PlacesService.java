package com.aleknik.tripapi.service;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.model.dto.PlacesResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Service layer for communicating with Google Places api.
 */
@Service
public class PlacesService {

    @Value("${google-api-key}")
    private String key;

    private static final String PLACES_API = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    private final RestTemplate restTemplate;

    @Autowired
    public PlacesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Check if location exists
     *
     * @param location Location name
     */
    public void checkIfLocationExists(String location) {
        final HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PLACES_API)
                .queryParam("query", location)
                .queryParam("key", key);

        final HttpEntity<?> entity = new HttpEntity<>(headers);

        final HttpEntity<PlacesResponseDto> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                PlacesResponseDto.class);

        if (response.getBody() == null || !response.getBody().getStatus().equals("OK")) {
            throw new BadRequestException(String.format("Destination '%s' does not exist!", location));
        }
    }
}
