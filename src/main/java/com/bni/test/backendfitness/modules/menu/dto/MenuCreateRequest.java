package com.bni.test.backendfitness.modules.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;

    @NotBlank
    private Integer numberOfMeeting;
}
