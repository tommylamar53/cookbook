package com.brapierre.cookbook.controller;

import com.brapierre.cookbook.entity.MealPlan;
import com.brapierre.cookbook.repository.MealPlanRepository;
import com.brapierre.cookbook.service.MealPlanService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/mealplans")
public class MealPlanViewController {

    private final MealPlanService mealPlanService;

    public MealPlanViewController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    @GetMapping
    public String listMealPlans(Model model, Authentication auth) {
        model.addAttribute(
                "mealPlans",
                mealPlanService.getMyMealPlans(auth.getName())
        );
        return "mealplans";
    }

    @GetMapping("/new")
    public String newMealPlanForm(Model model) {
        model.addAttribute("mealPlan", new MealPlan());
        return "mealplan_form";
    }

    // ✅ THIS FIXES THE 405
    @PostMapping
    public String createMealPlan(
            @ModelAttribute MealPlan mealPlan,
            Authentication auth
    ) {
        mealPlanService.create(mealPlan, auth.getName());
        return "redirect:/dashboard";
    }
}
