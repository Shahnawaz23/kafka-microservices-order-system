package com.kafka.delivery_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEvent {

    private String eventId;
    private String eventType;
    private int orderId;
    private int customerId;
    private String trackingNumber;
    private String deliveryAddress;
    private String deliveryStatus;
}