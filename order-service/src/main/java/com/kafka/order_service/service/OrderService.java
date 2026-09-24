package com.kafka.order_service.service;

import com.kafka.order_service.entity.OrderEntity;
import com.kafka.order_service.event.Event;
import com.kafka.order_service.kafka.service.KafkaService;
import com.kafka.order_service.repository.OrderRepository;
import com.kafka.order_service.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    KafkaService kafkaService;

    public OrderResponse createOrder(OrderEntity orderEntity) {

        OrderEntity orderEntityResponse = orderRepository.save(orderEntity);
        System.out.println("OrderService.createOrder order created. " + orderEntity.getOrderId());
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderEntityResponse.getOrderId());
        orderResponse.setOrderStatus("CREATED");

        String message = Event.createEvent(orderEntityResponse);

        kafkaService.sendMessage("", orderResponse.getOrderId(), message);

        return orderResponse;
    }


//    private String event(OrderEntity orderEntity) {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String event = objectMapper.writeValueAsString(orderEntity);
//    }
}
