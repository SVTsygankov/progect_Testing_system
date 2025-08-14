package com.svtsygankov.project_servlet_java_rush.servlet.user;

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

@WebServlet("/passing-test")
public class PassingTestServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = (Integer) req.getSession().getAttribute("currentTestId");

        if (id == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID теста не найден в сессии");
            return;
        }

        Test test = testService.findById(id);
        if (test == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тест не найден");
            return;
        }

        req.setAttribute("test", test);
        req.setAttribute("contentPage", "/WEB-INF/views/secure/passing-test-content.jsp");
        req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
    }
}