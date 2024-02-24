package com.bni.test.backendfitness.modules.users.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String id;

    private String name;

    private String email;

    private String phoneNumber;

    private String cardNumber;

    private String ccv;

    private String expiredDate;

    private String cardHolderName;

    private String otpCode;

    private String status;
}
