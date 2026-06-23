package com.soohyun.nutrition_api.service;

import com.soohyun.nutrition_api.exception.MealNotFoundException;
import com.soohyun.nutrition_api.model.Meal;
import com.soohyun.nutrition_api.repository.MealRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MealService {
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<Meal> getAllMeals() {
        return mealRepository.findAll();
    }

    public Optional<Meal> getMealById(UUID id) {
        return mealRepository.findById(id);
    }

    public Meal createMeal(Meal meal) {
        return mealRepository.save(meal);
    }

    public Meal updateMeal(UUID id, Meal updatedMeal) {
         Meal existingMeal = mealRepository.findById(id)
                 .orElseThrow(() ->
                         new MealNotFoundException(id));
         if (updatedMeal.getName() != null) {
             existingMeal.setName(updatedMeal.getName());
         }
         if (updatedMeal.getCalories() > 0) {
             existingMeal.setCalories(updatedMeal.getCalories());
         }
         if (updatedMeal.getDate() != null) {
             existingMeal.setDate(updatedMeal.getDate());
         }
         return mealRepository.save(existingMeal);
    }

    @Transactional
    public void deleteMeal(UUID id) {
        Meal meal = mealRepository.findById(id)
                        .orElseThrow(() ->
                                new MealNotFoundException(id));
        mealRepository.delete(meal);
        mealRepository.flush();
    }
}
