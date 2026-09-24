package com.kafka.delivery_service.kafka.consumer;

import com.kafka.delivery_service.event.PaymentSuccessEvent;
import com.kafka.delivery_service.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PaymentSuccessConsumer {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "payment-success", groupId = "delivery-service")
    public void consume(String message) {

        System.out.println("PaymentSuccessConsumer.consume.." + message);

        PaymentSuccessEvent paymentEvent = objectMapper.readValue(message, PaymentSuccessEvent.class);

        deliveryService.createDelivery(paymentEvent);
    }
}
