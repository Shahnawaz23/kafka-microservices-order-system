package com.kafka.order_service.controller;

import com.kafka.order_service.entity.OrderEntity;
import com.kafka.order_service.response.OrderResponse;
import com.kafka.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("create-order")
    public OrderResponse createOrder(@RequestBody OrderEntity orderEntity) {

        OrderResponse orderResponse = orderService.createOrder(orderEntity);

        System.out.println("orderId: " + orderEntity.getOrderId());
        System.out.println("customerId = " + orderEntity.getCustomerId());
        System.out.println("customerName = " + orderEntity.getCustomerName());
        System.out.println("productId = " + orderEntity.getProductId());
        System.out.println("productName = " + orderEntity.getProductName());
        System.out.println("quantity = " + orderEntity.getQuantity());
        System.out.println("amount = " + orderEntity.getAmount());
        System.out.println("deliveryAddress = " + orderEntity.getDeliveryAddress());
        return orderResponse;
    }
}
