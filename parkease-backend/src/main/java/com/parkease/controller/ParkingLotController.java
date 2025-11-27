package com.parkease.controller;

import com.parkease.dto.ParkingLotDTO;
import com.parkease.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins = "*")
public class ParkingLotController {
    
    @Autowired
    private ParkingLotService parkingLotService;
    
    @GetMapping("/available")
    public ResponseEntity<List<ParkingLotDTO>> getAvailableParkingLots() {
        return ResponseEntity.ok(parkingLotService.getAllAvailableParkingLots());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ParkingLotDTO>> getAllParkingLots() {
        return ResponseEntity.ok(parkingLotService.getAllParkingLots());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingLotById(@PathVariable String id) {
        try {
            ParkingLotDTO lot = parkingLotService.getParkingLotById(id);
            return ResponseEntity.ok(lot);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/search/city/{city}")
    public ResponseEntity<List<ParkingLotDTO>> searchByCity(@PathVariable String city) {
        return ResponseEntity.ok(parkingLotService.searchByCity(city));
    }
    
    @GetMapping("/search/area/{area}")
    public ResponseEntity<List<ParkingLotDTO>> searchByArea(@PathVariable String area) {
        return ResponseEntity.ok(parkingLotService.searchByArea(area));
    }
}
