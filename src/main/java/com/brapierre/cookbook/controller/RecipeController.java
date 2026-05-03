package com.brapierre.cookbook.controller;

import com.brapierre.cookbook.entity.MealPlan;
import com.brapierre.cookbook.entity.Recipe;
import com.brapierre.cookbook.repository.MealPlanRepository;
import com.brapierre.cookbook.repository.RecipeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepo;
    private final MealPlanRepository mealPlanRepo;

    public RecipeController(RecipeRepository recipeRepo,
                            MealPlanRepository mealPlanRepo) {
        this.recipeRepo = recipeRepo;
        this.mealPlanRepo = mealPlanRepo;
    }

    /* ===================== ADD RECIPE PAGE ===================== */

    @GetMapping("/mealplans/{id}/add-recipe")
    public String addRecipePage(@PathVariable Long id,
                                Model model,
                                Authentication auth) {

        MealPlan mealPlan = mealPlanRepo.findById(id).orElseThrow();

        if (!mealPlan.getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("mealPlan", mealPlan);
        model.addAttribute("recipe", new Recipe());
        return "add-recipe";
    }

    /* ===================== CREATE RECIPE ===================== */

    @PostMapping("/mealplans/{id}/recipes")
    public String createRecipe(@PathVariable Long id,
                               @ModelAttribute Recipe recipe,
                               Authentication auth) {

        MealPlan plan = mealPlanRepo.findById(id).orElseThrow();

        if (!plan.getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipe.setMealPlan(plan); // ✅ THIS IS THE KEY LINE
        recipeRepo.save(recipe);

        return "redirect:/mealplans/" + id + "/recipes";
    }

    /* ===================== VIEW RECIPES ===================== */

    @GetMapping("/mealplans/{id}/recipes")
    public String viewRecipes(@PathVariable Long id,
                              Model model,
                              Authentication auth) {

        MealPlan plan = mealPlanRepo.findById(id).orElseThrow();

        if (!plan.getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("recipes", recipeRepo.findByMealPlanId(id));
        model.addAttribute("mealPlanId", id);
        return "recipes";
    }

    /* ===================== EDIT RECIPE PAGE ===================== */
    @GetMapping("/recipes/{id}/edit")
    public String editRecipeForm(@PathVariable Long id,
                                 Model model,
                                 Authentication auth) {

        Recipe recipe = recipeRepo.findById(id).orElseThrow();

        if (!recipe.getMealPlan().getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("recipe", recipe);
        model.addAttribute("mealPlan", recipe.getMealPlan());

        return "edit-recipe";
    }

    /* ===================== UPDATE RECIPE ===================== */
    @PostMapping("/recipes/{id}/edit")
    public String updateRecipe(@PathVariable Long id,
                               @ModelAttribute Recipe updated,
                               Authentication auth) {

        Recipe recipe = recipeRepo.findById(id).orElseThrow();

        if (!recipe.getMealPlan().getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        recipe.setName(updated.getName());
        recipe.setIngredients(updated.getIngredients());
        recipe.setInstructions(updated.getInstructions());
        recipe.setImageLink(updated.getImageLink());

        recipeRepo.save(recipe);

        return "redirect:/mealplans/" +
                recipe.getMealPlan().getId() + "/recipes";
    }

    /* ===================== DELETE RECIPE ===================== */
    @PostMapping("/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable Long id,
                               Authentication auth) {

        Recipe recipe = recipeRepo.findById(id).orElseThrow();

        if (!recipe.getMealPlan().getUser().getEmail().equals(auth.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Long mealPlanId = recipe.getMealPlan().getId();
        recipeRepo.delete(recipe);

        return "redirect:/mealplans/" + mealPlanId + "/recipes";
    }
}