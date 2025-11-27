package com.parkease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    private String id;
    private String userId;
    private String userName;
    private String userEmail;
    private String parkingLotId;
    private String parkingLotName;
    private String parkingLotAddress;
    private double pricePerHour;
    private int hours;
    private double totalPrice;
    private String bookingDate;
    private String status; // CONFIRMED, CANCELLED, COMPLETED
}
