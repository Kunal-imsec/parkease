package com.parkease.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String role; // USER, OWNER, ADMIN
    private boolean active;
}
