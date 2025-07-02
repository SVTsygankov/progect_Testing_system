package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/admin/tests",
        initParams = {
                @WebInitParam(name = "resourceName", value = "/WEB-INF/views/admin/admin-tests.jsp"),
                @WebInitParam(name = "contentPage", value = "/WEB-INF/views/admin/admin-test-list.jsp")
        }
)
public class AdminTestsServlet extends HttpServlet {

    private TestService testService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testService = (TestService) config.getServletContext().getAttribute("testService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Test> tests = testService.findAll();

            // Группируем тесты по темам
            Map<String, List<Test>> testsByTopic = tests.stream()
                    .collect(Collectors.groupingBy(Test::getTopic));

            req.setAttribute("testsByTopic", testsByTopic);

            // Установка contentPage из инициализации
            req.setAttribute("contentPage", getInitParameter("contentPage"));

            // Перенаправляем на resourceName из инициализации
            req.getRequestDispatcher(getInitParameter("resourceName")).forward(req, resp);
        } catch (Exception e) {
            throw new IOException("Не удалось загрузить тесты", e);
        }
    }
}
