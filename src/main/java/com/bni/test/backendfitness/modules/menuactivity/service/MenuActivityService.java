package com.bni.test.backendfitness.modules.menuactivity.service;

import com.bni.test.backendfitness.modules.activity.entity.Activity;
import com.bni.test.backendfitness.modules.activity.repository.ActivityRepository;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import com.bni.test.backendfitness.modules.menu.repository.MenuRepository;
import com.bni.test.backendfitness.modules.menuactivity.dto.CreateMenuActivityRequest;
import com.bni.test.backendfitness.modules.menuactivity.entity.MenuActivity;
import com.bni.test.backendfitness.modules.menuactivity.repository.MenuActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MenuActivityService {
    private final MenuActivityRepository menuActivityRepository;
    private final MenuRepository menuRepository;
    private final ActivityRepository activityRepository;

    @Transactional
    public void createData(CreateMenuActivityRequest request){
        Menu menu = menuRepository.findById(request.getMenuId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Not Found")
        );
        Activity activity = activityRepository.findById(request.getActivityId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu Not Found")
        );

        boolean b = Objects.nonNull(menu.getTotal());
        menu.setTotal((b ? menu.getTotal() : 0) + request.getPrice());

        MenuActivity menuActivity = new MenuActivity();
        menuActivity.setId(UUID.randomUUID().toString());
        menuActivity.setPrice(request.getPrice());
        menuActivity.setDuration(request.getDuration());
        menuActivity.setNumberOfMeeting(request.getNumberOfMeeting());
        menuActivity.setMenu(menu);
        menuActivity.setActivity(activity);
        menuActivityRepository.save(menuActivity);
    }
}
