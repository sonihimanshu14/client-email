package com.example.springemailexample.services;


import com.example.springemailexample.entity.CustomerVehicleEntity;
import com.example.springemailexample.repository.CustomerVehicleRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerVehicleService {

    @Autowired
    KafkaAdmin kafkaAdmin;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;


    @Autowired
    CustomerVehicleRepository repository;

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
//    public void createKafkaTopic(String topicName) {
//        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
//            NewTopic newTopic = new NewTopic(topicName, 1, (short) 1);
//            adminClient.createTopics(Collections.singleton(newTopic));
//            System.out.println("Kafka topic created: " + topicName);
//        } catch (Exception e) {
//            System.err.println("Failed to create Kafka topic: " + e.getMessage());
//        }
//    }

    public void sendMsgToUserAdmin(long id) throws WriterException, IOException {

        Optional<CustomerVehicleEntity> customerVehicle=  repository.findById(id);
       String custName= customerVehicle.get().getCustomerName();
       String custVehicle= customerVehicle.get().getVehicleName();
        saveQRCodeToFile();

        kafkaTemplate.send("customer_arrived_topic","The "+ custName +" has arrived at the Toll Booth with vehicle : "+custVehicle);
    }

//    public byte[] generateQRCode() throws WriterException, IOException {
//
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
//            return outputStream.toByteArray();
//        }
//    }

    public void saveQRCodeToFile() throws IOException, WriterException {
        String directoryPath = "D:\\Aptitude";
        String fileName = "QRCode.png";
        String text = "Pay Rs 120 at Toll Booth";
        int width = 300;
        int height = 300;


        // Generate the QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        // Create the complete file path
        Path filePath = FileSystems.getDefault().getPath(directoryPath, fileName);

        // Write the QR code to the file
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", filePath);

        System.out.println("QR Code saved at: " + filePath.toString());
    }
}

