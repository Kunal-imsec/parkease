package com.parkease.repository;

import com.parkease.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {
    List<ParkingLot> findByOwnerId(String ownerId);

    List<ParkingLot> findByActiveTrueAndAvailabilityAndAvailableSlotsGreaterThan(
            String availability, int slots);

    List<ParkingLot> findByCityContainingIgnoreCaseAndActiveTrueAndAvailability(
            String city, String availability);

    List<ParkingLot> findByAreaContainingIgnoreCaseAndActiveTrueAndAvailability(
            String area, String availability);
}
