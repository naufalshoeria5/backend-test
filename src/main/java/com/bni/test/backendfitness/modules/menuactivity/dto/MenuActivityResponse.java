package com.bni.test.backendfitness.modules.menuactivity.dto;

import com.bni.test.backendfitness.modules.activity.dto.ActivityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuActivityResponse {
    private String id;

    private Long price;

    private Long duration;

    private Long numberOfMeeting;

    private ActivityResponse activity;
}
