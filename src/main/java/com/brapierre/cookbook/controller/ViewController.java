package com.brapierre.cookbook.controller;

import com.brapierre.cookbook.repository.UserRepository;
import com.brapierre.cookbook.service.MealPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final MealPlanService mealPlanService;
    private final UserRepository userRepo;

    public ViewController(MealPlanService mealPlanService, UserRepository userRepo) {
        this.mealPlanService = mealPlanService;
        this.userRepo = userRepo;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication auth) {
        String email = auth.getName();
        model.addAttribute("mealPlans", mealPlanService.getMyMealPlans(email));
        model.addAttribute("user", userRepo.findByEmail(email).orElseThrow());
        return "dashboard";
    }

    @GetMapping("/recipes/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new com.brapierre.cookbook.entity.Recipe());
        return "recipe_form";
    }
}
