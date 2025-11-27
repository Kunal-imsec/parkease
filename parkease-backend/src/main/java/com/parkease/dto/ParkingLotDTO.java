package com.parkease.dto;

import com.parkease.model.ParkingLot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingLotDTO {
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
    private String availability;
    private boolean active;
    
    public static ParkingLotDTO fromModel(ParkingLot lot) {
        return new ParkingLotDTO(
            lot.getId(), lot.getOwnerId(), lot.getOwnerName(),
            lot.getName(), lot.getAddress(), lot.getCity(), lot.getArea(),
            lot.getLatitude(), lot.getLongitude(), lot.getPricePerHour(),
            lot.getTotalSlots(), lot.getAvailableSlots(),
            lot.getAvailability(), lot.isActive()
        );
    }
}
