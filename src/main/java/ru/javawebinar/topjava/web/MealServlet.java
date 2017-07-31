package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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


    private InMemoryMealRepositoryImpl InMemoryMealRepositoryImpl;

    //Ложит всё созданиие Дао.
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEAL = "/meals.jsp";

    //Для первого запуска кода в АКТИВ возможно понадобится простой Мил Лист. А не модифицированный
    //private static final List<MealWithExceed> mewiex  = MealsUtil.getSmallFilter(MealsUtil.mealList, 2000);

   // private static final Collection list;
    private final static Logger log = getLogger(MealServlet.class);


    public static Logger getLog() {
        return log;
    }


    //[Проблема Конструктор]Или просто сделать в объявлении это new MealDaoImpl();

    public MealServlet()
    {
        //super(); там есть какойто супер инит если будет проблема
       // list=InMemoryMealRepositoryImpl.getAllMeals();
        this.InMemoryMealRepositoryImpl = new InMemoryMealRepositoryImpl(); //сразу прийдёт с коннекшеном
    }





    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("redirect to meals");

        String forward = "";
        //ТОЧКА ПОДКЛЮЧЕНИЯ ACTION пока Хз как. Точка Подключение всего КРУДА.

        String action =
                request.getParameter("action"); //реал код

        //параметры это слова
        //атрибуты это значения

        if (action == null)
        {
            forward = LIST_MEAL;
            //request.setAttribute("mealList",InMemoryMealRepositoryImpl.getAllMeals());
        }

        else if (action.equalsIgnoreCase("delete"))
        {
            forward = LIST_MEAL;
            int id = Integer.parseInt(request.getParameter("id"));
            //насчёт учёта многопоточности синхро в методы дао по удалению
            //1 действие в единицу времени (1/100000000 секунды) MB
            InMemoryMealRepositoryImpl.deleteMeal(id);

           // request.setAttribute("mealList",InMemoryMealRepositoryImpl.getAllMeals());
        }
        else if (action.equalsIgnoreCase("edit"))
        {
            forward = INSERT_OR_EDIT;
            //forward = LIST_MEAL;
            int id = Integer.parseInt(request.getParameter("id"));

            Meal meal = InMemoryMealRepositoryImpl.getMeal(id);

            request.setAttribute("meal", meal);
           // request.setAttribute("mealList",InMemoryMealRepositoryImpl.getAllMeals());
        }
        else if (action.equalsIgnoreCase("listMeal"))
        {
            forward = LIST_MEAL;

            //Точка коннекта со всем списком. Новым. Без Эксида из MySQLа.

        } else if(action.equalsIgnoreCase("create"))
        {
            forward = INSERT_OR_EDIT;
            Meal meal = new Meal(LocalDateTime.now(),"",1000);
            request.setAttribute("meal", meal);

        }
        //На странный экшен
        else
        {
            forward = INSERT_OR_EDIT; //настоящий код

            }



        RequestDispatcher view = request.getRequestDispatcher(forward);
        //request.setAttribute("mewiexList", mewiex);

        //request.setAttribute("mealList",InMemoryMealRepositoryImpl.getAllMeals());
        request.setAttribute("mealList", MealsUtil.getSmallFilter(InMemoryMealRepositoryImpl.getAllMeals(),2000));
        //response.sendRedirect("meals");
        view.forward(request,response); //настоящий код


        //Рабочий код дальше бэтовский без скриптозапуска
       // request.setAttribute("mewiexList", mewiex);
        // request.getRequestDispatcher("/meals.jsp").forward(request, response);

        //<url-pattern>/meals</url-pattern> меняем на mealServlet
        //в итоге нашло стандартный мэпинг на сервлет
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

       request.setCharacterEncoding("UTF-8");

       Meal meal = new Meal();

        //Нужна проверка на данные
       meal.setDateTime(LocalDateTime.parse(request.getParameter("dateTime")));
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));

        //уменьшить на 1 т.к в базе на 1 больше всегда
        //а вообще он всегда тут ставит ноль не присвоено
        //String ID = request.getParameter("id");

        //отсылаем запрос в рид онли
        if (!request.getParameter("id").isEmpty())
            meal.setId(Integer.parseInt(request.getParameter("id")));

        InMemoryMealRepositoryImpl.save(meal);


        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);

        //(поиграца)тут возможно не покажет норм юзера, иои вообще не покажет ничего, если не инициализировать meals, после обработки нового пользователя
        request.setAttribute("mealList", MealsUtil.getSmallFilter(InMemoryMealRepositoryImpl.getAllMeals(),2000));
        view.forward(request,response);


 }

}


//345.(HEAD CODE READER)
// Деление на блоки кода мышления. Отсортировка не важных блоков. Mind Line.

//346. (Фишки 3+1)
//1. По поводу простоты тегов скриптовые и простые. Самозакрытие или закрытие после сектора.
//2. 4 вида закрытий кода процентиком от библиотеки, библиотечных значений, просто текста до магического Java кодо
//3. РеквестДиспетчер это наш View из MVC. RequestMapping в 1 строку если форвард Аттрибуты не теряются.
// А нормально передаются на JSP.
//(Доп)хорошая идея возможно запись абреивиатур РЫЖИМ


//347 RequestMapping попроще. На action.
//Дописать завтра или позже

//348 ФОРМА редактирования(3+2+1)
//1.Чтобы реквест сработал с нужным нам типом данных <jsp:useBean id ="meal" type = "ru.javawebinar.topjava.model.Meal" scope = "request" />
//этот скриптовой тег из jstl кор. Sun Corporation.
//и обращение идёт через meal.id ввода в с:out
//2.Первое значит строка <form method = "POST" action = 'servlet_name'(its URL-pattern from servlet-mapping)> вся прога и потом
//3.input самозакрывающийся тег
//3.1.getParametr из формы POST. Редактирования. Получается из инпут типа name. Связующее звено.
///Двойное value (ручками)
//(Доп).а значение в нэйм кладётся value = "<c:out value = "${meal.x}"/>" или если не рукой, то просто value = "x";
//тип прописывается в тайпе (<input type ="text">).
// ТИП ЭЛЕМЕНТА.type = "text"; либо намбер. Тип переданного в getParametr.
//3.2.сложные типы html5 дефолтно прописываются в <section></section> удобный таблицы календаря например type="datetime-local";
// с аут String видимо спокойно конвертирует
//(Доп)P.S перед каждой инпут формочкой можно написать слово которое будет выводица перед ней. Строковый регистр формочки. Просто текст без тегов,
//между инпутами.





//52 //(МАПА ДВОЙНОЕ МОДИФИКАЦИЯ П 52)Если мапа берёт элемент ключа из элемента и он меняется. Менять его и в ключе и в элементе. Двойное мап.


/*<!-- jsp:useBean нужен IDEA для автодополнений - она понимает тип переменной,
        которая уже доступна в JSP (например через setAttribute). И еще эта переменная
        становится доступной в java вставках. Для вывода в JSP это тэг не обязателен.
        Если тип переменной JSP не совпадает с тем, что в jsp:useBean, будет ошибка.
        -->*/

//Делить проги на простые блоки легкие делать быстро.

//мб отвечает на запросы т.к идёт в параметры запрос из JSP файла
//хотя может запрос двухсторонний (атрибуты передаются на страницу. параметры берутся со страницы)

//Можно было написать одной строкой, но так намного понятней, что
//РеквестДиспетчер это наш View из MVC

//хорошая идея возможно запись абреивиатур РЫЖИМ

//Мозг привыйкает программировать по другому. От слов по разным языкам.

 /*
        try
        {
            //примет ли хз вроде для много клиенто поточности (как чат нужен другой форматтер)
            LocalDateTime date =
            //LocalDateTime ldt = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes());
            meal.setDateTime(ldt);
        }
        catch (ParseException e)
        {
            log.debug("Ошибка с формой Даты");
            e.printStackTrace();
        }
        */

