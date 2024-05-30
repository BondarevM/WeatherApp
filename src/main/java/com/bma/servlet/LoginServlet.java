package com.bma.servlet;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class LoginServlet extends FatherServlet {
    LoginService loginService = LoginService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("login", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            loginService.authentication(login, password);


            System.out.println();
        } catch (NoSuchAlgorithmException | DatabaseException | InvalidUserDataException e) {
            context.setVariable("errorMessage", e.getMessage());
            templateEngine.process("login", context, resp.getWriter());
        }

        System.out.println();
    }
}
