package com.bni.test.backendfitness.modules.menuactivity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMenuActivityRequest {

    @NotBlank
    private Long price;

    @NotBlank
    private Long duration;

    @NotBlank
    private Long numberOfMeeting;

    @NotBlank
    private String menuId;

    @NotBlank
    private String activityId;

}
