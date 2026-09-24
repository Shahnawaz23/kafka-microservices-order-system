package com.kafka.payment_service.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public KafkaService(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, int key, String message) {

        System.out.println("KafkaService.sendMessage...sending message: " + message);

        kafkaTemplate.send(topic, key, message);
    }
}
