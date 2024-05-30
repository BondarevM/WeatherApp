package com.bma.servlet;

import com.bma.exception.DatabaseException;
import com.bma.service.SessionService;
import com.bma.util.ThymeleafUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public abstract class FatherServlet extends HttpServlet {
    private static final SessionService sessionService = SessionService.getInstance();
    protected ITemplateEngine templateEngine;
    protected WebContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        templateEngine = (TemplateEngine) config.getServletContext().getAttribute("templateEngine");
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context = ThymeleafUtil.buildWebContext(req, resp, getServletContext());

        try {
            sessionService.validateAllSessions();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        Cookie[] cookies = req.getCookies();
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> c.getName().equals("sessionId")).findFirst();



        super.service(req, resp);
    }
}
