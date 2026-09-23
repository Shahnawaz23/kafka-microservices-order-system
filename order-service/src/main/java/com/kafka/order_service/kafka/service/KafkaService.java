package com.kafka.order_service.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final KafkaTemplate<Integer , String> kafkaTemplate;

    public KafkaService(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, int key, String message) {
        System.out.println("KafkaService.sendMessage... sending message: " +message);
        kafkaTemplate.send(topic, key, message).whenComplete((result, e) -> {
            if(e == null) {
                System.out.println("Message sent successfully: "
                                + "Topic: " + result.getRecordMetadata().topic()
                                + ", Partion: " + result.getRecordMetadata().partition()
                                + ", Offset: " + result.getRecordMetadata().offset());
            } else {
                System.out.println("Message sent failed: " + e.getMessage());
            }
        });
    }
}
