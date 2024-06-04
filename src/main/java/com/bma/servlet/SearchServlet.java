package com.bma.servlet;

import com.bma.service.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/search")
public class SearchServlet extends FatherServlet{
    private static final WeatherApiService weatherApiService = WeatherApiService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!(boolean)context.getVariable("sessionIsValid")){
            resp.sendRedirect("/login");
            return;
        }

        String cityName = req.getParameter("cityName");



        resp.sendRedirect("/");
    }
}
