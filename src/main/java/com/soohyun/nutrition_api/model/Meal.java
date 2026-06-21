package com.soohyun.nutrition_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    @NotBlank(message = "Meal name cannot be empty")
    private String name;
    @Min(value = 0, message = "Calories cannot be negative")
    private int calories;
    @NotNull(message = "Date cannot be empty")
    private LocalDate date;
    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    protected Meal() {}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
