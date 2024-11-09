package com.hotels.hotel.web.api;

import java.util.Date;
import java.util.List;
import  java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotels.hotel.data.entity.Reservation;
import com.hotels.hotel.data.entity.Room;
import com.hotels.hotel.data.repository.ReservationRepository;
import com.hotels.hotel.data.repository.RoomRepository;
import com.hotels.hotel.exceptions.BadRequestException;
import com.hotels.hotel.exceptions.NotFoundException;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/api/rooms")
public class RoomApiController {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public RoomApiController(RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@RequestBody Room room ){
       return this.roomRepository.save(room);
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable("id") long id) {
        Optional<Room> room =   this.roomRepository.findById(id);

        if(room.isEmpty()) {
            throw new NotFoundException("Room with Id " + id + " Not Found");
        }
        return room.get();

    }

    @GetMapping("/filter/reservations")
    public List<Reservation> getAllReservations(@RequestParam(value = "date", required = false)String dateString) {

          
         if(StringUtils.isNotBlank(dateString)) {
            Date date = new Date(new java.util.Date().getTime());
            return this.reservationRepository.findAllByResDate(date);
         }

         return this.reservationRepository.findAll();
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable("id") long id,   @RequestBody Room room ){
        if(id != room.getId()) {
            throw new BadRequestException("Id on path doesnt match body");
        }

        return this.roomRepository.save(room);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteroom(@PathVariable("id") long id) {
        this.roomRepository.deleteById(id);
    }

}
