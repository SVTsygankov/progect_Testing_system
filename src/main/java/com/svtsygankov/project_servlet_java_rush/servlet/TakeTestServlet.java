package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.dao.TestDao;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.listener.ContextListener;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/secure/test/take")
public class TakeTestServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        testService = new TestService((TestDao) context.getAttribute(ContextListener.TEST_SERVICE));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Test test = testService.findById(id);

        if (test == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Тест не найден");
            return;
        }

        req.setAttribute("test", test);
        req.getRequestDispatcher("/WEB-INF/views/take-test.jsp").forward(req, resp);
    }
}