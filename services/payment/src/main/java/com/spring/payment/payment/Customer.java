package com.spring.payment.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        Integer id,
        @NotNull(message = "firstname is required")
        String firstName,
        @NotNull(message = "lastname is required")
        String lastName,
        @NotNull(message = "email is required")
        @Email(message = "the customer is not correctly formatted")
        String email
) {
}
