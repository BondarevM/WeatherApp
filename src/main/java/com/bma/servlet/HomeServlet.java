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

//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("sessionId")) {
//                    context.setVariable("sessionId", cookie.getValue());
//                }
//            }
//        }

        String sessionId = (String)context.getVariable("sessionId");

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

        Cookie[] cookies = req.getCookies();

        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("sessionId")).findFirst();
        String sessionId = cookie.get().getValue();

        try {
            locationService.saveLocation(cityName, latitude, longitude, sessionId);
        } catch (InvalidSessionException | DatabaseException e) {
            System.out.println();
            context.setVariable("errorMessage", e.getMessage());

        }

        resp.sendRedirect("/");

    }
}
