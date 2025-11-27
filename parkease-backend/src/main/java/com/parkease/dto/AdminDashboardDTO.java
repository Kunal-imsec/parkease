package com.parkease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    private long totalUsers;
    private long totalOwners;
    private long totalParkingLots;
    private long totalBookings;
    private long pendingOwnerRequests;
    private long activeOwners;
    private long activeParkingLots;
}
