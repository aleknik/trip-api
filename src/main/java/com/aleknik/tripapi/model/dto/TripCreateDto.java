package com.aleknik.tripapi.model.dto;

import com.aleknik.tripapi.model.domain.Trip;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TripCreateDto {

    @NotEmpty
    private String destination;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    private String comment;

    public TripCreateDto() {
    }

    public Trip createTrip() {
        return new Trip(destination, startDate, endDate, comment);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
