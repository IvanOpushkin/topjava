package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> calloriesDay = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            LocalDate mealsDate = userMeal.getDateTime().toLocalDate(); //Простое разделение дней и часов

           //Материал из будущего
            //calloriesDay.merge(mealsDate, userMeal.getCalories(),);



            //Линейный перебор O(N)
            if (calloriesDay.get(mealsDate) == null) {
                calloriesDay.put(mealsDate, userMeal.getCalories());
            } else {
                calloriesDay.put(mealsDate, calloriesDay.get(mealsDate) + userMeal.getCalories());
            }

        }

        //Линейная фильтрация по одному перебор фильр, мэп создание по 1 экземпляру, коллект наверное тоже.
        List<UserMealWithExceed> finalList = mealList.stream()
                //.filter(p -> p.getDateTime().toLocalTime().toNanoOfDay() > startTime.toNanoOfDay() && p.getDateTime().toLocalTime().toNanoOfDay() < endTime.toNanoOfDay())
                .filter(p -> TimeUtil.isBetween(p.getDateTime().toLocalTime(),startTime,endTime))
                .map(p -> new UserMealWithExceed(p.getDateTime(), p.getDescription(), p.getCalories(), calloriesDay.get(p.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());

       // for (UserMealWithExceed oneMeal : finalList) {
         //   System.out.println(oneMeal.toString());
      //  }

    return finalList;
    }
}

//&& (calloriesDay.get(p.getDateTime().toLocalDate())) < caloriesPerDay);


//Нужна второстепенная мапа в <ЛокалДэйт,Количество каллорий всего дня> затем перебор после сравнения по ключу даты get(key) returns
//каллорис дня, и фильтрануть их со временем и каллорийностью одновременно. (Прям в фильтре сравнивать по датам из готовой мапы каллории))
//Как бы поэффективней это делать. Наверно 1.Мапа с фул каллориями. 2.Фильтр списка по времени.
// 3.Перенос списка в список UserMealWithExceed (уже подходящего по времени из п2) с добавлением по мапе только Exceed значений тру.
//Неверно 3. Все значения не только с тру


//Нука оценим тайм комплексити O(n2) - бабл сорт или инсерт сорт. Перебор двумерный.
//O(n) перебор одномерный. типо наибольшего числа в массиве. пробросом линейно 1 раз.

//Итого первая мапа перебор линейный O(n) первый пункт
//Второй пункт время перебирается линейно и каллорийность чекается линейно (по идее сначала же проверяется первое условие в && (И)
//до второго дойдёт только если время подошло. Вторая игра с мапами тоже линейная O(n)
//Третье перенос отфильтрованых значений в лист тоже O(n)


//Таинство это очень круто. В нужный момент, когда краски раскрылись и фантазии читателя наполнилась, дать ему поиграть со своей фантазией.
//облегчить сцену как вариант, атмосферу возможно оставить.

//
