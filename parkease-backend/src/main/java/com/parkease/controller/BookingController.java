package com.parkease.controller;

import com.parkease.dto.BookingDTO;
import com.parkease.service.BookingService;
import com.parkease.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private String extractUserId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractId(token);
    }
    
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestHeader("Authorization") String authHeader, 
                                           @RequestBody Map<String, Object> request) {
        try {
            String userId = extractUserId(authHeader);
            String parkingLotId = (String) request.get("parkingLotId");
            int hours = (Integer) request.get("hours");
            
            BookingDTO booking = bookingService.createBooking(userId, parkingLotId, hours);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/user")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@RequestHeader("Authorization") String authHeader) {
        String userId = extractUserId(authHeader);
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }
    
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@RequestHeader("Authorization") String authHeader, @PathVariable String bookingId) {
        try {
            String userId = extractUserId(authHeader);
            String message = bookingService.cancelBooking(bookingId, userId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
