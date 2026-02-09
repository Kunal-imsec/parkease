package com.parkease.dto;

import com.parkease.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private String id;
    private String userId;
    private String userName;
    private String userEmail;
    private String parkingLotId;
    private String parkingLotName;
    private String parkingLotAddress;
    private double latitude;
    private double longitude;
    private double pricePerHour;
    private int hours;
    private double totalPrice;
    private String bookingDate;
    private String status;

    public static BookingDTO fromModel(Booking booking) {
        return new BookingDTO(
                booking.getId(), booking.getUserId(), booking.getUserName(),
                booking.getUserEmail(), booking.getParkingLotId(),
                booking.getParkingLotName(), booking.getParkingLotAddress(),
                booking.getLatitude(), booking.getLongitude(),
                booking.getPricePerHour(), booking.getHours(),
                booking.getTotalPrice(),
                booking.getBookingDate() != null ? booking.getBookingDate().toString() : null,
                booking.getStatus());
    }
}
