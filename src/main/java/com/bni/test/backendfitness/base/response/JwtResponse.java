package com.bni.test.backendfitness.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String id;
    private String email;
    private String token;
    private String type = "Bearer";
    public JwtResponse(String accessToken, String id, String email) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
    }
}
