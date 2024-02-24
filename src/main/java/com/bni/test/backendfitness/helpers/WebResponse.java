package com.bni.test.backendfitness.helpers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebResponse<T> {
    private T data;
    private String errors;

    public static WebResponse<Object> setValidateRequest(Object data, String message) {
        return WebResponse.builder()
                .errors(message)
                .data(data)
                .build();
    }
}
