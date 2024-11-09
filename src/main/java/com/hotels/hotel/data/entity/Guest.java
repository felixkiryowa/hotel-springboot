package com.hotels.hotel.data.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "guests")
@Data
@ToString
public class Guest {

    @Id
    @Column(name = "guest_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long guestId;


    @OneToMany(mappedBy = "guestId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email_address")
    private String email_address;

    @Column(name="address")
    private String address;

    @Column(name = "country")
    private String country;

    @Column(name="state")
    private String state;

    @Column(name = "phone_number")
    private String phone_number;

}
