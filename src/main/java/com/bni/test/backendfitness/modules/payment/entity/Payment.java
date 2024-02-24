package com.bni.test.backendfitness.modules.payment.entity;

import com.bni.test.backendfitness.base.baseenum.EPaymentStatus;
import com.bni.test.backendfitness.modules.subscription.entity.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    private Subscription subscription;

    private String method;

    private LocalDate date;

    private String otpCode;

    private LocalDateTime expiredOtp;

    @Enumerated(EnumType.STRING)
    private EPaymentStatus status;
}
