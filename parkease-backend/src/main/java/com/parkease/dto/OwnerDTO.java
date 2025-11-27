package com.parkease.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDTO {
    private String id;
    private String email;
    private String name;
    private String phone;
    private String businessName;
    private boolean approved;
    private boolean active;
}
