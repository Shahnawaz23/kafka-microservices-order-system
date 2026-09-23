package com.kafka.order_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private int customerId;
    private String customerName;

    private int productId;
    private String productName;

    private int quantity;
    private double amount;

    private String deliveryAddress;
}
