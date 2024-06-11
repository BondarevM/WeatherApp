package com.bma.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@UtilityClass
public class ThymeleafUtil {

    public static WebContext buildWebContext(HttpServletRequest req, HttpServletResponse resp, ServletContext servletContext) {
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(servletContext);
        IServletWebExchange webExchange = application.buildExchange(req, resp);

        return new WebContext(webExchange);
    }


    public static ITemplateResolver buildTemplateResolver(ServletContext servletContext){

        JakartaServletWebApplication jakartaServletWebApplication = JakartaServletWebApplication.buildApplication(servletContext);
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(jakartaServletWebApplication);
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    public static TemplateEngine buildTemplateEngine(ServletContext servletContext){
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(buildTemplateResolver(servletContext));

        return templateEngine;
    }
}
