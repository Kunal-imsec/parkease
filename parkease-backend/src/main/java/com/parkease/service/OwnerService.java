package com.parkease.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.parkease.dto.ParkingLotDTO;
import com.parkease.model.Booking;
import com.parkease.model.ParkingLot;
import com.parkease.util.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    
    @Autowired
    private JsonFileUtil jsonFileUtil;
    
    public List<ParkingLotDTO> getOwnerParkingLots(String ownerId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        return lots.stream()
                .filter(l -> l.getOwnerId().equals(ownerId))
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public ParkingLotDTO createParkingLot(ParkingLot lot, String ownerId, String ownerName) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        
        lot.setId(UUID.randomUUID().toString());
        lot.setOwnerId(ownerId);
        lot.setOwnerName(ownerName);
        lot.setAvailableSlots(lot.getTotalSlots());
        lot.setActive(true);
        
        lots.add(lot);
        jsonFileUtil.writeList("parkingLots.json", lots);
        
        return ParkingLotDTO.fromModel(lot);
    }
    
    public ParkingLotDTO updateParkingLot(String lotId, ParkingLot updatedLot, String ownerId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        
        ParkingLot lot = lots.stream()
                .filter(l -> l.getId().equals(lotId) && l.getOwnerId().equals(ownerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parking lot not found or unauthorized"));
        
        lot.setName(updatedLot.getName());
        lot.setAddress(updatedLot.getAddress());
        lot.setCity(updatedLot.getCity());
        lot.setArea(updatedLot.getArea());
        lot.setLatitude(updatedLot.getLatitude());
        lot.setLongitude(updatedLot.getLongitude());
        lot.setPricePerHour(updatedLot.getPricePerHour());
        lot.setTotalSlots(updatedLot.getTotalSlots());
        lot.setAvailability(updatedLot.getAvailability());
        
        jsonFileUtil.writeList("parkingLots.json", lots);
        
        return ParkingLotDTO.fromModel(lot);
    }
    
    public String deleteParkingLot(String lotId, String ownerId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        
        boolean removed = lots.removeIf(l -> l.getId().equals(lotId) && l.getOwnerId().equals(ownerId));
        
        if (!removed) {
            throw new RuntimeException("Parking lot not found or unauthorized");
        }
        
        jsonFileUtil.writeList("parkingLots.json", lots);
        
        return "Parking lot deleted successfully";
    }
    
    public String updateAvailability(String lotId, String availability, String ownerId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        
        ParkingLot lot = lots.stream()
                .filter(l -> l.getId().equals(lotId) && l.getOwnerId().equals(ownerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parking lot not found or unauthorized"));
        
        lot.setAvailability(availability);
        jsonFileUtil.writeList("parkingLots.json", lots);
        
        return "Availability updated successfully";
    }
    
    public List<Booking> getOwnerBookings(String ownerId) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        List<String> lotIds = lots.stream()
                .filter(l -> l.getOwnerId().equals(ownerId))
                .map(ParkingLot::getId)
                .collect(Collectors.toList());
        
        List<Booking> bookings = jsonFileUtil.readList("bookings.json", new TypeReference<List<Booking>>() {});
        return bookings.stream()
                .filter(b -> lotIds.contains(b.getParkingLotId()))
                .collect(Collectors.toList());
    }
}
