package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> mealList = Arrays.asList(
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
       getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)
               .forEach(System.out::println);
//        .toLocalDate();
//        .toLocalTime();


    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

            Map<LocalDate,Integer> filteredWithExceededHelper = mealList.stream()
                    .collect(Collectors.groupingBy(Meal -> Meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

            return mealList.stream()
                    .filter(Meal -> TimeUtil.isBetween(Meal.getDateTime().toLocalTime(), startTime, endTime))
                    .map(Meal-> new MealWithExceed(Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(),
                            filteredWithExceededHelper.get(Meal.getDateTime().toLocalDate()) > caloriesPerDay))
                    .collect(Collectors.toList());

    }
}
