package com.bni.test.backendfitness.modules.users.entity;

import com.bni.test.backendfitness.base.baseEnum.EStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @Enumerated(EnumType.STRING)
    private EStatus status;
}
