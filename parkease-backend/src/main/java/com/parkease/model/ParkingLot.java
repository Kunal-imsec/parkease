package com.parkease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLot {
    private String id;
    private String ownerId;
    private String ownerName;
    private String name;
    private String address;
    private String city;
    private String area;
    private double latitude;
    private double longitude;
    private double pricePerHour;
    private int totalSlots;
    private int availableSlots;
    private String availability; // AVAILABLE, NOT_AVAILABLE
    private boolean active;
}
