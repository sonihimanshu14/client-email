package com.example.springemailexample.controller;

import com.example.springemailexample.entity.CustomerVehicleEntity;
import com.example.springemailexample.services.CustomerVehicleService;
import com.example.springemailexample.services.OcrService;
import com.google.zxing.WriterException;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer-vehicles")
public class CustomerVehicleController {

    @Autowired
     CustomerVehicleService service;

    @Autowired
    OcrService ocrService;

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

    @GetMapping("/producersMsg")
    public ResponseEntity<String> getmessageFromClient(@RequestParam("id")long id) throws IOException, WriterException {
        service.sendMsgToUserAdmin(id);
        return ResponseEntity.ok("Payment Qr Generated");

    }

    @GetMapping("/producersPay")
    public ResponseEntity<String> getPaymentMessageFromClient(@RequestParam("id")long id,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam("payment") String payment

    ) throws IOException, WriterException, TesseractException {
        service.sendPaymentMsgToUserAdmin(id,file,payment);
        return ResponseEntity.ok("Success");

    }

//    @PostMapping("/extract")
//    public ResponseEntity<String> extractText(@RequestParam("file") MultipartFile file) {
//        try {
//            // Save the file to a temporary location
//
//
//            // Perform OCR
//            String extractedText = ocrService.extractTextFromImage(file);
//
//            // Delete the temporary file
//
//
//            return ResponseEntity.ok(extractedText);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error processing file: " + e.getMessage());
//        }
//    }


}