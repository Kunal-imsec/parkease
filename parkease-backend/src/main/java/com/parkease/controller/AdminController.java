package com.parkease.controller;

import com.parkease.dto.AdminDashboardDTO;
import com.parkease.dto.OwnerDTO;
import com.parkease.dto.OwnerRequestDTO;
import com.parkease.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardDTO> getDashboard() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }
    
    @GetMapping("/owner-requests/pending")
    public ResponseEntity<List<OwnerRequestDTO>> getPendingRequests() {
        return ResponseEntity.ok(adminService.getPendingOwnerRequests());
    }
    
    @GetMapping("/owner-requests")
    public ResponseEntity<List<OwnerRequestDTO>> getAllRequests() {
        return ResponseEntity.ok(adminService.getAllOwnerRequests());
    }
    
    @PostMapping("/owner-requests/{requestId}/approve")
    public ResponseEntity<?> approveRequest(@PathVariable String requestId) {
        try {
            String message = adminService.approveOwnerRequest(requestId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/owner-requests/{requestId}/reject")
    public ResponseEntity<?> rejectRequest(@PathVariable String requestId) {
        try {
            String message = adminService.rejectOwnerRequest(requestId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        return ResponseEntity.ok(adminService.getAllOwners());
    }
    
    @PutMapping("/parking-lots/{lotId}/toggle")
    public ResponseEntity<?> toggleParkingLot(@PathVariable String lotId) {
        try {
            String message = adminService.toggleParkingLotStatus(lotId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
