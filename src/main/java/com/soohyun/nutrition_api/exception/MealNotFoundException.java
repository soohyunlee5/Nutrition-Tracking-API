package com.soohyun.nutrition_api.exception;

import java.util.UUID;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(UUID id) {
        super("Meal not found with id: " + id);
    }
}
