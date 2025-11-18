package com.etiya.common.events.billingaccount;

public record SoftDeleteBillingAccountEvent(
        String customerId,
        int id,
        String deletedDate
) {

}
