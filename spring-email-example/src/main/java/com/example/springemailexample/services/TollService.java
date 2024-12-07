package com.example.springemailexample.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toll")
public class TollService {


        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        private static final String TOPIC = "toll-events";

        @PostMapping("/vehicle")
        public String tollEvent(@RequestBody String vehicleInfo) {
            // Send the vehicle info to Kafka
            kafkaTemplate.send(TOPIC, vehicleInfo);
            return "Vehicle event sent to Kafka!";
        }
    }



