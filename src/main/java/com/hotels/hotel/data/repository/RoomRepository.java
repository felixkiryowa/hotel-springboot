package com.hotels.hotel.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotels.hotel.data.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room>findByRoomNumberIgnoreCase(String roomNumber);

}
