package com.bma.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


import java.io.IOException;

@WebServlet("")
public class HomeServlet extends FatherServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.setVariable("name","Mishanya");

        Cookie[] cookies = req.getCookies();
        if (cookies != null){
            for(Cookie cookie: cookies){
                if (cookie.getName().equals("sessionId")){
                    context.setVariable("sessionId", cookie.getValue());
                }
            }
        }



//        HttpSession session = req.getSession();
        templateEngine.process("home", context, resp.getWriter());
    }


}
