package com.parkease.dto;

import com.parkease.model.OwnerRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerRequestDTO {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String businessName;
    private String requestDate;
    private String status;

    public static OwnerRequestDTO fromModel(OwnerRequest request) {
        return new OwnerRequestDTO(
                request.getId(), request.getEmail(), request.getName(),
                request.getPhone(), request.getBusinessName(),
                request.getRequestDate() != null ? request.getRequestDate().toString() : null,
                request.getStatus());
    }
}
