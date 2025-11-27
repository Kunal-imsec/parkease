package com.parkease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Owner {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String businessName;
    private boolean approved;
    private boolean active;
}
