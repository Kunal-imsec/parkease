package com.parkease.service;

import com.parkease.dto.BookingDTO;
import com.parkease.model.Booking;
import com.parkease.model.ParkingLot;
import com.parkease.model.User;
import com.parkease.repository.BookingRepository;
import com.parkease.repository.ParkingLotRepository;
import com.parkease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public BookingDTO createBooking(String userId, String parkingLotId, int hours) {
        // Update expired bookings to COMPLETED
        updateExpiredBookings();

        // Check for active bookings for this user
        boolean hasActiveBooking = bookingRepository.existsByUserIdAndStatus(userId, "CONFIRMED");

        if (hasActiveBooking) {
            throw new RuntimeException(
                    "You already have an active booking. Please cancel or wait for it to complete before making a new booking.");
        }

        ParkingLot lot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        if (lot.getAvailableSlots() <= 0) {
            throw new RuntimeException("No available slots");
        }

        if (!"AVAILABLE".equals(lot.getAvailability())) {
            throw new RuntimeException("Parking lot is not available");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setUserName(user.getName());
        booking.setUserEmail(user.getEmail());
        booking.setParkingLotId(parkingLotId);
        booking.setParkingLotName(lot.getName());
        booking.setParkingLotAddress(lot.getAddress());
        booking.setLatitude(lot.getLatitude());
        booking.setLongitude(lot.getLongitude());
        booking.setPricePerHour(lot.getPricePerHour());
        booking.setHours(hours);
        booking.setTotalPrice(lot.getPricePerHour() * hours);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");

        // Update available slots
        lot.setAvailableSlots(lot.getAvailableSlots() - 1);
        parkingLotRepository.save(lot);

        // Save booking
        booking = bookingRepository.save(booking);

        return BookingDTO.fromModel(booking);
    }

    private void updateExpiredBookings() {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> confirmedBookings = bookingRepository.findByStatus("CONFIRMED");

        for (Booking booking : confirmedBookings) {
            LocalDateTime bookingTime = booking.getBookingDate();
            LocalDateTime expiryTime = bookingTime.plusHours(booking.getHours());

            if (now.isAfter(expiryTime)) {
                booking.setStatus("COMPLETED");
                bookingRepository.save(booking);

                // Free up the slot
                parkingLotRepository.findById(booking.getParkingLotId())
                        .ifPresent(lot -> {
                            lot.setAvailableSlots(lot.getAvailableSlots() + 1);
                            parkingLotRepository.save(lot);
                        });
            }
        }
    }

    @Transactional
    public List<BookingDTO> getUserBookings(String userId) {
        // Update expired bookings before returning
        updateExpiredBookings();

        return bookingRepository.findByUserId(userId).stream()
                .map(BookingDTO::fromModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookingDTO> getAllBookings() {
        // Update expired bookings before returning
        updateExpiredBookings();

        return bookingRepository.findAll().stream()
                .map(BookingDTO::fromModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public String cancelBooking(String bookingId, String userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUserId().equals(userId)) {
            throw new RuntimeException("Booking not found or unauthorized");
        }

        if (!"CONFIRMED".equals(booking.getStatus())) {
            throw new RuntimeException("Only confirmed bookings can be cancelled");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);

        // Restore available slot
        parkingLotRepository.findById(booking.getParkingLotId())
                .ifPresent(lot -> {
                    lot.setAvailableSlots(lot.getAvailableSlots() + 1);
                    parkingLotRepository.save(lot);
                });

        return "Booking cancelled successfully";
    }

    public List<BookingDTO> getParkingLotBookings(String parkingLotId) {
        return bookingRepository.findByParkingLotId(parkingLotId).stream()
                .map(BookingDTO::fromModel)
                .collect(Collectors.toList());
    }
}
