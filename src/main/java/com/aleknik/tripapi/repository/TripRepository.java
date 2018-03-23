package com.aleknik.tripapi.repository;

import com.aleknik.tripapi.model.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {

    Optional<Trip> findById(long id);

}
