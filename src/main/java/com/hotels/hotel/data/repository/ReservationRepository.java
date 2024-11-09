package com.hotels.hotel.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotels.hotel.data.entity.Reservation;
import java.util.Optional;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation>findById(Long id);

    List<Reservation> findAllByResDate(Date date);

}
