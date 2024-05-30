package com.bma.servlet;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.model.Session;
import com.bma.model.User;
import com.bma.service.LoginService;
import com.bma.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends FatherServlet {
    private static final LoginService loginService = LoginService.getInstance();
    private static final SessionService sessionService = SessionService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("login", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = null;

        try {
            user = loginService.authentication(login, password);

        } catch (NoSuchAlgorithmException | DatabaseException | InvalidUserDataException e) {
            context.setVariable("errorMessage", e.getMessage());
            templateEngine.process("login", context, resp.getWriter());
            return;
        }

        Session session = new Session(UUID.randomUUID().toString(), user, LocalDateTime.now().plusHours(24));
        sessionService.saveSession(session);

        Cookie cookie = new Cookie("sessionId", session.getId());
        cookie.setMaxAge(24 * 60 * 60);
        resp.addCookie(cookie);

        context.setVariable("name","Mishanya");
        context.setVariable("sessionId", cookie.getValue());


        templateEngine.process("home", context, resp.getWriter());
    }
}
