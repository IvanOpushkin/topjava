package ru.javawebinar.topjava.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Mega on 27.07.2017.
 */
public class DbUtil {

    private static Connection connection;

    //Как получить коннекшен идущий по нашему приложению.
    //Очень важный элемент нужный возможно Всем Фрэймворкам и файлам будет статик,
    //для быстрого вызова

    public static Connection getConnection() {
        if (connection != null)
            return connection;

        else {
            try {
                Properties prop = new Properties();
                //Прочитали файл в inputstream
                InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
                prop.load(inputStream);
                //как импорт на библиотеку
                String driver = prop.getProperty("driver");
                //фул ссылка на нашу базу данных
                String url = prop.getProperty("url");

                String user = prop.getProperty("user");
                String password = prop.getProperty("password");

                //Java Reflection API (пока тока имя)
                Class.forName(driver);

                //DriverManager для работы с базами данных
                //getConnection получение прямого соединения с базой
                // ссылка на базу. Имя и пароль сразу аутентификация

                connection = DriverManager.getConnection(url, user, password);


                //сам файлик db.properties
                //driver=com.mysql.jdbc.Driver
                //   url=jdbc:mysql://localhost:3306/UserDB DIET OUR DATABASE
                // user=admin
                //   password=test


            }
            //Throwable = любая ошибка Java Machine мультикэтч
            catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return connection;

        }
    }
}
