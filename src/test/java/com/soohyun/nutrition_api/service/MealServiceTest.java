package com.soohyun.nutrition_api.service;

import com.soohyun.nutrition_api.model.Meal;
import com.soohyun.nutrition_api.repository.MealRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {
    @Mock
    private MealRepository mealRepository;
    @InjectMocks
    private MealService mealService;

    @Test
    void getAllMeals() {
        Meal meal1 = new Meal("Chicken Rice", 500, LocalDate.of(2026, 6, 20));
        Meal meal2 = new Meal("Oatmeal", 150, LocalDate.of(2026, 6, 20));
        given(mealRepository.findAll()).willReturn(List.of(meal1, meal2));
        List<Meal> mealList = mealService.getAllMeals();
        assertThat(mealList).isNotNull()
                .hasSize(2)
                .extracting(Meal::getName)
                .containsExactly("Chicken Rice", "Oatmeal");
        verify(mealRepository).findAll();
    }

    @Test
    void createMeal() {
        Meal meal = new Meal("Kimchi Fried Rice", 400, LocalDate.of(2026, 6, 20));
        given(mealRepository.save(meal)).willReturn(meal);
        Meal newMeal = mealService.createMeal(meal);
        assertThat(newMeal).isNotNull().isSameAs(meal);
        assertThat(newMeal.getName()).isEqualTo("Kimchi Fried Rice");
        verify(mealRepository).save(meal);
        verifyNoMoreInteractions(mealRepository);
    }

    @Test
    void getMealById() {
         Meal meal = new Meal("Cheese Pizza", 300, LocalDate.of(2026, 6, 20));
         given(mealRepository.findById(meal.getId())).willReturn(Optional.of(meal));
         Optional<Meal> mealId = mealService.getMealById(meal.getId());
         assertThat(mealId).isPresent().contains(meal);
         verify(mealRepository).findById(meal.getId());
    }

    @Test
    void deleteMeal() {
        Meal meal = new Meal("Hamburger", 450, LocalDate.of(2026, 6, 20));
        given(mealRepository.findById(meal.getId())).willReturn(Optional.of(meal));
        mealService.deleteMeal(meal.getId());
        verify(mealRepository).findById(meal.getId());
        verify(mealRepository).delete(meal);
        verify(mealRepository).flush();
        verifyNoMoreInteractions(mealRepository);
    }
}
