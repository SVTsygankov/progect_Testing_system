package com.svtsygankov.project_servlet_java_rush.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration",
        initParams = {@WebInitParam(name = "resourceName", value = "/registration.html")})
public class RegistrationServlet extends BaseAuthenticationServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(authenticationService.register(req)) {
            resp.sendRedirect("/login");
        } else {
            req.setAttribute("registrationErrorMessage", "User name is not unique");
        }
    }
}
