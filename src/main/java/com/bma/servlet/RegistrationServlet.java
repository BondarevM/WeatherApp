package com.bma.servlet;

import com.bma.exception.DatabaseException;
import com.bma.exception.InvalidUserDataException;
import com.bma.service.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/registration")
public class RegistrationServlet extends FatherServlet{
    private static final RegistrationService registrationService = RegistrationService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        templateEngine.process("registration", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmedPassword = req.getParameter("confirmedPassword");

        try {
            registrationService.saveUser(login,password,confirmedPassword);
        } catch (InvalidUserDataException | NoSuchAlgorithmException | DatabaseException e) {
            context.setVariable("errorMessage", e.getMessage());
            templateEngine.process("registration", context, resp.getWriter());
        }



    }
}
