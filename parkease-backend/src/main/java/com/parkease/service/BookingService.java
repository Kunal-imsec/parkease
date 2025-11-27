package com.parkease.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.parkease.dto.BookingDTO;
import com.parkease.model.Booking;
import com.parkease.model.ParkingLot;
import com.parkease.model.User;
import com.parkease.util.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private JsonFileUtil jsonFileUtil;

    public BookingDTO createBooking(String userId, String parkingLotId, int hours) {
        // Check if user has any active bookings
        List<Booking> existingBookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });

        // Update expired bookings to COMPLETED
        updateExpiredBookings(existingBookings);

        // Check for active bookings for this user
        boolean hasActiveBooking = existingBookings.stream()
                .anyMatch(b -> b.getUserId().equals(userId) && "CONFIRMED".equals(b.getStatus()));

        if (hasActiveBooking) {
            throw new RuntimeException(
                    "You already have an active booking. Please cancel or wait for it to complete before making a new booking.");
        }

        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {
        });
        ParkingLot lot = lots.stream()
                .filter(l -> l.getId().equals(parkingLotId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        if (lot.getAvailableSlots() <= 0) {
            throw new RuntimeException("No available slots");
        }

        if (!"AVAILABLE".equals(lot.getAvailability())) {
            throw new RuntimeException("Parking lot is not available");
        }

        List<User> users = jsonFileUtil.readList("users.json", new TypeReference<List<User>>() {
        });
        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create booking
        Booking booking = new Booking();
        booking.setId(UUID.randomUUID().toString());
        booking.setUserId(userId);
        booking.setUserName(user.getName());
        booking.setUserEmail(user.getEmail());
        booking.setParkingLotId(parkingLotId);
        booking.setParkingLotName(lot.getName());
        booking.setParkingLotAddress(lot.getAddress());
        booking.setPricePerHour(lot.getPricePerHour());
        booking.setHours(hours);
        booking.setTotalPrice(lot.getPricePerHour() * hours);
        booking.setBookingDate(LocalDateTime.now().toString());
        booking.setStatus("CONFIRMED");

        // Update available slots
        lot.setAvailableSlots(lot.getAvailableSlots() - 1);
        jsonFileUtil.writeList("parkingLots.json", lots);

        // Save booking
        existingBookings.add(booking);
        jsonFileUtil.writeList("bookings.json", existingBookings);

        return BookingDTO.fromModel(booking);
    }

    private void updateExpiredBookings(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();
        boolean updated = false;

        for (Booking booking : bookings) {
            if ("CONFIRMED".equals(booking.getStatus())) {
                try {
                    LocalDateTime bookingTime = LocalDateTime.parse(booking.getBookingDate());
                    LocalDateTime expiryTime = bookingTime.plusHours(booking.getHours());

                    if (now.isAfter(expiryTime)) {
                        booking.setStatus("COMPLETED");
                        updated = true;

                        // Restore the parking slot
                        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json",
                                new TypeReference<List<ParkingLot>>() {
                                });
                        lots.stream()
                                .filter(l -> l.getId().equals(booking.getParkingLotId()))
                                .findFirst()
                                .ifPresent(lot -> {
                                    lot.setAvailableSlots(lot.getAvailableSlots() + 1);
                                });
                        jsonFileUtil.writeList("parkingLots.json", lots);
                    }
                } catch (Exception e) {
                    // If parsing fails, skip this booking
                    continue;
                }
            }
        }

        if (updated) {
            jsonFileUtil.writeList("bookings.json", bookings);
        }
    }

    public List<BookingDTO> getUserBookings(String userId) {
        List<Booking> bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });

        // Update expired bookings before returning
        updateExpiredBookings(bookings);

        // Re-read after update
        bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });

        return bookings.stream()
                .filter(b -> b.getUserId().equals(userId))
                .map(BookingDTO::fromModel)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });

        // Update expired bookings before returning
        updateExpiredBookings(bookings);

        // Re-read after update
        bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });

        return bookings.stream()
                .map(BookingDTO::fromModel)
                .collect(Collectors.toList());
    }

    public String cancelBooking(String bookingId, String userId) {
        List<Booking> bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {
        });
        Booking booking = bookings.stream()
                .filter(b -> b.getId().equals(bookingId) && b.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Booking not found or unauthorized"));

        booking.setStatus("CANCELLED");

        // Restore available slot
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {
        });
        lots.stream()
                .filter(l -> l.getId().equals(booking.getParkingLotId()))
                .findFirst()
                .ifPresent(lot -> {
                    lot.setAvailableSlots(lot.getAvailableSlots() + 1);
                });

        jsonFileUtil.writeList("bookings.json", bookings);
        jsonFileUtil.writeList("parkingLots.json", lots);

        return "Booking cancelled successfully";
    }
}
