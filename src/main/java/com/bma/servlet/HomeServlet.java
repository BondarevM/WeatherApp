package com.bma.servlet;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidSessionException;
import com.bma.model.dto.WeatherDto;
import com.bma.service.LocationService;
import com.bma.service.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("")
public class HomeServlet extends FatherServlet {
    private static final LocationService locationService = LocationService.getInstance();
    private static final WeatherApiService weatherApiService = WeatherApiService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = (String) context.getVariable("sessionId");
        context.setVariable("errorMessage", req.getParameter("errorMessage"));

        try {
            List<WeatherDto> weathersForCurrentUser = weatherApiService.getWeathersForCurrentUser(sessionId);
            context.setVariable("weathers", weathersForCurrentUser);

        } catch (InterruptedException e) {
            context.setVariable("errorMessage", "Something went wrong, please try again");
            resp.sendRedirect("/");
            return;
        }

        templateEngine.process("home", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cityName = req.getParameter("cityName");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");

        String sessionId = (String) context.getVariable("sessionId");

        try {
            locationService.saveLocation(cityName, latitude, longitude, sessionId);
        } catch (InvalidSessionException | DatabaseException e) {
            System.out.println();
            context.setVariable("errorMessage", e.getMessage());
        }

        resp.sendRedirect("/");
    }
}
