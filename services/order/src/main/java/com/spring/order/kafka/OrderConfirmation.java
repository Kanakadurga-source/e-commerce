package com.spring.order.kafka;

import com.spring.order.customer.CustomerResponse;
import com.spring.order.order.PaymentMethod;
import com.spring.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
