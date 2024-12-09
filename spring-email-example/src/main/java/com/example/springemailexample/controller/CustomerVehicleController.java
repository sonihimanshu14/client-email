package com.example.springemailexample.controller;

import com.example.springemailexample.entity.CustomerVehicleEntity;
import com.example.springemailexample.services.CustomerVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer-vehicles")
public class CustomerVehicleController {

    private final CustomerVehicleService service;

    public CustomerVehicleController(CustomerVehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerVehicleEntity> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerVehicleEntity> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CustomerVehicleEntity create(@RequestBody CustomerVehicleEntity customerVehicle) {
        return service.save(customerVehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerVehicleEntity> update(@PathVariable Long id, @RequestBody CustomerVehicleEntity customerVehicle) {
        try {
            return ResponseEntity.ok(service.update(id, customerVehicle));
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

