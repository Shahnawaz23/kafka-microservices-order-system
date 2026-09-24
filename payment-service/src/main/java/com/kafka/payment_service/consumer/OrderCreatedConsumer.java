package com.kafka.payment_service.consumer;

import com.kafka.payment_service.event.OrderCreatedEvent;
import com.kafka.payment_service.service.PaymentService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderCreatedConsumer {

    private final PaymentService paymentService;
    private final tools.jackson.databind.ObjectMapper objectMapper;

    public OrderCreatedConsumer(PaymentService paymentService, ObjectMapper objectMapper) {

        this.paymentService = paymentService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "order-created", groupId = "payment-service")
    public void consume(String message) {

        try {

            System.out.println(
                    "Payment Service received: " + message
            );

            OrderCreatedEvent order =
                    objectMapper.readValue(
                            message,
                            OrderCreatedEvent.class
                    );

            paymentService.processPayment(order);

        } catch (Exception e) {

            System.out.println(
                    "Error processing order-created event: "
                            + e.getMessage()
            );
        }
    }
}