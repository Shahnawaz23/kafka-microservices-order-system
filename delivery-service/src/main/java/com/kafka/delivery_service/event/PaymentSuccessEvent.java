package com.kafka.delivery_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSuccessEvent {

    private String eventId;
    private String eventType;
    private int orderId;
    private int customerId;
    private double amount;
    private String paymentId;
    private String paymentMethod;
    private String paymentStatus;
    private String deliveryAddress;
}