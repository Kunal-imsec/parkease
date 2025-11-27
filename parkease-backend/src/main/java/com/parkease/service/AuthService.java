package com.parkease.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.parkease.dto.AuthResponse;
import com.parkease.dto.LoginRequest;
import com.parkease.dto.RegisterRequest;
import com.parkease.model.Owner;
import com.parkease.model.OwnerRequest;
import com.parkease.model.User;
import com.parkease.util.JsonFileUtil;
import com.parkease.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private JsonFileUtil jsonFileUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        if ("ADMIN".equals(request.getRole())) {
            return loginAdmin(request);
        } else if ("OWNER".equals(request.getRole())) {
            return loginOwner(request);
        } else {
            return loginUser(request);
        }
    }

    private AuthResponse loginAdmin(LoginRequest request) {
        // Default admin credentials
        if ("admin@parkease.com".equals(request.getEmail()) && "admin123".equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail(), "ADMIN", "admin-1");
            return new AuthResponse(token, "ADMIN", "admin-1", "Admin", request.getEmail());
        }
        throw new RuntimeException("Invalid admin credentials");
    }

    private AuthResponse loginOwner(LoginRequest request) {
        List<Owner> owners = jsonFileUtil.readList("owners.json", new TypeReference<List<Owner>>() {
        });
        Owner owner = owners.stream()
                .filter(o -> o.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (!owner.isApproved()) {
            throw new RuntimeException("Owner account not approved yet");
        }

        if (!owner.isActive()) {
            throw new RuntimeException("Owner account is inactive");
        }

        // Support both plain text (for demo) and BCrypt passwords
        boolean passwordMatches = owner.getPassword().startsWith("$2a$")
                ? passwordEncoder.matches(request.getPassword(), owner.getPassword())
                : owner.getPassword().equals(request.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(owner.getEmail(), "OWNER", owner.getId());
        return new AuthResponse(token, "OWNER", owner.getId(), owner.getName(), owner.getEmail());
    }

    private AuthResponse loginUser(LoginRequest request) {
        List<User> users = jsonFileUtil.readList("users.json", new TypeReference<List<User>>() {
        });

        System.out.println("=== LOGIN ATTEMPT ===");
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password received: [" + request.getPassword() + "]");
        System.out.println("Total users in DB: " + users.size());

        User user = users.stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("User found: " + user.getName());
        System.out.println("User password in DB: [" + user.getPassword() + "]");
        System.out.println("User active: " + user.isActive());

        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        // Support both plain text (for demo) and BCrypt passwords
        boolean passwordMatches = user.getPassword().startsWith("$2a$")
                ? passwordEncoder.matches(request.getPassword(), user.getPassword())
                : user.getPassword().equals(request.getPassword());

        System.out.println("Password matches: " + passwordMatches);
        System.out.println("Is BCrypt: " + user.getPassword().startsWith("$2a$"));

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), "USER", user.getId());
        return new AuthResponse(token, "USER", user.getId(), user.getName(), user.getEmail());
    }

    public String registerUser(RegisterRequest request) {
        List<User> users = jsonFileUtil.readList("users.json", new TypeReference<List<User>>() {
        });

        boolean exists = users.stream().anyMatch(u -> u.getEmail().equals(request.getEmail()));
        if (exists) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole("USER");
        user.setActive(true);

        users.add(user);
        jsonFileUtil.writeList("users.json", users);

        return "User registered successfully";
    }

    public String registerOwner(RegisterRequest request) {
        List<OwnerRequest> requests = jsonFileUtil.readList("ownerRequests.json",
                new TypeReference<List<OwnerRequest>>() {
                });

        boolean exists = requests.stream().anyMatch(r -> r.getEmail().equals(request.getEmail()));
        if (exists) {
            throw new RuntimeException("Owner request already submitted");
        }

        OwnerRequest ownerRequest = new OwnerRequest();
        ownerRequest.setId(UUID.randomUUID().toString());
        ownerRequest.setEmail(request.getEmail());
        ownerRequest.setPassword(passwordEncoder.encode(request.getPassword()));
        ownerRequest.setName(request.getName());
        ownerRequest.setPhone(request.getPhone());
        ownerRequest.setBusinessName(request.getBusinessName());
        ownerRequest.setRequestDate(LocalDateTime.now().toString());
        ownerRequest.setStatus("PENDING");

        requests.add(ownerRequest);
        jsonFileUtil.writeList("ownerRequests.json", requests);

        return "Owner registration request submitted for approval";
    }
}
