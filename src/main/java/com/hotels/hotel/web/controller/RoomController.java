package com.hotels.hotel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotels.hotel.data.repository.RoomRepository;

import org.springframework.ui.Model;;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final  RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public String getroom(Model model) {

        model.addAttribute("rooms", this.roomRepository.findAll());
        return "room-list";

    }



}
