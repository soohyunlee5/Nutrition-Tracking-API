package com.soohyun.nutrition_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "meals"
)
public class Meal {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;
    private String name;
    private int calories;
    private LocalDate date;

    protected Meal() {};

    public Meal(String name, int calories, LocalDate date) {
        this.name = name;
        this.calories = calories;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format(
                "Meal[id=%s, name=%s, calorie=%s, date=%s]",
                id, name, calories, date
        );
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return date;
    }
}
