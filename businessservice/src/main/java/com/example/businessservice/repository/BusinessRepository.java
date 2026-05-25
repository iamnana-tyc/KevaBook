package com.example.businessservice.repository;

import com.example.businessservice.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}
