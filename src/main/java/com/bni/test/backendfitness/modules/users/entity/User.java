package com.bni.test.backendfitness.modules.users.entity;

import com.bni.test.backendfitness.base.baseenum.EStatus;
import com.bni.test.backendfitness.modules.subscription.entity.Subscription;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    @Size(max = 100)
    @Email
    private String email;

    @NotNull
    @Size(max = 100)
    private String password;

    @Size(max = 100)
    private String phoneNumber;

    private String cardNumber;

    private String ccv;

    private String expiredDate;

    private String cardHolderName;

    private String otpCode;

    private LocalDateTime otpExpiredAt;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscription;
}
