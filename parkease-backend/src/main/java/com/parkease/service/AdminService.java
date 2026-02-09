package com.parkease.service;

import com.parkease.dto.AdminDashboardDTO;
import com.parkease.dto.OwnerDTO;
import com.parkease.dto.OwnerRequestDTO;
import com.parkease.model.Booking;
import com.parkease.model.Owner;
import com.parkease.model.OwnerRequest;
import com.parkease.model.ParkingLot;
import com.parkease.model.User;
import com.parkease.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private OwnerRequestRepository ownerRequestRepository;

    public AdminDashboardDTO getDashboardStats() {
        List<User> users = userRepository.findAll();
        List<Owner> owners = ownerRepository.findAll();
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();
        List<OwnerRequest> requests = ownerRequestRepository.findAll();

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
                activeParkingLots);
    }

    public List<OwnerRequestDTO> getPendingOwnerRequests() {
        return ownerRequestRepository.findByStatus("PENDING").stream()
                .map(OwnerRequestDTO::fromModel)
                .collect(Collectors.toList());
    }

    public List<OwnerRequestDTO> getAllOwnerRequests() {
        return ownerRequestRepository.findAll().stream()
                .map(OwnerRequestDTO::fromModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public String approveOwnerRequest(String requestId) {
        OwnerRequest request = ownerRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("APPROVED");
        ownerRequestRepository.save(request);

        // Create owner account
        Owner owner = new Owner();
        owner.setEmail(request.getEmail());
        owner.setPassword(request.getPassword());
        owner.setName(request.getName());
        owner.setPhone(request.getPhone());
        owner.setBusinessName(request.getBusinessName());
        owner.setApproved(true);
        owner.setActive(true);

        ownerRepository.save(owner);

        return "Owner request approved successfully";
    }

    @Transactional
    public String rejectOwnerRequest(String requestId) {
        OwnerRequest request = ownerRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus("REJECTED");
        ownerRequestRepository.save(request);

        return "Owner request rejected";
    }

    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(o -> new OwnerDTO(o.getId(), o.getEmail(), o.getName(), o.getPhone(),
                        o.getBusinessName(), o.isApproved(), o.isActive()))
                .collect(Collectors.toList());
    }

    @Transactional
    public String toggleParkingLotStatus(String lotId) {
        ParkingLot lot = parkingLotRepository.findById(lotId)
                .orElseThrow(() -> new RuntimeException("Parking lot not found"));

        lot.setActive(!lot.isActive());
        parkingLotRepository.save(lot);

        return "Parking lot status updated";
    }
}
