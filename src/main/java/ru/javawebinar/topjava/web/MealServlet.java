package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Mega on 24.07.2017.
 */

//Это базовый контроллер Java. Без фитч фрэймворков

public class MealServlet extends HttpServlet {

    //Пока просто фильтрацию выключили какбы убрав ограничения со времени было с 7 до 12)
    //Переделать создав второй метод создающий попроще создающий просто новый список с проверкой.
    //ПЕРЕДЕЛАНО ДОБАВЛЕН МЕТОД ПРОСТОЙ ФИЛЬТРАЦИИ В MEALLIST. п35 не концентрироваца на простом, сделать кусок проги и дальше (ВЫШЕ ЛВЛ большие кода простые)
    //mb make it private + getter


    private static MealDaoImpl mealDaoImpl = new MealDaoImpl();
    //Ложит всё созданиие Дао.
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    //Для первого запуска кода в АКТИВ возможно понадобится простой Мил Лист. А не модифицированный
    private static final List<MealWithExceed> mewiex  = MealsUtil.getSmallFilter(MealsUtil.mealList, 2000);

    private final static Logger log = getLogger(MealServlet.class);


    public static Logger getLog() {
        return log;
    }


    //[Проблема Конструктор]Или просто сделать в объявлении это new MealDaoImpl();

    /*public MealServlet()
    {
        //super();
        mealDaoImpl = new MealDaoImpl(); //сразу прийдёт с коннекшеном
    }
    */




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("redirect to meals");

        String forward = "";
        //ТОЧКА ПОДКЛЮЧЕНИЯ ACTION пока Хз как. Точка Подключение всего КРУДА.

        String action = "";   //пока экшена нет заменим на пустоту
        //request.getParameter("action"); реал код


        if (action.equalsIgnoreCase("delete"))
        {
            forward = LIST_MEAL;
            int mealId = Integer.parseInt("2"
                    //request.getParameter("mealId")
                     );
            //насчёт учёта многопоточности синхро в методы дао по удалению
            //1 действие в единицу времени (1/100000000 секунды) MB
            mealDaoImpl.deleteMeal(mealId);

            request.setAttribute("meals",mealDaoImpl.getAllMeals());
        }
        else if (action.equalsIgnoreCase("edit"))
        {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDaoImpl.getMealById(mealId);
            request.setAttribute("meal", meal);
        }
        else if (action.equalsIgnoreCase("listMeal"))
        {
            forward = LIST_MEAL;
            // forward = INSERT_OR_EDIT;

            //Точка коннекта со всем списком. Новым. Без Эксида из MySQLа.
            request.setAttribute("meals",mealDaoImpl.getAllMeals());
        } else
            //forward = INSERT_OR_EDIT; //настоящий код
                forward = LIST_MEAL;



        RequestDispatcher view = request.getRequestDispatcher(forward);
        request.setAttribute("mewiexList", mewiex);
        //request.setAttribute("mewiexList",mealDaoImpl.getAllMeals());

        view.forward(request,response);


        //Рабочий код дальше бэтовский без скриптозапуска
       // request.setAttribute("mewiexList", mewiex);
        // request.getRequestDispatcher("/meals.jsp").forward(request, response);

        //<url-pattern>/meals</url-pattern> меняем на mealServlet
        //в итоге нашло стандартный мэпинг на сервлет
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        Meal meal = new Meal();


        try
        {
            //примет ли хз
            Date date = new SimpleDateFormat("YYYY/MM/DD/HH/MM").parse(request.getParameter("localDateTime"));
            LocalDateTime ldt = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes());
            meal.setDateTime(ldt);
        }
        catch (ParseException e)
        {
            log.debug("Ошибка с формой Даты");
            e.printStackTrace();
        }
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));


        String ID = request.getParameter("mealId");

        if (ID == null || ID.isEmpty())
        {
            mealDaoImpl.addMeal(meal);
        }
        else
        {
            meal.setMealId(Integer.parseInt(ID));
            mealDaoImpl.updateMeal(meal);
        }

        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);

        //(поиграца)тут возможно не покажет норм юзера, иои вообще не покажет ничего, если не инициализировать meals, после обработки нового пользователя
        request.setAttribute("meals",mealDaoImpl.getAllMeals());
        view.forward(request,response);

 }

}


//Делить проги на простые блоки легкие делать быстро.

//мб отвечает на запросы т.к идёт в параметры запрос из JSP файла
//хотя может запрос двухсторонний

//Можно было написать одной строкой, но так намного понятней, что
//РеквестДиспетчер это наш View из MVC

//хорошая идея возможно запись абреивиатур РЫЖИМ

//Мозг привыйкает программировать по другому. От слов по разным языкам.

