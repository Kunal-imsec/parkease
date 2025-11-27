package com.parkease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequest {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String businessName;
    private String requestDate;
    private String status; // PENDING, APPROVED, REJECTED
}
