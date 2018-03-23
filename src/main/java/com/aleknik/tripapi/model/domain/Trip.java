package com.aleknik.tripapi.model.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "comment", nullable = true)
    private String comment;

    public Trip() {
    }

    public Trip(String destination, Date startDate, Date endDate, String comment) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
