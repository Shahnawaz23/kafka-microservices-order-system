package com.kafka.delivery_service.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessage(String topic, int key, String message) {

        System.out.println("KafkaService.sendMessage...sending message to Kafka " + message);

        kafkaTemplate.send(topic, key, message);
    }
}
