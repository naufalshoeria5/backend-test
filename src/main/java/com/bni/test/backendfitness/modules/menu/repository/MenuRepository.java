package com.bni.test.backendfitness.modules.menu.repository;

import com.bni.test.backendfitness.modules.menu.enitity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {
}
