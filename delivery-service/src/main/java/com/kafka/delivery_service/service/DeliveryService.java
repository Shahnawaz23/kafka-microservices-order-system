package com.kafka.delivery_service.service;

import com.kafka.delivery_service.entity.DeliveryEntity;
import com.kafka.delivery_service.event.DeliveryEvent;
import com.kafka.delivery_service.event.PaymentSuccessEvent;
import com.kafka.delivery_service.kafka.service.KafkaService;
import com.kafka.delivery_service.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    KafkaService kafkaService;

    @Autowired
    ObjectMapper objectMapper;

    public void createDelivery(PaymentSuccessEvent paymentEvent) {

        System.out.println("DeliveryService Creating Delivery for " + paymentEvent.getOrderId());

        String trackingNumber = "TRK-" + paymentEvent.getOrderId();

        DeliveryEntity deliveryEntity = new DeliveryEntity();

        deliveryEntity.setOrderId(paymentEvent.getOrderId());

        deliveryEntity.setCustomerId(paymentEvent.getCustomerId());

        deliveryEntity.setTrackingNumber(trackingNumber);

        deliveryEntity.setDeliveryAddress(paymentEvent.getDeliveryAddress());

        deliveryEntity.setDeliveryStatus("CREATED");

        //save delivery
        deliveryRepository.save(deliveryEntity);

        DeliveryEvent deliveryEvent = new DeliveryEvent(
                                    "DEL-" + paymentEvent.getOrderId(),
                                    "DELIVERY_CREATED",
                paymentEvent.getOrderId(),
                paymentEvent.getCustomerId(),
                trackingNumber,
                paymentEvent.getDeliveryAddress(),
                "CREATED"
        );

        publishEvent(paymentEvent.getOrderId(), deliveryEvent);
    }

    public void publishEvent(int orderId, DeliveryEvent deliveryEvent) {

        String message = objectMapper.writeValueAsString(deliveryEvent);

        System.out.println("DeliveryService Publishing Event: " + message);

        kafkaService.sendMessage("delivery-created", orderId, message);
    }
}
