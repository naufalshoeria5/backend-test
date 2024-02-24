package com.bni.test.backendfitness.modules.activity.service;

import com.bni.test.backendfitness.modules.activity.dto.ActivityResponse;
import com.bni.test.backendfitness.modules.activity.dto.CreateActivityRequest;
import com.bni.test.backendfitness.modules.activity.entity.Activity;
import com.bni.test.backendfitness.modules.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public List<ActivityResponse> get(){
        List<Activity> all = activityRepository.findAll();

        return all.stream().map(this::toActivityResponse).toList();
    }
    public void createData(CreateActivityRequest request){
        Activity activity = new Activity();
        activity.setId(UUID.randomUUID().toString());
        activity.setName(request.getName());

        activityRepository.save(activity);
    }

    private ActivityResponse toActivityResponse(Activity activity){
        return ActivityResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .build();
    }
}
