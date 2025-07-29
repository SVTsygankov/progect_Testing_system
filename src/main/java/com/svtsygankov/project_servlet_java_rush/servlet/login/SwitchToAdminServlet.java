package com.svtsygankov.project_servlet_java_rush.servlet.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/secure/switch-to-admin")
public class SwitchToAdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Проверка прав уже выполнена в фильтре
        response.sendRedirect(request.getContextPath() + "/admin/tests");
    }
}
