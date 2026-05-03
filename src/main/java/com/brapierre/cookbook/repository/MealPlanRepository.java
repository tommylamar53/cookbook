package com.brapierre.cookbook.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.brapierre.cookbook.entity.MealPlan;


public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    List<MealPlan> findByUserEmail(String email);

    Optional<MealPlan> findByIdAndUserEmail(Long id, String email);
}

