package com.bma.servlet;

import com.bma.model.dto.LocationDto;
import com.bma.service.WeatherApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends FatherServlet{
    private static final WeatherApiService weatherApiService = WeatherApiService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!(boolean)context.getVariable("sessionIsValid")){
            resp.sendRedirect("/login");
            return;
        }

        String cityName = req.getParameter("cityName");

        if (cityName.isBlank()){
            resp.sendRedirect("/");
            return;
        }

        try {
            List<LocationDto> locations = weatherApiService.getLocations(cityName);
            context.setVariable("locations", locations);

            if (locations.isEmpty()){
                resp.sendRedirect("/?errorMessage=Invalid city name");
                return;
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        templateEngine.process("search", context, resp.getWriter());
//        resp.sendRedirect("/");
    }
}
