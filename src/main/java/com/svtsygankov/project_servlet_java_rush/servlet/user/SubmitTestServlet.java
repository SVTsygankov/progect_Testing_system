package com.svtsygankov.project_servlet_java_rush.servlet.user;

import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.RESULTS_SERVICE;
import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/submit-test")
public class SubmitTestServlet extends HttpServlet {
    private TestService testService;
    private ResultService resultService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.testService = (TestService) config.getServletContext().getAttribute(TEST_SERVICE);
        this.resultService = (ResultService) config.getServletContext().getAttribute(RESULTS_SERVICE);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            // 2. Получаем testId из сессии
            Integer testId = (Integer) req.getSession().getAttribute("currentTestId");
            if (testId == null) {
                throw new IllegalStateException("Сессия теста не инициализирована");
            }

            // 3. Парсим ответы пользователя
            Map<Integer, Integer> questionToAnswerMap = parseAnswers(req);

            // 4. Сохраняем результат
            var user = (User) req.getSession().getAttribute("user");
            Result result = resultService.createTestResult(user.getId(), testId, questionToAnswerMap);

            // 5. Очищаем сессию и показываем результат
            req.getSession().removeAttribute("currentTestId");
            req.setAttribute("result", result);
            req.getRequestDispatcher("/WEB-INF/views/secure/test-result.jsp").forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Ошибка сохранения результатов: " + e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    private Map<Integer, Integer> parseAnswers(HttpServletRequest request) {
        Map<Integer, Integer> answers = new HashMap<>();

        request.getParameterMap().forEach((paramName, paramValues) -> {
            if (paramName.startsWith("question_")) {
                int questionIndex = Integer.parseInt(paramName.substring("question_".length()));
                int answerIndex = Integer.parseInt(paramValues[0]);
                answers.put(questionIndex, answerIndex);
            }
        });

        return answers;
    }
}
