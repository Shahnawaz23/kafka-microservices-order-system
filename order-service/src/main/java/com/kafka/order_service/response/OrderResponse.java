package com.kafka.order_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {

    int orderId;
    String orderStatus;
}
