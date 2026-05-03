package com.brapierre.cookbook.controller;

import com.brapierre.cookbook.entity.MealPlan;
import com.brapierre.cookbook.service.MealPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Hidden;


import java.util.List;

@RestController
@RequestMapping("/api/mealplans")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @PostMapping
    public MealPlan create(@RequestBody MealPlan mealPlan,
                           Authentication authentication) {
        String email = authentication.getName();
        return mealPlanService.create(mealPlan, email);
    }

    @GetMapping
    public List<MealPlan> getMine(Authentication authentication) {
        return mealPlanService.getMyMealPlans(authentication.getName());
    }

    @GetMapping("/{id}")
    public MealPlan getById(@PathVariable Long id,
                            Authentication authentication) {
        return mealPlanService.getById(id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       Authentication authentication) {
        mealPlanService.delete(id, authentication.getName());
    }
}
