package com.spring.order.customer;

public record CustomerResponse(
        String id,
        String firstName,
        String lastname,
        String email

) {
}
