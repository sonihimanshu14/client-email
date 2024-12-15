package com.example.springemailexample.services;
import com.example.springemailexample.entity.UserAdmin;
import com.example.springemailexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdminService {

    @Autowired
   UserRepository userRepository;

    @Autowired
     PasswordEncoder passwordEncoder;

    public UserAdmin createUser(UserAdmin user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<UserAdmin> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserAdmin> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public UserAdmin updateUser(Long id, UserAdmin userDetails) {
        UserAdmin user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setEmail(userDetails.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

//    @KafkaListener(topics = "customer_arrived", groupId = "admin-group")
//    public void listenToCustomerArrived(String message) {
//        System.out.println("Admin received message from Toll Booth: " + message);
//        // Perform any additional admin-related processing here
//    }


    @KafkaListener(topics = "customer_arrived_topic", groupId = "vehicles_group")
    public void listenToTopic(String recmessage){System.out.println("The Vehicle Arrived :  " + recmessage);}

    @KafkaListener(topics = "payment_received_topic", groupId = "payment_group")
    public void listenToPaymentTopic(String recmessage){System.out.println("The Payment Received :  " + recmessage);}



}

