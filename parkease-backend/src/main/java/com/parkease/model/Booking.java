package com.parkease.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    private String userName;
    private String userEmail;

    @Column(name = "parking_lot_id", nullable = false)
    private String parkingLotId;

    private String parkingLotName;
    private String parkingLotAddress;
    private double latitude;
    private double longitude;
    private double pricePerHour;

    @Column(nullable = false)
    private int hours;

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private String status; // CONFIRMED, CANCELLED, COMPLETED
}
