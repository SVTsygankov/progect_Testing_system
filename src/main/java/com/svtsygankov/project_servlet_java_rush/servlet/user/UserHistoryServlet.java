package com.svtsygankov.project_servlet_java_rush.servlet.user;

import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.Test;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.listener.ContextListener;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/secure/history")
public class UserHistoryServlet extends HttpServlet {

    private ResultService resultService;
    private TestService testService;

    @Override
    public void init() {
        this.resultService = (ResultService) getServletContext()
                .getAttribute(ContextListener.RESULTS_SERVICE);
        this.testService = (TestService) getServletContext()
                .getAttribute(ContextListener.TEST_SERVICE);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        List<Result> results = resultService.getUserResults(user.getId());

        // Создаем Map для быстрого доступа к тестам
        Map<Integer, Test> testsMap = results.stream()
                .map(Result::getTestId)
                .distinct()
                .collect(Collectors.toMap(
                        testId -> testId,
                        (Integer id) -> {
                            try {
                                return testService.findById(id);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ));

        req.setAttribute("results", results);
        req.setAttribute("testsMap", testsMap);
        req.setAttribute("pageTitle", "История тестирования");
        req.setAttribute("contentPage", "/WEB-INF/views/secure/history.jsp");

        req.getRequestDispatcher("/WEB-INF/views/layout.jsp").forward(req, resp);
    }
}
