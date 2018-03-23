package com.aleknik.tripapi.service;

import com.aleknik.tripapi.controller.exception.BadRequestException;
import com.aleknik.tripapi.model.dto.PlacesResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PlacesService {

    @Value("${google-api-key}")
    private String key;

    private static final String PLACES_API = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    public void checkIfLocationExists(String location) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(PLACES_API)
                .queryParam("query", location)
                .queryParam("key", key);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<PlacesResponseDto> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                PlacesResponseDto.class);

        if (response.getBody() == null || !response.getBody().getStatus().equals("OK")) {
            throw new BadRequestException(String.format("Destination '%s' does not exist!", location));
        }
    }
}
