package com.bni.test.backendfitness.modules.menu.service;

import com.bni.test.backendfitness.modules.activity.dto.ActivityResponse;
import com.bni.test.backendfitness.modules.menu.dto.MenuCreateRequest;
import com.bni.test.backendfitness.modules.menu.dto.MenuResponse;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import com.bni.test.backendfitness.modules.menu.repository.MenuRepository;
import com.bni.test.backendfitness.modules.menuactivity.dto.MenuActivityResponse;
import com.bni.test.backendfitness.modules.menuactivity.entity.MenuActivity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    public List<MenuResponse> get(){
        List<Menu> all = menuRepository.findAll();

        return all.stream().map(this::toMenuResponse).toList();
    }

    @Transactional
    public void create(MenuCreateRequest request){
        Menu menu = new Menu();

        menu.setId(UUID.randomUUID().toString());
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setNumberOfMeeting(request.getNumberOfMeeting());
        menuRepository.save(menu);
    }

    private MenuResponse toMenuResponse(Menu menu){
        List<MenuActivityResponse> list = menu.getMenuActivity().stream().map(this::toMenuActivityResponse).toList();

        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .description(menu.getDescription())
                .numberOfMeeting(menu.getNumberOfMeeting())
                .menuActivityResponse(list)
                .total(menu.getTotal())
                .build();
    }

    private MenuActivityResponse toMenuActivityResponse(MenuActivity menuActivity){
        ActivityResponse activity = ActivityResponse.builder().id(menuActivity.getActivity().getId()).name(menuActivity.getActivity().getName()).build();
        return MenuActivityResponse.builder()
                .id(menuActivity.getId())
                .price(menuActivity.getPrice())
                .numberOfMeeting(menuActivity.getNumberOfMeeting())
                .duration(menuActivity.getDuration())
                .activity(activity)
                .build();
    }
}
