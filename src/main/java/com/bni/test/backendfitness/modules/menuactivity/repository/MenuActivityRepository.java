package com.bni.test.backendfitness.modules.menuactivity.repository;

import com.bni.test.backendfitness.modules.menuactivity.entity.MenuActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuActivityRepository extends JpaRepository<MenuActivity, String> {


}
