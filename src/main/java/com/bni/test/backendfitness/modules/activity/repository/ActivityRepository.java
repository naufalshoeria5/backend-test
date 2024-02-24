package com.bni.test.backendfitness.modules.activity.repository;

import com.bni.test.backendfitness.modules.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {
}
