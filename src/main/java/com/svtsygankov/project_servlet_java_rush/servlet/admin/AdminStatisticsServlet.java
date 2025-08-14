package com.svtsygankov.project_servlet_java_rush.servlet.admin;

import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.RESULTS_SERVICE;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.USER_SERVICE;

@WebServlet("/admin/statistics")
public class AdminStatisticsServlet extends HttpServlet {

    private UserService userService;
    private TestService testService;
    private ResultService resultService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute(USER_SERVICE);
        testService = (TestService) getServletContext().getAttribute(TEST_SERVICE);
        resultService = (ResultService) getServletContext().getAttribute(RESULTS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Проверка авторизации и роли
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        if (!"ADMIN".equals(user.getRole().name())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            // Получаем статистику
            long userCount = userService.getUserCount();
            long testCount = testService.getTestCount();
            long resultCount = resultService.getTotalResultCount();

            // Передаем в JSP
            req.setAttribute("userCount", userCount);
            req.setAttribute("testCount", testCount);
            req.setAttribute("resultCount", resultCount);

            req.setAttribute("contentPage", "/WEB-INF/views/admin/statistics.jsp");
            req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка загрузки статистики");
            req.setAttribute("contentPage", "/WEB-INF/views/alerts.jsp");
            req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
        }
    }
}
