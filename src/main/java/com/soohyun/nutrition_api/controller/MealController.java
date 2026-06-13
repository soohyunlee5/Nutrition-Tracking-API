package com.soohyun.nutrition_api.controller;

import com.soohyun.nutrition_api.exception.MealNotFoundException;
import com.soohyun.nutrition_api.model.Meal;
import com.soohyun.nutrition_api.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
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
    Meal newMeal(@RequestBody Meal newMeal) {
        return mealService.createMeal(newMeal);
    }

    @DeleteMapping("/{id}")
    void deleteMeal(@PathVariable UUID id) {
        mealService.deleteMeal(id);
    }
}
