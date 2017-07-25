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
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));


    //Пока просто фильтрацию выключили какбы убрав ограничения со времени было с 7 до 12)
    //Переделать создав второй метод создающий попроще создающий просто новый список с проверкой.

    public static final List<MealWithExceed> mewiex  = getFilteredWithExceeded(mealList, LocalTime.of(0, 0), LocalTime.of(23,59), 2000);


    public static void main(String[] args) {

        mewiex.forEach(System.out::println);

//        .toLocalDate();
//        .toLocalTime();


    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate,Integer> filteredWithExceededHelper = mealList.stream()
                    .collect(Collectors.groupingBy(Meal -> Meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

            return mealList.stream()
                    .filter(Meal -> TimeUtil.isBetween(Meal.getDateTime().toLocalTime(), startTime, endTime))
                    .map(Meal-> new MealWithExceed(Meal.getDateTime(), Meal.getDescription(), Meal.getCalories(),
                            filteredWithExceededHelper.get(Meal.getDateTime().toLocalDate()) > caloriesPerDay))
                    .collect(Collectors.toList());

    }


}
