package com.parkease.repository;

import com.parkease.model.OwnerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRequestRepository extends JpaRepository<OwnerRequest, String> {
    List<OwnerRequest> findByStatus(String status);

    boolean existsByEmail(String email);
}
