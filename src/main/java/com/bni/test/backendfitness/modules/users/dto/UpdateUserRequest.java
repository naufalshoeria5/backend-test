package com.bni.test.backendfitness.modules.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {
    private String name;

    private String cardNumber;

    private String ccv;

    private String expiredDate;

    private String cardHolderName;
}
