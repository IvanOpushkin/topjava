package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Meal {

    //Почему не примитивный тип
    private Integer id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal()
    {}


   public Meal(String description)
   {
       this.description = description;
   }

    public Meal(LocalDateTime dateTime, String description, int calories)
    {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }


    public Meal(Integer mealId, LocalDateTime dateTime, String description, int calories) {

        this.id = mealId;
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

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNew()
    {
        return id == null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy--MM--dd hh:mm"));
    }
}
