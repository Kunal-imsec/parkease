package com.parkease.service;

import com.parkease.dto.ParkingLotDTO;
import com.parkease.model.ParkingLot;
import com.parkease.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ParkingLotService {

        @Autowired
        private ParkingLotRepository parkingLotRepository;

        public List<ParkingLotDTO> getAllAvailableParkingLots() {
                return parkingLotRepository.findByActiveTrueAndAvailabilityAndAvailableSlotsGreaterThan("AVAILABLE", 0)
                                .stream()
                                .map(ParkingLotDTO::fromModel)
                                .collect(Collectors.toList());
        }

        public List<ParkingLotDTO> getAllParkingLots() {
                return parkingLotRepository.findAll().stream()
                                .map(ParkingLotDTO::fromModel)
                                .collect(Collectors.toList());
        }

        public ParkingLotDTO getParkingLotById(String id) {
                ParkingLot lot = parkingLotRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

                return ParkingLotDTO.fromModel(lot);
        }

        public List<ParkingLotDTO> searchByCity(String city) {
                return parkingLotRepository
                                .findByCityContainingIgnoreCaseAndActiveTrueAndAvailability(city, "AVAILABLE").stream()
                                .map(ParkingLotDTO::fromModel)
                                .collect(Collectors.toList());
        }

        public List<ParkingLotDTO> searchByArea(String area) {
                return parkingLotRepository
                                .findByAreaContainingIgnoreCaseAndActiveTrueAndAvailability(area, "AVAILABLE").stream()
                                .map(ParkingLotDTO::fromModel)
                                .collect(Collectors.toList());
        }
}
