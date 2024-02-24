package com.bni.test.backendfitness.modules.activity.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.activity.dto.ActivityResponse;
import com.bni.test.backendfitness.modules.activity.dto.CreateActivityRequest;
import com.bni.test.backendfitness.modules.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<ActivityResponse>> get(){
        List<ActivityResponse> activityResponses = activityService.get();

        return WebResponse.<List<ActivityResponse>>builder()
                .data(activityResponses)
                .build();
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public void create(@RequestBody CreateActivityRequest request){
        activityService.createData(request);
    }
}
