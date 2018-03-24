package com.aleknik.tripapi.model.dto;

public class PlacesResponseDto {

    public static final String OK = "OK";

    public static final String ZERO_RESULTS = "ZERO_RESULTS";

    private String status;

    public PlacesResponseDto() {
    }

    public PlacesResponseDto(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
