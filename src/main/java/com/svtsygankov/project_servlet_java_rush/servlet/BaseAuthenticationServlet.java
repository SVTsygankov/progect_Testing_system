package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.service.AuthenticationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.AUTHENTICATION_SERVICE;

public class BaseAuthenticationServlet extends HttpServlet {

    protected AuthenticationService authenticationService;

    // Путь к форме (например, "login-form.jsp" или "registration-form.jsp")
    private String contentPage;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        authenticationService =
                (AuthenticationService) servletConfig.getServletContext().getAttribute(AUTHENTICATION_SERVICE);
        this.contentPage = servletConfig.getInitParameter("contentPage");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Устанавливаем атрибут с формой
        req.setAttribute("contentPage", "/WEB-INF/views/" + contentPage + ".jsp");
        req.getRequestDispatcher(getInitParameter("resourceName")).forward(req,resp);
    }
}
