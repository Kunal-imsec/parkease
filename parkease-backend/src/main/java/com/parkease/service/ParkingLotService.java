package com.parkease.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.parkease.dto.ParkingLotDTO;
import com.parkease.model.ParkingLot;
import com.parkease.util.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingLotService {
    
    @Autowired
    private JsonFileUtil jsonFileUtil;
    
    public List<ParkingLotDTO> getAllAvailableParkingLots() {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        return lots.stream()
                .filter(l -> l.isActive() && "AVAILABLE".equals(l.getAvailability()) && l.getAvailableSlots() > 0)
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public List<ParkingLotDTO> getAllParkingLots() {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        return lots.stream()
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public ParkingLotDTO getParkingLotById(String id) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        ParkingLot lot = lots.stream()
                .filter(l -> l.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));
        
        return ParkingLotDTO.fromModel(lot);
    }
    
    public List<ParkingLotDTO> searchByCity(String city) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        return lots.stream()
                .filter(l -> l.isActive() && "AVAILABLE".equals(l.getAvailability()) 
                        && l.getCity().toLowerCase().contains(city.toLowerCase()))
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }
    
    public List<ParkingLotDTO> searchByArea(String area) {
        List<ParkingLot> lots = jsonFileUtil.readList("parkingLots.json", new TypeReference<List<ParkingLot>>() {});
        return lots.stream()
                .filter(l -> l.isActive() && "AVAILABLE".equals(l.getAvailability()) 
                        && l.getArea().toLowerCase().contains(area.toLowerCase()))
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }
}
