package com.hotels.hotel;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hotels.hotel.data.entity.Guest;
import com.hotels.hotel.data.entity.Reservation;
import com.hotels.hotel.data.entity.Room;
import com.hotels.hotel.data.repository.GuestRepository;
import com.hotels.hotel.data.repository.ReservationRepository;
import com.hotels.hotel.data.repository.RoomRepository;
import com.hotels.hotel.service.RoomReservationService;
import com.hotels.hotel.service.model.RoomReservation;

@Component
public class BackupCLRunner implements CommandLineRunner{

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final RoomReservationService roomReservationService;

    public BackupCLRunner(RoomRepository roomRepository, GuestRepository guestRepository, 
    ReservationRepository reservationRepository, RoomReservationService roomReservationService) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.roomReservationService = roomReservationService;
    }

    @Override
    public void run(String... args) throws Exception {

        // List<Room> rooms = roomRepository.findAll();
        // Optional<Room> room = roomRepository.findByRoomNumberIgnoreCase("p1");
        // System.out.println(room);
        // // rooms.forEach(System.out::println);

        // System.out.println("*** RESERVATIONS ***");
        // List<Reservation> reservations = this.reservationRepository.findAll();
        // Optional<Reservation> reservation = reservationRepository.findByReservationId(1L);
        // System.out.println(reservation.toString());
        // reservations.forEach(System.out::println);

        // System.out.println("*** GUESTS ****");
        // List<Guest> guests = guestRepository.findAll();
        // guests.forEach(System.out::println);

        // reservations.forEach(System.out::println);


        List<RoomReservation> reservations = this.roomReservationService.getRoomReservationsforDate("2023-08-28");
        reservations.forEach(System.out::println);


        
    }

}
