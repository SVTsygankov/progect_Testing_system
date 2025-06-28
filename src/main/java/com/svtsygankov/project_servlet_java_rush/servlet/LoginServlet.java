package com.svtsygankov.project_servlet_java_rush.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/login",
        initParams = {@WebInitParam(name = "resourceName", value = "/WEB-INF/views/login.jsp"),
                      @WebInitParam(name = "contentPage", value = "login-form")
        })
public class LoginServlet extends BaseAuthenticationServlet {

      @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (authenticationService.authenticated(req)) {
           resp.sendRedirect("/secure/tests");

        } else {
            req.setAttribute("contentPage", "/WEB-INF/views/login-form.jsp");
            req.getRequestDispatcher(getInitParameter("resourceName")).forward(req, resp);
        }
    }
}
