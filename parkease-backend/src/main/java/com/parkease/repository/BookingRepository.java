package com.parkease.repository;

import com.parkease.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByUserId(String userId);

    List<Booking> findByParkingLotId(String parkingLotId);

    List<Booking> findByStatus(String status);

    boolean existsByUserIdAndStatus(String userId, String status);
}
