package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

/**
 * Created by Mega on 27.07.2017.
 */
public interface MealDao {

    void addMeal(Meal meal);
    void updateMeal(Meal meal);
    void deleteMeal(int id);
    Meal getMealById(int id);
    List<Meal> getAllMeals();


}
