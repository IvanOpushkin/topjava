package ru.javawebinar.topjava.DAO;



import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.DbUtil;
import ru.javawebinar.topjava.web.MealServlet;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mega on 27.07.2017.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {

    ///private
    public Map<Integer, Meal> repository = new ConcurrentHashMap();

  //Еслиб это был просто int + volatile почемуто бы не помогало с нашим двойным потоком.
    //Над всеми потоками стоит мега мощная машина. Можно пускать по 1 потоку, потомучто всё происходит со скоростью света. Мгновенно скажем.

    //делает значение абсолютно уникальным
    ///private
  public static AtomicInteger increment = new AtomicInteger(0);


    {
        //Напрямую репозиторий никогда не лапать, закрытая бизнес модель
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510));
    }



    public void save(Meal meal)
    {
        System.out.println(meal.isNew());
        if (meal.isNew())
        {
            //я что увеличиваю номер только с одной стороны?
            //ИТОГО ОШИБКА ЗАБЫЛ УВЕЛИЧИВАТЬ ID В МИЛ. УВЕЛИЧИВАЛ ТОЛЬКО ХОДОВОЙ ИНТ. ВООБЩЕ

           increment.getAndIncrement();

            meal.setId(increment.get());
            repository.put(increment.get(),meal);
            //Понять структуру путаницы
            //Не забыть про потокобезопасный форматтер даты к которому смогут обращаться множество юзеров.


        } else
        {

            //-1 тоже тут
            repository.put(meal.getId(),meal);
        }

    }



    public void deleteMeal(Integer id)
    {
        repository.remove(id);
    }

    public Meal getMeal(Integer id)
    {

        if (repository.get(id)!= null)
            return repository.get(id);
        else return new Meal("Данного блюда не существует");
    }

    public Collection<Meal> getAllMeals()
   {
       return repository.values();
   }

   public static void main(String[] args)
   {

       System.out.println(increment);

       Collection<Meal> meals = new InMemoryMealRepositoryImpl().getAllMeals();

       for (Meal meal : meals)
       {
           System.out.print(meal.isNew() +" ");
           System.out.println(meal);
       }
   }
}



//п52 модификация, как двойные циклы - двойные мапы с повтором данных - двойное изменение и в первом параметре и во втором.
//иначе путаница. где Н там Н.

//getAllMeals
//Видимо в этом методе можно будет сделать обратную сортировку по выводу через TreeMap

//Date.valueOf(meal.getDateTime().toLocalDate()
// + meal.getDateTime().toLocalTime().toNanoOfDay()));
//Просто Date.valueOf(LocalDate) без миллисекунд часов покачт

//Date.from(meal.getDateTime().atZone(ZoneId.systemDefault()).toInstant()));
//LocalDateTime состоит из конечных переменных, а Date это по факту миллисекунды
//с 1 Января 1970 года 00 00 Grinvich time Zone


//LocalDateTime ldt = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDate(),
//date.getHours(), date.getMinutes());

//у всего есть вторая сторона в программировании рид или врайт. туда или обратно.
//локал дэйт в дэйт или дэйт в локал дэйт. 2 стороны минимум всегда.
//preparedStatement... .executeUpdate(); написать походу или сразу сделать createStatement().executeQuery();

//тоесть суть многослойной программы. дописываем что угодно.
// а прого в Реальном времени отлично работает, пока она дописывается.

//Странный перевод дат, через instance
//https://blog.progs.be/542/date-to-java-time

//Есть два типа execute Update или Query разные команды одной команды.

//Форварды дальше заранее просто ссылки.
//И даём свойства слову action через attribute
//Также в jsp через форвард есть уматный паттерн подключения данных из java










//нерабочие 4 строчки. тк после добавления мила в репозиторий редактируется правильно айдишник мила. нулевой
//получается добавляется мил с нулевым ID в репозиторий


//System.out.println(increment);
// meal.setId(increment.get());
//System.out.println(increment);
//repository.put(increment.getAndIncrement(),meal);


  /*
    private Connection connection;

        Ну устанавилвается соединение возможно
    public MealDaoImpl() {

        this.connection = DbUtil.getConnection();

    }
*/

   /*
    public void addMeal(Meal meal) {

        try {
            //Создание предварительного предложения-команды в базу.
            //может быть я упустил идею с ID
            PreparedStatement preparedStatement =
       connection.prepareStatement("insert into Meals(LocalDateTime,Description,Calories) values (?, ?, ?)");

            //ПУНКТ 317 UPDATER простые команды для базы данных
            preparedStatement.setDate(1, new Date(meal.getDateTime().getNano()));

            //по времени под классом. getNano hz hz rabotaet li
            //Вернёт ли миллисикунды или надо сконвертировать из НаноСекунд
            //говорят почемуто бросает милли нам нужные вместо нано
            //такто 1 миллион нано сукунд = 1 миллисекунда

            preparedStatement.setString(2,meal.getDescription());
            preparedStatement.setInt(3,meal.getCalories());

            //команду в базу
            preparedStatement.executeUpdate();


        } catch (SQLException e)
        {
            MealServlet.getLog().debug("Ошибка в районе скрипта DaoImpl-ADD");
            e.printStackTrace();
        }
    }
    */

   /*
    public void updateMeal(Meal meal)
    {
        try
        {
            //Получается работа без Хибернэйта. Свой Java JDBC. Удобный PreparedStatement.

            PreparedStatement preparedStatement =
                    connection.prepareStatement("update Meals set LocalDateTime=? , Description=? , Calories=? where id=?");
            //почемуто where был разделён с первой частью скрипта через + строка обычно (может просто перенос)

           //что важно в предварительном стэйтмэнте идёт счёт с 1, а не 0. Как и во многих бизнес моделях.
            //возможно разделить на 1000000 при переносе на миллисек, если реал нано

            preparedStatement.setDate(1,new Date(meal.getDateTime().getNano()));
            preparedStatement.setString(2, meal.getDescription());
            preparedStatement.setInt(3,meal.getCalories());
            preparedStatement.setInt(4,meal.getMealId());

            preparedStatement.executeUpdate();

        }

        catch (SQLException e) //
        {
            MealServlet.getLog().debug("Ошибка в районе скрипта DaoImpl-Update");
            e.printStackTrace();
        }
    }
*/

/*
    public void deleteMeal(int mealId) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from meals where id=?");
            preparedStatement.setInt(1,mealId);
            preparedStatement.executeUpdate();

        } catch (SQLException e)
        {
            MealServlet.getLog().debug("Ошибка в районе скрипта DaoImpl-RemoveById");
            e.printStackTrace();
        }
    }
    */

 /*
    public Meal getMealById(int MealId)
    {
        Meal meal = new Meal();
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("select * from Meals where id=?");
            preparedStatement.setInt(1,MealId);
            //Элемент для чтения из MySQL и Способ. Табличный Сэт.
            ResultSet rs = preparedStatement.executeQuery();


           //проверка
            if (rs.next())
            {
                meal.setMealId(rs.getInt("ID"));

                //Преобразование даты из нано формата в локальный дата и время.
                Date date = rs.getDate("LocalDateTime");
                LocalDateTime ldt = LocalDateTime.of(date.getYear(), date.getMonth(),
                        date.getDate(), date.getHours(),date.getMinutes());
               meal.setDateTime(ldt);

               meal.setDescription(rs.getString("Description"));
               meal.setCalories(rs.getInt("Calories"));

            }

        }
        catch (SQLException e)
        {
            MealServlet.getLog().debug("Ошибка, гдето в определении еды по ID");
            e.printStackTrace();
        }


        return meal;

    }
    */

   /*


    public List<Meal> getAllMeals() {


        List<Meal> meals = new ArrayList<Meal>();
        try
        {
            //Вариация препэард стэйтмент, когда стэйтмент не требует данных
            Statement statement = connection.createStatement();


            ResultSet rs = statement.executeQuery("select * from Meals");

            while (rs.next())
            {
                Meal meal = new Meal();
                meal.setMealId(rs.getInt("ID"));
                meal.setDescription(rs.getString("Description"));
                meal.setCalories(rs.getInt("Calories"));

                Date date = rs.getDate("LocalDateTime");

                meal.setDateTime(LocalDateTime.of(date.getYear(),date.getMonth(),date.getDate(),
                        date.getHours(), date.getMinutes()));
                meals.add(meal);
            }

        }
        catch (SQLException e)
        {
            MealServlet.getLog().debug("Ошибка, гдето в выводе списка всей еды");
            e.printStackTrace();
        }

        return meals;
    }

    */

    /*
    public static void main(String args[])
    {

        List<Meal> meals = new MealDaoImpl().getAllMeals();

        meals.forEach(System.out::println);


    }*/



