package com.spring.notification.email;

import lombok.Getter;

public enum EmailTemplate {
    PAYMENT_CONFIRMATION("payment-confirmation.html","Payment Sucessfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html","Order Sucessfully processed");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
