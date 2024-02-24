package com.bni.test.backendfitness.modules.menu.dto;

import com.bni.test.backendfitness.modules.menuactivity.dto.MenuActivityResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResponse {

    private String id;

    private String name;

    private String description;

    private Integer numberOfMeeting;

    private Long total;

    private List<MenuActivityResponse> menuActivityResponse;
}
