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
import java.util.List;

@WebServlet("/secure/tests")
public class TestsListServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        testService = new TestService((TestDao) context.getAttribute(ContextListener.TEST_SERVICE));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        List<Test> tests = testService.findAll();
        req.setAttribute("tests", tests);
        req.getRequestDispatcher("/WEB-INF/views/tests.jsp").forward(req, resp);
    }
}