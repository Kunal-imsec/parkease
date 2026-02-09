package com.parkease.service;

import com.parkease.dto.AuthResponse;
import com.parkease.dto.LoginRequest;
import com.parkease.dto.RegisterRequest;
import com.parkease.model.Owner;
import com.parkease.model.OwnerRequest;
import com.parkease.model.User;
import com.parkease.repository.OwnerRepository;
import com.parkease.repository.OwnerRequestRepository;
import com.parkease.repository.UserRepository;
import com.parkease.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerRequestRepository ownerRequestRepository;

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
        Owner owner = ownerRepository.findByEmail(request.getEmail())
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
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isActive()) {
            throw new RuntimeException("User account is inactive");
        }

        // Support both plain text (for demo) and BCrypt passwords
        boolean passwordMatches = user.getPassword().startsWith("$2a$")
                ? passwordEncoder.matches(request.getPassword(), user.getPassword())
                : user.getPassword().equals(request.getPassword());

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), "USER", user.getId());
        return new AuthResponse(token, "USER", user.getId(), user.getName(), user.getEmail());
    }

    @Transactional
    public String registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setRole("USER");
        user.setActive(true);

        userRepository.save(user);

        return "User registered successfully";
    }

    @Transactional
    public String registerOwner(RegisterRequest request) {
        if (ownerRequestRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Owner request already submitted");
        }

        OwnerRequest ownerRequest = new OwnerRequest();
        ownerRequest.setEmail(request.getEmail());
        ownerRequest.setPassword(passwordEncoder.encode(request.getPassword()));
        ownerRequest.setName(request.getName());
        ownerRequest.setPhone(request.getPhone());
        ownerRequest.setBusinessName(request.getBusinessName());
        ownerRequest.setRequestDate(LocalDateTime.now());
        ownerRequest.setStatus("PENDING");

        ownerRequestRepository.save(ownerRequest);

        return "Owner registration request submitted for approval";
    }
}
