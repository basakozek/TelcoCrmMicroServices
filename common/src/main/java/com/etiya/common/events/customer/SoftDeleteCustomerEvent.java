package com.etiya.common.events.customer;

public record SoftDeleteCustomerEvent(
        String customerId,
        String deletedDate // Silinme tarihini String olarak gönderelim
) {
}
