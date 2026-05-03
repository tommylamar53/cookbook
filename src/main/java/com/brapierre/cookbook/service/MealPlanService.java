package com.brapierre.cookbook.service;

import com.brapierre.cookbook.entity.MealPlan;
import com.brapierre.cookbook.repository.MealPlanRepository;
import com.brapierre.cookbook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepo;
    private final UserRepository userRepo;

    public MealPlanService(MealPlanRepository mealPlanRepo, UserRepository userRepo) {
        this.mealPlanRepo = mealPlanRepo;
        this.userRepo = userRepo;
    }

    public MealPlan create(MealPlan plan, String userEmail) {
        plan.setUser(userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found")));
        return mealPlanRepo.save(plan);
    }

    public List<MealPlan> getMyMealPlans(String email) {
        return mealPlanRepo.findByUserEmail(email);
    }

    public MealPlan getById(Long id, String email) {
        return mealPlanRepo.findByIdAndUserEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public void delete(Long id, String email) {
        MealPlan plan = getById(id, email);
        mealPlanRepo.delete(plan);
    }
}
