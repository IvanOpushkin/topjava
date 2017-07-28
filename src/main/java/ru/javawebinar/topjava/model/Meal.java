package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class Meal {

    private int mealId;

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal()
    {}


    public Meal(int mealId, LocalDateTime dateTime, String description, int calories) {

        this.mealId = mealId;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
