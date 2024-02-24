package com.bni.test.backendfitness.modules.menu.service;

import com.bni.test.backendfitness.modules.menu.dto.MenuCreateRequest;
import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import com.bni.test.backendfitness.modules.menu.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;


    @Test
    void createMenu(){
        MenuCreateRequest request = new MenuCreateRequest();

        request.setName("Paket 1");
        request.setDescription("Layanan Kebugaran Untuk Layanan Overweight");
        request.setPrice(1_00_000L);
        request.setMenuDuration(10);

        menuService.create(request);

        List<Menu> all = menuRepository.findAll();

        assertFalse(all.isEmpty());
    }

}