package com.soohyun.nutrition_api.controller;

import com.soohyun.nutrition_api.exception.MealNotFoundException;
import com.soohyun.nutrition_api.model.Meal;
import com.soohyun.nutrition_api.model.User;
import com.soohyun.nutrition_api.service.MealService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/meals")
public class MealController {
    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping
    List<Meal> all() {
        return mealService.getAllMeals();
    }

    @GetMapping("/{id}")
    Meal one(@PathVariable UUID id) {
        return mealService.getMealById(id)
                .orElseThrow(() -> new MealNotFoundException(id));
    }

    @PostMapping
    Meal newMeal(@Valid @RequestBody Meal newMeal, @AuthenticationPrincipal User currentUser) {
        newMeal.setUser(currentUser);
        return mealService.createMeal(newMeal);
    }

    @PatchMapping("/{id}")
    Meal updateMeal(@PathVariable UUID id, @Valid @RequestBody Meal updatedMeal) {
        return mealService.updateMeal(id, updatedMeal);
    }

    @DeleteMapping("/{id}")
    void deleteMeal(@PathVariable UUID id) {
        mealService.deleteMeal(id);
    }
}
