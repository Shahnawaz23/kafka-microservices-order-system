package com.kafka.delivery_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryId;

    private Integer orderId;
    private Integer customerId;
    private String trackingNumber;
    private String deliveryAddress;
    private String deliveryStatus;

}
