package com.example.springemailexample.repository;

import com.example.springemailexample.entity.CustomerVehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerVehicleRepository  extends JpaRepository<CustomerVehicleEntity, Long> {
}

