package com.bni.test.backendfitness.modules.menuactivity.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.menuactivity.dto.CreateMenuActivityRequest;
import com.bni.test.backendfitness.modules.menuactivity.service.MenuActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu-activity")
@RequiredArgsConstructor
public class MenuActivityController {

    private final MenuActivityService menuActivityService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(@RequestBody CreateMenuActivityRequest request){
        menuActivityService.createData(request);

        return WebResponse.<String>builder().data("OK").build();
    }
}
