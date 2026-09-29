package com.kafka.order_service.service;

import com.kafka.order_service.entity.OrderEntity;
import com.kafka.order_service.event.Event;
import com.kafka.order_service.kafka.service.KafkaService;
import com.kafka.order_service.repository.OrderRepository;
import com.kafka.order_service.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KafkaService kafkaService;

    public OrderResponse createOrder(OrderEntity orderEntity) {

        OrderEntity orderEntityResponse = orderRepository.save(orderEntity);
        System.out.println("OrderService.createOrder order created. " + orderEntity.getOrderId());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderEntityResponse.getOrderId());
        orderResponse.setOrderStatus("CREATED");

        Event event = new Event("PAY-"+orderEntity.getOrderId(), "ORDER_CREATED", orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getAmount(),orderEntity.getDeliveryAddress());

        publishEvent("order-created", orderEntity.getOrderId(), event);;

        return orderResponse;
    }

    public void publishEvent(String topic, int key, Event event) {

        String message = objectMapper.writeValueAsString(event);

        kafkaService.sendMessage(topic, key, message);
    }
//    private String event(OrderEntity orderEntity) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String event = objectMapper.writeValueAsString(orderEntity);
//    }
}
