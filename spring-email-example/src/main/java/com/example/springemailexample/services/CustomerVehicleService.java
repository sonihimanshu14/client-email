package com.example.springemailexample.services;




import com.example.springemailexample.entity.CustomerVehicleEntity;
import com.example.springemailexample.repository.CustomerVehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerVehicleService {


    private final CustomerVehicleRepository repository;

    public CustomerVehicleService(CustomerVehicleRepository repository) {
        this.repository = repository;
    }

    public List<CustomerVehicleEntity> findAll() {
        return repository.findAll();
    }

    public Optional<CustomerVehicleEntity> findById(Long id) {
        return repository.findById(id);
    }

    public CustomerVehicleEntity save(CustomerVehicleEntity customerVehicle) {
        return repository.save(customerVehicle);
    }

    public CustomerVehicleEntity update(Long id, CustomerVehicleEntity updatedCustomerVehicle) {
        return repository.findById(id).map(vehicle -> {
            vehicle.setVehicleName(updatedCustomerVehicle.getVehicleName());
            vehicle.setCustomerName(updatedCustomerVehicle.getCustomerName());
            vehicle.setRegistrationNumber(updatedCustomerVehicle.getRegistrationNumber());
            return repository.save(vehicle);
        }).orElseThrow(() -> new RuntimeException("CustomerVehicle not found with id " + id));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

