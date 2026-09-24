package com.kafka.payment_service.service;

import com.kafka.payment_service.entity.PaymentEntity;
import com.kafka.payment_service.event.OrderCreatedEvent;
import com.kafka.payment_service.event.PaymentEvent;
import com.kafka.payment_service.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private  KafkaService kafkaService;
    @Autowired
    private  ObjectMapper objectMapper;

    public void processPayment(OrderCreatedEvent order) {

        System.out.println("PaymentService received order: " + order.getOrderId());

        boolean paymentSuccessful = order.getAmount() > 50000;

        String paymentId = "TXN-" + order.getOrderId();

        PaymentEntity payment = new PaymentEntity();

        payment.setOrderId(order.getOrderId());
        payment.setCustomerId(order.getCustomerId());
        payment.setAmount(order.getAmount());
        payment.setPaymentId(paymentId);
        payment.setPaymentMethod("UPI");

        PaymentEvent paymentEvent;

        if (paymentSuccessful) {

            payment.setPaymentStatus("SUCCESS");
            payment.setReason(null);

            paymentRepository.save(payment);

            paymentEvent = new PaymentEvent(
                    "PAY-" + order.getOrderId(),
                    "PAYMENT_SUCCESS",
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getAmount(),
                    paymentId,
                    "UPI",
                    "SUCCESS",
                    order.getDeliveryAddress(),
                    null
            );

            publishPaymentEvent("payment-success", order.getOrderId(), paymentEvent);

        } else {

            payment.setPaymentStatus("FAILED");
            payment.setReason("INSUFFICIENT_FUNDS");

            paymentRepository.save(payment);

            paymentEvent = new PaymentEvent(
                    "PAY-" + order.getOrderId(),
                    "PAYMENT_FAILED",
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getAmount(),
                    paymentId,
                    "UPI",
                    "FAILED",
                    order.getDeliveryAddress(),
                    "INSUFFICIENT_FUNDS"
            );

            publishPaymentEvent(
                    "payment-failed",
                    order.getOrderId(),
                    paymentEvent
            );
        }
    }

    private void publishPaymentEvent(String topic, int key, PaymentEvent paymentEvent) {

            String message = objectMapper.writeValueAsString(paymentEvent);

            kafkaService.sendMessage(topic, key, message);
    }
}