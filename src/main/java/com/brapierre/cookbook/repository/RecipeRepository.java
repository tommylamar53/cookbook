package com.brapierre.cookbook.repository;

import com.brapierre.cookbook.entity.Recipe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByMealPlanId(Long mealPlanId);
}
