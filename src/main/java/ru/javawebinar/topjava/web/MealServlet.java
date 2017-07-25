package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by Mega on 24.07.2017.
 */
public class MealServlet extends HttpServlet {
    private final static Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        log.debug("redirect to meals"); //mb meals
        //        request.getRequestDispatcher("/users.jsp").forward(request, response);
        //mb meals

       //заполнить лист еды. если его нет хм. Вроде просто грамотно взять из милутиля



        //request.setAttribute("MLWElist",);



                //sendForward
        response.sendRedirect("meals.jsp"); //meals

    }
}
