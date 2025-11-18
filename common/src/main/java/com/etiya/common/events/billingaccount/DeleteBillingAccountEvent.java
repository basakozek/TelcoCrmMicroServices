package com.etiya.common.events.billingaccount;

public record DeleteBillingAccountEvent(
        String customerId,
        int billingAccountId
) {
}
