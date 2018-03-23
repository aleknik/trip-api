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
}
