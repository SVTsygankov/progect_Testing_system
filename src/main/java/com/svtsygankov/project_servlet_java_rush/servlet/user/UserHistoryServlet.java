package com.svtsygankov.project_servlet_java_rush.servlet.user;

import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.listener.ContextListener;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/secure/history")
public class UserHistoryServlet extends HttpServlet {
    private ResultService resultService;

    @Override
    public void init() {
        this.resultService = (ResultService) getServletContext()
                .getAttribute(ContextListener.RESULTS_SERVICE);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        var userId = user.getId();
        try {
            List<Result> results = resultService.getUserResults(userId);

            // Создаем список DTO или Map с дополнительным полем correctAnswersCount
            List<Map<String, Object>> resultsWithCounts = new ArrayList<>();

            for (Result result : results) {
                Map<String, Object> resultData = new HashMap<>();
                resultData.put("result", result);

                // Конвертируем LocalDateTime в Date для JSTL
                resultData.put(
                        "dateAsDate",
                        java.util.Date.from(result.getDate().atZone(ZoneId.systemDefault()).toInstant())
                );

                long correctCount = result.getAnswers().stream()
                        .filter(Result.UserAnswer::isCorrect)
                        .count();
                resultData.put("correctAnswersCount", correctCount);
                resultsWithCounts.add(resultData);
            }

            req.setAttribute("resultsWithCounts", resultsWithCounts);
            req.setAttribute("contentPage", "/WEB-INF/views/secure/history-content.jsp"); // Для лейаута

            // Для отладки: проверка загрузки ресурса
            InputStream is = getClass().getClassLoader().getResourceAsStream("messages.properties");
            if (is == null) {
                System.err.println("Файл messages.properties не найден в classpath!");
            } else {
                System.out.println("Файл messages.properties успешно загружен");
                is.close();
            }
            req.getRequestDispatcher("/WEB-INF/views/secure/history-layout.jsp").forward(req, resp);

        } catch (Exception e) {
            System.out.println("Исключение: " + e);
            req.setAttribute("error", "Error loading history");
            req.getRequestDispatcher("/WEB-INF/views/alerts.jsp")
                    .forward(req, resp);
        }
    }
}
