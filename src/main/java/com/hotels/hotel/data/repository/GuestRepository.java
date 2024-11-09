package com.hotels.hotel.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotels.hotel.data.entity.Guest;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByGuestId(Long guestId);

}
