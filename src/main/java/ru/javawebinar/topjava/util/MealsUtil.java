package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<Meal> mealList = Arrays.asList(
            new Meal(1,LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
            new Meal(2,LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
            new Meal(3,LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
            new Meal(4,LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
            new Meal(5,LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
            new Meal(6,LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));


    public static void main(String[] args) {

        //mewiex.forEach(System.out::println);

//        .toLocalDate();
//        .toLocalTime();


    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate,Integer> filteredWithExceededHelper = mealList.stream()
                    .collect(Collectors.groupingBy(Meal -> Meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

            return mealList.stream()
                    .filter(Meal -> TimeUtil.isBetween(Meal.getDateTime().toLocalTime(), startTime, endTime))
                    .map(Meal-> new MealWithExceed(Meal.getMealId(), Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(),
                            filteredWithExceededHelper.get(Meal.getDateTime().toLocalDate()) > caloriesPerDay))
                    .collect(Collectors.toList());

    }


    public static List<MealWithExceed> getSmallFilter(List<Meal> mealList, int caloriesPerDay)
    {
        Map<LocalDate, Integer> timeCalories = mealList.stream()
                .collect(Collectors.groupingBy(Meal -> Meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        return mealList.stream()
                .map(Meal -> new MealWithExceed(Meal.getMealId(),Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(),
                        timeCalories.get(Meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }




}
