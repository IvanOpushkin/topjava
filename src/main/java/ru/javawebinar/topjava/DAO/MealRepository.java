package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by Mega on 27.07.2017.
 */
public interface MealRepository {

    void save(Meal meal);

    //и в репозитории и тут сменим расширение delete and get
    //нет была ошибка не в типах, убрать ли для повышения производительности.
    void deleteMeal(Integer id);
    Meal getMeal(Integer id);

    Collection<Meal> getAllMeals();


}
