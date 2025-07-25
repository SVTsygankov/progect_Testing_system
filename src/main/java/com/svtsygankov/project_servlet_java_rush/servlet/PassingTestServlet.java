package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/test-passing")
public class PassingTestServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Проверяем аутентификацию
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login");
            return;
        }

        // Проверяем, что тест выбран
        Integer testId = (Integer) request.getSession().getAttribute("currentTestId");
        if (testId == null) {
            response.sendRedirect("/list-tests"); // Нет теста в сессии → возвращаем к списку
            return;
        }

        // Загружаем тест и передаем на JSP
        Test test = testService.findById(testId);
        request.setAttribute("test", test);
        request.getRequestDispatcher("/test-passing.jsp").forward(request, response);
    }
}
