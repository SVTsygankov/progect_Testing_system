package com.svtsygankov.project_servlet_java_rush.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/start-test")
public class StartTestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int testId = Integer.parseInt(request.getParameter("id"));
        request.getSession().setAttribute("currentTestId", testId);
        response.sendRedirect("/test-passing");
    }
}