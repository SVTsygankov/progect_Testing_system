package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.entity.Role;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(urlPatterns = "/login",
        initParams = {@WebInitParam(name = "resourceName", value = "/WEB-INF/views/login.jsp"),
                      @WebInitParam(name = "contentPage", value = "login-form")
        })
public class LoginServlet extends BaseAuthenticationServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (authenticationService.authenticated(req)) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            String redirectUrl;
            if (user.getRole() == Role.ADMIN) {
                redirectUrl = "/admin/tests";
            } else {
                redirectUrl = "/secure/tests";
            }

            resp.sendRedirect(redirectUrl);

        } else {
            req.setAttribute("contentPage", getInitParameter("contentPage"));
            req.getRequestDispatcher(getInitParameter("resourceName")).forward(req, resp);
        }
    }
}
