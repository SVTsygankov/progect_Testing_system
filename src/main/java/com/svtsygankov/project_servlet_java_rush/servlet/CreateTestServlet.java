package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.entity.AnswerOption;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.service.TestService;
import com.svtsygankov.project_servlet_java_rush.entity.Question;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static com.svtsygankov.project_servlet_java_rush.listener.ContextListener.TEST_SERVICE;

@WebServlet("/admin/test/create")
public class CreateTestServlet extends HttpServlet {

    private TestService testService;

    private static final String PARAM_QUESTION_TEXT = "questionText_";
    private static final String PARAM_ANSWER_TEXT = "answerText_";
    private static final String PARAM_IS_CORRECT = "isCorrect_";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        testService = (TestService) servletConfig.getServletContext().getAttribute(TEST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/admin/create-test.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Получаем текущего пользователя из сессии
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Пользователь не авторизован");
            return;
        }

        Long createdBy = currentUser.getId();

        String title = req.getParameter("title");
        String topic = req.getParameter("topic");

        if (title == null || title.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указано название теста");
            return;
        }

        if (topic == null || topic.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Не указана тема теста");
            return;
        }

        // Получаем все параметры и фильтруем их один раз
        List<String> filteredParams = Collections.list(req.getParameterNames()).stream()
                .filter(name -> name.startsWith(PARAM_QUESTION_TEXT) || name.startsWith(PARAM_ANSWER_TEXT))
                .toList();

        // Вызываем отдельные методы парсинга
        Map<Integer, String> questionTexts = parseQuestionTexts(filteredParams, req);
        Map<Integer, List<AnswerOption>> questionAnswers = parseAnswerOptions(filteredParams, req);

        // Собираем вопросы
        List<Question> questions = questionTexts.entrySet().stream()
                .map(entry -> {
                    int qId = entry.getKey();
                    String qText = entry.getValue();
                    List<AnswerOption> answers = questionAnswers.getOrDefault(qId, Collections.emptyList());
                    return new Question(qId, qText, answers);
                })
                .toList();

        if (questions.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Должен быть хотя бы один вопрос");
            return;
        }

        for (Question question : questions) {
            if (question.getAnswers().size() < 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "У вопроса \"" + question.getText() + "\" должно быть минимум 2 варианта ответа");
                return;
            }

            boolean hasCorrect = question.getAnswers().stream().anyMatch(AnswerOption::isCorrect);
            if (!hasCorrect) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "У вопроса \"" + question.getText() + "\" должен быть хотя бы один правильный вариант");
                return;
            }
        }

        try {
            testService.createTest(title, topic, createdBy, questions);
            resp.sendRedirect(req.getContextPath() + "/tests");
        } catch (Exception e) {
            throw new IOException("Ошибка при сохранении теста", e);
        }
    }
    private Map<Integer, String> parseQuestionTexts(List<String> filteredParams, HttpServletRequest req) {
        Map<Integer, String> questionTexts = new HashMap<>();

        for (String name : filteredParams) {
            if (name.startsWith(PARAM_QUESTION_TEXT)) {
                int qIndex = extractIndex(name);
                String text = req.getParameter(name);
                questionTexts.put(qIndex, text);
            }
        }

        return questionTexts;
    }

    private Map<Integer, List<AnswerOption>> parseAnswerOptions(List<String> filteredParams, HttpServletRequest req) {
        Map<Integer, List<AnswerOption>> questionAnswers = new HashMap<>();

        for (String name : filteredParams) {
            if (name.startsWith(PARAM_ANSWER_TEXT)) {
                String[] parts = name.split("_");
                int qIndex = Integer.parseInt(parts[1]);
                int aIndex = Integer.parseInt(parts[2]);

                String answerText = req.getParameter(name);
                String isCorrectStr = req.getParameter(PARAM_IS_CORRECT + qIndex + "_" + aIndex);
                boolean isCorrect = "on".equals(isCorrectStr);

                AnswerOption answer = new AnswerOption();
                answer.setId(aIndex);
                answer.setText(answerText);
                answer.setCorrect(isCorrect);

                questionAnswers.computeIfAbsent(qIndex, k -> new ArrayList<>()).add(answer);
            }
        }
        return questionAnswers;
    }

    private int extractIndex(String paramName) {
        String[] parts = paramName.split("_");
        return Integer.parseInt(parts[1]);
    }
}