package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.service.AuthenticationService;
import com.svtsygankov.project_servlet_java_rush.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.AUTHENTICATION_SERVICE;

public class BaseAuthenticationServlet extends HttpServlet {
    protected AuthenticationService authenticationService;
    protected UserService userService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        authenticationService = (AuthenticationService) servletConfig.getServletContext().getAttribute(AUTHENTICATION_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getInitParameter("resourceName")).forward(req,resp);
    }
}
