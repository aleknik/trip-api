package com.aleknik.tripapi.model.dto;

public class PlacesResponseDto {

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
