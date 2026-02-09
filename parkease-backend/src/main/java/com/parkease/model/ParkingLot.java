package com.parkease.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_lots")
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // Keep ownerId for backward compatibility with services
    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    private String ownerName;

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String area;
    private double latitude;
    private double longitude;

    @Column(nullable = false)
    private double pricePerHour;

    @Column(nullable = false)
    private int totalSlots;

    @Column(nullable = false)
    private int availableSlots;

    @Column(nullable = false)
    private String availability; // AVAILABLE, NOT_AVAILABLE

    @Column(nullable = false)
    private boolean active = true;
}
