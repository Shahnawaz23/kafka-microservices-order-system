package com.kafka.order_service.event;

import com.kafka.order_service.entity.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.ObjectMapper;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {

    private String eventId;
    private String eventType;
    private int orderId;
    private int customerId;
    private double amount;
    private String deliveryAddress;

    public static String createEvent(OrderEntity orderEntity) {

        ObjectMapper objectMapper = new ObjectMapper();

        Event event = new Event("PAY-"+orderEntity.getOrderId(), "ORDER_CREATED", orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getAmount(),orderEntity.getDeliveryAddress());

        return objectMapper.writeValueAsString(event);
    }
}
