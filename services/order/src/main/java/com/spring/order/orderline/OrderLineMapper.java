package com.spring.order.orderline;

import com.spring.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .quantity(request.quantity())
                .order(
                        Order.builder()
                                .id(request.id())
                                .build()
                )
                .productId(request.productId())
                .build();
    }

    public OrderlineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderlineResponse(
                orderLine.getId(),
                orderLine.getQuantity());
    }
}
