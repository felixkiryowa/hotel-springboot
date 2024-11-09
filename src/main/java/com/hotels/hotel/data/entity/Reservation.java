package com.hotels.hotel.data.entity;

import java.util.List;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "reservations")
@Data
@ToString
public class Reservation {

    @Id
    @Column(name="reservation_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "guest_id", referencedColumnName = "guest_id", nullable = false)
    private Guest guestId;

    @OneToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    private Room roomId;
    
    @Column(name = "res_date")
    @Temporal(TemporalType.DATE)
    private Date resDate;

}
