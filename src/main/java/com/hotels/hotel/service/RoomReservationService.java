package com.hotels.hotel.service;

import org.springframework.stereotype.Service;

import com.hotels.hotel.data.entity.Reservation;
import com.hotels.hotel.data.entity.Room;
import com.hotels.hotel.data.entity.Guest;
import com.hotels.hotel.data.repository.GuestRepository;
import com.hotels.hotel.data.repository.ReservationRepository;
import com.hotels.hotel.data.repository.RoomRepository;
import com.hotels.hotel.service.model.RoomReservation;

import io.micrometer.common.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.sql.Date;

@Service
public class RoomReservationService {

    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;


    public RoomReservationService(GuestRepository guestRepository, RoomRepository roomRepository,
            ReservationRepository reservationRepository) {
        this.guestRepository = guestRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation>getRoomReservationsforDate(String reservationDate) {
        Date date = null;

        if(StringUtils.isNotEmpty(reservationDate)) {
            date = Date.valueOf(reservationDate);
        }else {
            date = new Date(new java.util.Date().getTime());
        }

        Map<Long, RoomReservation> roomReservations = new HashMap<>();

        List<Room> rooms = roomRepository.findAll();

        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservations.put(roomReservation.getRoomId(), roomReservation);
        });

        List<Reservation> reservations = this.reservationRepository.findAllByResDate(date);
        reservations.forEach(reservation -> {
            Room room_id = reservation.getRoomId();
            RoomReservation roomReservation = roomReservations.get(room_id.getId());
            roomReservation.setReservationId(reservation.getId());
            roomReservation.setResDate(reservation.getResDate().toString());

            Guest guest_id = reservation.getGuestId();
            Optional<Guest> guest = this.guestRepository.findById(guest_id.getGuestId());
            roomReservation.setGuestId(guest.get().getGuestId());
            roomReservation.setFirstName(guest.get().getFirst_name());
            roomReservation.setLastName(guest.get().getLast_name());

        });

        return roomReservations.values().stream().toList();

    }



}
