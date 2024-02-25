package com.bni.test.backendfitness.modules.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPaymentResponse {

    private String menuName;

    private Long totalPayment;

    private String paymentCode;
}
