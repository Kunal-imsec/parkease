package com.parkease.controller;

import com.parkease.dto.ParkingLotDTO;
import com.parkease.model.Booking;
import com.parkease.model.ParkingLot;
import com.parkease.service.OwnerService;
import com.parkease.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/owner")
@CrossOrigin(origins = "*")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private String extractOwnerId(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractId(token);
    }
    
    private String extractOwnerName(String authHeader) {
        String token = authHeader.substring(7);
        return jwtUtil.extractEmail(token);
    }
    
    @GetMapping("/parking-lots")
    public ResponseEntity<List<ParkingLotDTO>> getOwnerParkingLots(@RequestHeader("Authorization") String authHeader) {
        String ownerId = extractOwnerId(authHeader);
        return ResponseEntity.ok(ownerService.getOwnerParkingLots(ownerId));
    }
    
    @PostMapping("/parking-lots")
    public ResponseEntity<?> createParkingLot(@RequestHeader("Authorization") String authHeader, @RequestBody ParkingLot lot) {
        try {
            String ownerId = extractOwnerId(authHeader);
            String ownerName = extractOwnerName(authHeader);
            ParkingLotDTO created = ownerService.createParkingLot(lot, ownerId, ownerName);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/parking-lots/{lotId}")
    public ResponseEntity<?> updateParkingLot(@RequestHeader("Authorization") String authHeader, 
                                               @PathVariable String lotId, 
                                               @RequestBody ParkingLot lot) {
        try {
            String ownerId = extractOwnerId(authHeader);
            ParkingLotDTO updated = ownerService.updateParkingLot(lotId, lot, ownerId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/parking-lots/{lotId}")
    public ResponseEntity<?> deleteParkingLot(@RequestHeader("Authorization") String authHeader, @PathVariable String lotId) {
        try {
            String ownerId = extractOwnerId(authHeader);
            String message = ownerService.deleteParkingLot(lotId, ownerId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/parking-lots/{lotId}/availability")
    public ResponseEntity<?> updateAvailability(@RequestHeader("Authorization") String authHeader, 
                                                @PathVariable String lotId, 
                                                @RequestBody Map<String, String> request) {
        try {
            String ownerId = extractOwnerId(authHeader);
            String availability = request.get("availability");
            String message = ownerService.updateAvailability(lotId, availability, ownerId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getOwnerBookings(@RequestHeader("Authorization") String authHeader) {
        String ownerId = extractOwnerId(authHeader);
        return ResponseEntity.ok(ownerService.getOwnerBookings(ownerId));
    }
}
