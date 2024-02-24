package com.bni.test.backendfitness.modules.menu.controller;

import com.bni.test.backendfitness.helpers.WebResponse;
import com.bni.test.backendfitness.modules.menu.dto.MenuCreateRequest;
import com.bni.test.backendfitness.modules.menu.dto.MenuResponse;
import com.bni.test.backendfitness.modules.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public WebResponse<List<MenuResponse>> get(){
        List<MenuResponse> menuResponses = menuService.get();

        return WebResponse.<List<MenuResponse>>builder()
                .data(menuResponses)
                .build();
    }

    @PostMapping
    public WebResponse<String> createData(@RequestBody MenuCreateRequest request){
        menuService.create(request);

        return WebResponse.<String>builder().data("OK").build();
    }
}
