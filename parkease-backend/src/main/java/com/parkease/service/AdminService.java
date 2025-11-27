package com.parkease.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.parkease.dto.AdminDashboardDTO;
import com.parkease.dto.OwnerDTO;
import com.parkease.dto.OwnerRequestDTO;
import com.parkease.model.Booking;
import com.parkease.model.Owner;
import com.parkease.model.OwnerRequest;
import com.parkease.model.ParkingLot;
import com.parkease.model.User;
import com.parkease.util.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService {
    
    @Autowired
    private JsonFileUtil jsonFileUtil;
    
    public AdminDashboardDTO getDashboardStats() {
        List<User> users = jsonFileUtil.readList("users.json", new TypeReference<List<User>>() {});
        List<Owner> owners = jsonFileUtil.readList("owners.json", new TypeReference<List<Owner>>() {});
        List<ParkingLot> parkingLots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        List<Booking> bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {});
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json", new TypeReference<List<OwnerRequest>>() {});
        
        long pendingRequests = requests.stream().filter(r -> "PENDING".equals(r.getStatus())).count();
        long activeOwners = owners.stream().filter(Owner::isActive).count();
        long activeParkingLots = parkingLots.stream().filter(ParkingLot::isActive).count();
        
        return new AdminDashboardDTO(
            users.size(),
            owners.size(),
            parkingLots.size(),
            bookings.size(),
            pendingRequests,
            activeOwners,
            activeParkingLots
        );
    }
    
    public List<OwnerRequestDTO> getPendingOwnerRequests() {
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json", new TypeReference<List<OwnerRequest>>() {});
        return requests.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .map(OwnerRequestDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public List<OwnerRequestDTO> getAllOwnerRequests() {
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json", new TypeReference<List<OwnerRequest>>() {});
        return requests.stream()
                .map(OwnerRequestDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public String approveOwnerRequest(String requestId) {
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json", new TypeReference<List<OwnerRequest>>() {});
        OwnerRequest request = requests.stream()
                .filter(r -> r.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("APPROVED");
        jsonFileUtil.writeList("ownerRequests.json", requests);
        
        // Create owner account
        List<Owner> owners = jsonFileUtil.readList("owners.json", new TypeReference<List<Owner>>() {});
        Owner owner = new Owner();
        owner.setId(UUID.randomUUID().toString());
        owner.setEmail(request.getEmail());
        owner.setPassword(request.getPassword());
        owner.setName(request.getName());
        owner.setPhone(request.getPhone());
        owner.setBusinessName(request.getBusinessName());
        owner.setApproved(true);
        owner.setActive(true);
        
        owners.add(owner);
        jsonFileUtil.writeList("owners.json", owners);
        
        return "Owner request approved successfully";
    }
    
    public String rejectOwnerRequest(String requestId) {
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json", new TypeReference<List<OwnerRequest>>() {});
        OwnerRequest request = requests.stream()
                .filter(r -> r.getId().equals(requestId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Request not found"));
        
        request.setStatus("REJECTED");
        jsonFileUtil.writeList("ownerRequests.json", requests);
        
        return "Owner request rejected";
    }
    
    public List<OwnerDTO> getAllOwners() {
        List<Owner> owners = jsonFileUtil.readList("owners.json", new TypeReference<List<Owner>>() {});
        return owners.stream()
                .map(o -> new OwnerDTO(o.getId(), o.getEmail(), o.getName(), o.getPhone(), 
                        o.getBusinessName(), o.isApproved(), o.isActive()))
                .collect(Collectors.toList());
    }
    
    public String toggleParkingLotStatus(String lotId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        ParkingLot lot = lots.stream()
                .filter(l -> l.getId().equals(lotId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));
        
        lot.setActive(!lot.isActive());
        jsonFileUtil.writeList("parkingLots.json", lots);
        
        return "Parking lot status updated";
    }
}
