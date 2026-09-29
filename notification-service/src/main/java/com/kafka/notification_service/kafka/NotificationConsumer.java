package com.kafka.notification_service.kafka;

import com.kafka.notification_service.event.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import tools.jackson.databind.ObjectMapper;


public class NotificationConsumer {

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = {"order-created", "payment-success", "payment-failed", "delivery-created"}, groupId = "notification-service")
    public void consume(String message) {

        System.out.println("NotificationConsumer.consume... " + message);

        NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);

        sendNotification(event);
    }


    public void sendNotification(NotificationEvent event) {

        switch (event.getEventType()) {
            case "ORDER_CREATED":
                System.out.println(
                        "SMS SENT\n" +
                        "Customer: " + event.getCustomerId() + "\n" +
                        "Order: " + event.getOrderId() + "\n" +
                        "Your order has been created successfully."
                );
                break;
            case "PAYMENT_SUCCESS":
                System.out.println(
                                "SMS SENT\n" +
                                "Customer: " + event.getCustomerId() + "\n" +
                                "Order: " + event.getOrderId() + "\n" +
                                "Your payment of Rs." +
                                event.getAmount() +
                                " was successful."
                        );
                break;

            case "PAYMENT_FAILED":
                System.out.println(
                                "SMS SENT\n" +
                                "Customer: " + event.getCustomerId() + "\n" +
                                "Order: " + event.getOrderId() + "\n" +
                                "Your payment failed."
                );
                break;

            case "DELIVERY_CREATED":
                System.out.println(
                        "SMS SENT\n" +
                                "Customer: " + event.getCustomerId() + "\n" +
                                "Order: " + event.getOrderId() + "\n" +
                                "Your delivery has been created."
                );

                break;

            default:
                System.out.println(
                        "No notification configured for event: "
                                + event.getEventType()
                );


        }
    }

}
