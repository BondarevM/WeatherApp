package com.bma.servlet;

import com.bma.exception.InvalidSessionException;
import com.bma.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebServlet("/logout")
public class LogoutServlet extends FatherServlet {
    private static final SessionService sessionService = SessionService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = (String) context.getVariable("sessionId");

        try {
            sessionService.deleteSession(sessionId);
        } catch (InvalidSessionException ignored) {
        }

        Cookie deleteCookie = new Cookie("sessionId", "");
        deleteCookie.setMaxAge(0);
        resp.addCookie(deleteCookie);

        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
