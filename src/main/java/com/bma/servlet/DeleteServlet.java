package com.bma.servlet;

import com.bma.exception.InvalidSessionException;
import com.bma.service.LocationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class DeleteServlet extends FatherServlet {
    private static final LocationService locationService = LocationService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = (String) context.getVariable("sessionId");

        String cityName = req.getParameter("cityName");
        String latitude = req.getParameter("latitude");
        String longitude = req.getParameter("longitude");

        try {
            locationService.deleteLocation(sessionId, cityName, latitude, longitude);
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        }

        resp.sendRedirect("/");
    }
}
