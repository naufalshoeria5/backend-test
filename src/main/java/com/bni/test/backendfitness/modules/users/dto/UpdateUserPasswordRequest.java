package com.bni.test.backendfitness.modules.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserPasswordRequest {

    private String id;

    private String passwordOld;

    private String passwordNew;
}
