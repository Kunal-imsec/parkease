package com.parkease.service;

import com.parkease.dto.ParkingLotDTO;
import com.parkease.model.Booking;
import com.parkease.model.ParkingLot;
import com.parkease.repository.BookingRepository;
import com.parkease.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<ParkingLotDTO> getOwnerParkingLots(String ownerId) {
        return parkingLotRepository.findByOwnerId(ownerId).stream()
                .map(ParkingLotDTO::fromModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public ParkingLotDTO createParkingLot(ParkingLot lot, String ownerId, String ownerName) {
        lot.setOwnerId(ownerId);
        lot.setOwnerName(ownerName);
        lot.setAvailableSlots(lot.getTotalSlots());
        lot.setActive(true);

        lot = parkingLotRepository.save(lot);

        return ParkingLotDTO.fromModel(lot);
    }

    @Transactional
    public ParkingLotDTO updateParkingLot(String lotId, ParkingLot updatedLot, String ownerId) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        if (!lot.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized");
        }

        lot.setName(updatedLot.getName());
        lot.setAddress(updatedLot.getAddress());
        lot.setCity(updatedLot.getCity());
        lot.setArea(updatedLot.getArea());
        lot.setLatitude(updatedLot.getLatitude());
        lot.setLongitude(updatedLot.getLongitude());
        lot.setPricePerHour(updatedLot.getPricePerHour());
        lot.setTotalSlots(updatedLot.getTotalSlots());
        lot.setAvailability(updatedLot.getAvailability());

        lot = parkingLotRepository.save(lot);

        return ParkingLotDTO.fromModel(lot);
    }

    @Transactional
    public String deleteParkingLot(String lotId, String ownerId) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        if (!lot.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized");
        }

        parkingLotRepository.delete(lot);

        return "Parking lot deleted successfully";
    }

    @Transactional
    public String updateAvailability(String lotId, String availability, String ownerId) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        if (!lot.getOwnerId().equals(ownerId)) {
            throw new RuntimeException("Unauthorized");
        }

        lot.setAvailability(availability);
        parkingLotRepository.save(lot);

        return "Availability updated successfully";
    }

    public List<Booking> getOwnerBookings(String ownerId) {
        List<ParkingLot> lots = parkingLotRepository.findByOwnerId(ownerId);
        List<String> lotIds = lots.stream()
                .map(ParkingLot::getId)
                .collect(Collectors.toList());

        return bookingRepository.findAll().stream()
                .filter(b -> lotIds.contains(b.getParkingLotId()))
                .collect(Collectors.toList());
    }
}
